package de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.part1;

import de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.model.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValleyWalker {

	private Map<Integer, List<Coordinates>> blizzardCoordinateCache = new HashMap<>();

	public ValleyAndExpeditionState walkInMinimumTime(InitialState initialState) {

		int minimumMinutes = 808;

		fillBlizzardCache(initialState, minimumMinutes);

		final Valley valley = initialState.getValley();

		ValleyAndExpeditionState start = new ValleyAndExpeditionState();
		start.setExpeditionLocation(new Coordinates(valley.getEntry().getX(), valley.getEntry().getY()));

		ValleyAndExpeditionState minState = start;

		final Stack<ValleyAndExpeditionState> openNodes = new Stack<>();
		openNodes.add(start);

		int numberOfNodesEvaluated = 0;
		int numberOfSuccessfulNodesEvaluated = 0;
		//printState(valley, start, initialState);
		while (!openNodes.isEmpty()) {
			final ValleyAndExpeditionState currentState = openNodes.pop();
			while (!currentState.getExpeditionLocation().equals(valley.getExit())) {
				if (abortBranch(valley, minimumMinutes, currentState, openNodes)) {
					break;
				}
				currentState.setMinute(currentState.getMinute() + 1);

				final List<Coordinates> availableOptions = Stream.of(Direction.values())
						.map(direction -> {
							final Coordinates coordinates = new Coordinates(currentState.getExpeditionLocation().getX(), currentState.getExpeditionLocation().getY());
							direction.getMove().accept(coordinates);
							return coordinates;
						})
						.filter(coordinates -> !blizzardCoordinateCache.get(currentState.getMinute()).contains(coordinates))
						.filter(coordinates -> valley.getFields().contains(coordinates))
						.sorted(Comparator.comparing(coordinates -> {
							final Coordinates exit = valley.getExit();
							return Math.abs(exit.getX() - coordinates.getX()) + Math.abs(exit.getY() - coordinates.getY());
						})).collect(Collectors.toList());
				if (availableOptions.isEmpty()) {
					//no options remaining and current position is occupied by blizzard
					if (blizzardCoordinateCache.get(currentState.getMinute()).contains(currentState.getExpeditionLocation())) {
						break;
					}
					//else wait
				} else if (availableOptions.size() == 1) {
					currentState.getExpeditionLocation().setX(availableOptions.get(0).getX());
					currentState.getExpeditionLocation().setY(availableOptions.get(0).getY());
					//				if (currentState.getBlizzardList().stream().noneMatch(blizzard -> blizzard.getCoordinates().equals(currentState.getExpeditionLocation()))) {
					//					createNewBranch(openNodes, currentState, new Coordinates(currentState.getExpeditionLocation().getX(), currentState.getExpeditionLocation().getY()));
					//				}

				} else {
					currentState.getExpeditionLocation().setX(availableOptions.get(0).getX());
					currentState.getExpeditionLocation().setY(availableOptions.get(0).getY());
					for (int possibility = 1; possibility < availableOptions.size(); possibility++) {
						createNewBranch(openNodes, currentState, availableOptions.get(possibility));
					}
					//			if (currentState.getBlizzardList().stream().noneMatch(blizzard -> blizzard.getCoordinates().equals(currentState.getExpeditionLocation()))) {
					//				createNewBranch(openNodes, currentState, new Coordinates(currentState.getExpeditionLocation().getX(), currentState.getExpeditionLocation().getY()));
					//			}
				}
			}
			numberOfNodesEvaluated++;
			if (currentState.getExpeditionLocation().equals(valley.getExit())) {
				numberOfSuccessfulNodesEvaluated++;
			}
			if (numberOfNodesEvaluated % 100_000 == 0) {
				System.out.println(numberOfNodesEvaluated + " branches evaluated. Current minium is: " + minimumMinutes);
				System.out.println(numberOfSuccessfulNodesEvaluated + " branches successfully evaluated.");
			}

			if (currentState.getMinute() < minimumMinutes && currentState.getExpeditionLocation().equals(valley.getExit())) {
				minimumMinutes = currentState.getMinute();
				minState = currentState;
				System.out.println(minimumMinutes + " is the new best time.");
				//printState(valley, currentState, initialState);
			}
		}
		System.out.println(numberOfNodesEvaluated + " branches evaluated.");

		return minState;
	}

	private void fillBlizzardCache(InitialState initialState, int minimumMinutes) {
		final Map<Integer, List<Blizzard>> blizzardCache = new HashMap<>();
		blizzardCache.put(0, initialState.getBlizzardList().stream()
				.map(blizzard -> new Blizzard(new Coordinates(blizzard.getCoordinates().getX(), blizzard.getCoordinates().getY()), blizzard.getDirection()))
				.collect(Collectors.toList()));
		for (int minute = 1; minute <= minimumMinutes; minute++) {
			final List<Blizzard> previousBlizzard = blizzardCache.get(minute - 1);
			blizzardCache.put(minute, new ArrayList<>());
			for (Blizzard oldBlizzard : previousBlizzard) {
				final Blizzard blizzard = new Blizzard(new Coordinates(oldBlizzard.getCoordinates().getX(), oldBlizzard.getCoordinates().getY()), oldBlizzard.getDirection());
				blizzardCache.get(minute).add(blizzard);
				blizzard.getDirection().getMove().accept(blizzard.getCoordinates());
				if (!initialState.getValley().getFields().contains(blizzard.getCoordinates())) {
					wrapBlizzardMovement(initialState.getValley(), blizzard);
				}
			}
		}
		blizzardCoordinateCache = blizzardCache.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream().map(Blizzard::getCoordinates).collect(Collectors.toList())));
	}

	private boolean abortBranch(Valley valley, int minimumMinutes, ValleyAndExpeditionState currentState, Stack<ValleyAndExpeditionState> openNodes) {
		final int stepsToExit = Math.abs(valley.getExit().getX() - currentState.getExpeditionLocation().getX()) + Math.abs(valley.getExit().getY() - currentState.getExpeditionLocation().getY());
		if (currentState.getMinute() + stepsToExit >= minimumMinutes) {
			return true;
		}
		return openNodes.stream().anyMatch(openNode -> openNode.getMinute() == currentState.getMinute() && openNode.getExpeditionLocation().equals(currentState.getExpeditionLocation()));
	}

	private void createNewBranch(Stack<ValleyAndExpeditionState> openNodes, ValleyAndExpeditionState currentState, Coordinates coordinates) {
		final ValleyAndExpeditionState next = new ValleyAndExpeditionState();
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