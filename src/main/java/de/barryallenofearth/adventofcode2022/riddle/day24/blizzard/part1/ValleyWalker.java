package de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.part1;

import de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.model.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValleyWalker {

	private Map<Integer, List<Coordinates>> blizzardCoordinateCache = new HashMap<>();

	public static final int MINIMUM_MINUTES = 800;

	public ExpeditionState walkInMinimumTime(InitialState initialState) {

		fillBlizzardCache(initialState);

		final Valley valley = initialState.getValley();
		System.out.println("Minimum distance " + (Math.abs(valley.getExit().getX() - valley.getEntry().getX()) + Math.abs(valley.getExit().getY() - valley.getEntry().getY())));

		ExpeditionState start = new ExpeditionState();
		start.setExpeditionLocation(new Coordinates(valley.getEntry().getX(), valley.getEntry().getY()));

		final Set<ExpeditionState> openNodes = new HashSet<>();
		openNodes.add(start);

		int numberOfSteps = 0;
		ExpeditionState currentState = null;
		do {
			currentState = openNodes.stream()
					.sorted(Comparator.comparingInt(ExpeditionState::getMinute)).findFirst()
					.get();
			openNodes.remove(currentState);
			if (currentState.getExpeditionLocation().equals(valley.getExit())) {
				break;
			}
			//if (abortBranch(valley, currentState, openNodes)) {
			//	continue;
			//}
			final int minute = currentState.getMinute() + 1;
			currentState.setMinute(minute);

			final Coordinates expeditionLocation = currentState.getExpeditionLocation();
			final List<Coordinates> availableOptions = Stream.of(Direction.values())
					.map(direction -> direction.getMove().apply(expeditionLocation))
					.filter(coordinates -> !blizzardCoordinateCache.get(minute).contains(coordinates))
					.filter(coordinates -> valley.getFields().contains(coordinates))
					.collect(Collectors.toList());
			if (availableOptions.isEmpty()) {
				//no options remaining and current position is occupied by blizzard
				if (blizzardCoordinateCache.get(currentState.getMinute()).contains(currentState.getExpeditionLocation())) {
					continue;
				}
				createNewBranch(openNodes, currentState, new Coordinates(currentState.getExpeditionLocation().getX(), currentState.getExpeditionLocation().getY()));
			} else {
				//else wait
				if (!blizzardCoordinateCache.get(currentState.getMinute()).contains(currentState.getExpeditionLocation())) {
					createNewBranch(openNodes, currentState, new Coordinates(currentState.getExpeditionLocation().getX(), currentState.getExpeditionLocation().getY()));
				}

				for (Coordinates availableOption : availableOptions) {
					createNewBranch(openNodes, currentState, availableOption);
				}
			}
			numberOfSteps++;
			if (numberOfSteps % 100_000 == 0) {
				System.out.println(numberOfSteps + " steps taken. Current minute is: " + minute);
			}
		} while (!openNodes.isEmpty());

		if (currentState.getExpeditionLocation().equals(valley.getExit())) {
			return currentState;
		}
		throw new IllegalStateException("Target was not reached. Final position after " + currentState.getMinute()
				+ " minutes is (" + currentState.getExpeditionLocation().getX() + "," + currentState.getExpeditionLocation().getY() + ")");
	}

	private void fillBlizzardCache(InitialState initialState) {
		final Map<Integer, List<Blizzard>> blizzardCache = new HashMap<>();
		blizzardCache.put(0, initialState.getBlizzardList());
		for (int minute = 1; minute <= MINIMUM_MINUTES; minute++) {
			final List<Blizzard> previousBlizzard = blizzardCache.get(minute - 1);
			blizzardCache.put(minute, new ArrayList<>());
			for (Blizzard oldBlizzard : previousBlizzard) {
				final Blizzard blizzard = new Blizzard(oldBlizzard.getDirection().getMove().apply(oldBlizzard.getCoordinates()), oldBlizzard.getDirection());
				if (!initialState.getValley().getFields().contains(blizzard.getCoordinates())) {
					wrapBlizzardMovement(initialState.getValley(), blizzard);
				}
				blizzardCache.get(minute).add(blizzard);
			}
		}
		blizzardCoordinateCache = blizzardCache.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream().map(Blizzard::getCoordinates).collect(Collectors.toList())));
	}

	private boolean abortBranch(Valley valley, ExpeditionState currentState, Set<ExpeditionState> openNodes) {
		final int stepsToExit = Math.abs(valley.getExit().getX() - currentState.getExpeditionLocation().getX()) + Math.abs(valley.getExit().getY() - currentState.getExpeditionLocation().getY());
		if (currentState.getMinute() + stepsToExit >= MINIMUM_MINUTES) {
			return true;
		}
		return false;
	}

	private void createNewBranch(Set<ExpeditionState> openNodes, ExpeditionState currentState, Coordinates coordinates) {
		final ExpeditionState next = new ExpeditionState();
		next.setMinute(currentState.getMinute());
		next.setExpeditionLocation(coordinates);
		openNodes.add(next);
	}

	private void wrapBlizzardMovement(Valley valley, Blizzard blizzard) {
		switch (blizzard.getDirection()) {
		case LEFT:
			blizzard.getCoordinates().setX(valley.getMaxX());
			break;
		case RIGHT:
			blizzard.getCoordinates().setX(valley.getMinX());
			break;
		case UP:
			blizzard.getCoordinates().setY(valley.getMaxY());
			break;
		case DOWN:
			blizzard.getCoordinates().setY(valley.getMinY());
			break;
		}
	}
/*
	public void printState(Valley valley, ValleyAndExpeditionState state, InitialState initialState) {
		final ValleyAndExpeditionState printingState = new ValleyAndExpeditionState();
		printingState.setMinute(state.getMinute());
		printingState.setExpeditionLocation(new Coordinates(valley.getEntry().getX(), valley.getEntry().getY()));
		printingState.getExpeditionFields().addAll(state.getExpeditionFields());
		for (int minute = 1; minute <= printingState.getMinute(); minute++) {
			System.out.println("Minute " + minute);
			for (int y = 0; y <= valley.getMaxY() + 1; y++) {
				for (int x = 0; x <= valley.getMaxX() + 1; x++) {
					final Coordinates coordinates = new Coordinates(x, y);
					if (coordinates.equals(printingState.getExpeditionFields().get(minute - 1))) {
						System.out.print("E");
					} else if (blizzardCoordinateCache.get(minute).contains(coordinates)) {
						final List<Blizzard> blizzards = blizzardCoordinateCache.get(minute).stream().filter(blizzardCoordinate -> blizzardCoordinate.equals(coordinates)).collect(Collectors.toList());
						if (blizzards.size() > 1) {
							System.out.print(blizzards.size());
						} else {
							System.out.print(blizzards.get(0).getDirection().getKey());
						}
					} else if (!valley.getFields().contains(coordinates)) {
						System.out.print("#");
					} else {
						System.out.print(".");
					}
				}
				System.out.println();
			}
			System.out.println();
		}
	}*/
}