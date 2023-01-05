package de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.part1;

import de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.model.*;

import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValleyWalker {

	public ValleyAndExpeditionState walkInMinimumTime(InitialState initialState) {
		final Valley valley = initialState.getValley();

		ValleyAndExpeditionState start = new ValleyAndExpeditionState();
		start.getBlizzardList().addAll(initialState.getBlizzardList());
		start.setExpeditionLocation(valley.getEntry());

		ValleyAndExpeditionState minState = start;
		int minimumMinutes = Integer.MAX_VALUE;

		final Stack<ValleyAndExpeditionState> openNodes = new Stack<>();
		openNodes.add(start);
		printState(valley, start);

		int numberOfNodesEvaluated = 0;
		while (!openNodes.isEmpty()) {
			final ValleyAndExpeditionState currentState = openNodes.pop();
			while (!currentState.getExpeditionLocation().equals(valley.getExit())) {
				if (currentState.getMinute() > minimumMinutes) {
					break;
				}
				for (Blizzard blizzard : currentState.getBlizzardList()) {
					blizzard.getDirection().getMove().accept(blizzard.getCoordinates());
					if (!valley.getFields().contains(blizzard.getCoordinates())) {
						wrapBlizzardMovement(valley, blizzard);
					}
				}
				final List<Coordinates> availableOptions = Stream.of(Direction.values())
						.map(direction -> {
							final Coordinates coordinates = new Coordinates(currentState.getExpeditionLocation().getX(), currentState.getExpeditionLocation().getY());
							direction.getMove().accept(coordinates);
							return coordinates;
						})
						.filter(coordinates -> currentState.getBlizzardList().stream().noneMatch(blizzard -> blizzard.getCoordinates().equals(coordinates)))
						.filter(coordinates -> valley.getFields().contains(coordinates))
						.sorted(Comparator.comparing(coordinates -> {
							final Coordinates exit = valley.getExit();
							return Math.abs(exit.getX() - coordinates.getX()) + Math.abs(exit.getY() - coordinates.getY());
						})).collect(Collectors.toList());
				if (availableOptions.isEmpty()) {
					//no options remaining and current position is occupied by blizzard
					if (currentState.getBlizzardList().stream().anyMatch(blizzard -> blizzard.getCoordinates().equals(currentState.getExpeditionLocation()))) {
						break;
					}
					//else wait
				} else if (availableOptions.size() == 1) {
					currentState.getExpeditionLocation().setX(availableOptions.get(0).getX());
					currentState.getExpeditionLocation().setY(availableOptions.get(0).getY());
					if (currentState.getBlizzardList().stream().noneMatch(blizzard -> blizzard.getCoordinates().equals(currentState.getExpeditionLocation()))) {
						createNewBranch(openNodes, currentState, availableOptions, new Coordinates(currentState.getExpeditionLocation().getX(), currentState.getExpeditionLocation().getY()));
					}

				} else {
					currentState.getExpeditionLocation().setX(availableOptions.get(0).getX());
					currentState.getExpeditionLocation().setY(availableOptions.get(0).getY());
					for (int possibility = 1; possibility < availableOptions.size(); possibility++) {
						createNewBranch(openNodes, currentState, availableOptions, availableOptions.get(possibility));
					}
					if (currentState.getBlizzardList().stream().noneMatch(blizzard -> blizzard.getCoordinates().equals(currentState.getExpeditionLocation()))) {
						createNewBranch(openNodes, currentState, availableOptions, new Coordinates(currentState.getExpeditionLocation().getX(), currentState.getExpeditionLocation().getY()));
					}
				}
				currentState.setMinute(currentState.getMinute() + 1);
				//printState(valley, start);
			}
			numberOfNodesEvaluated++;
			if (numberOfNodesEvaluated % 1_000 == 0) {
				System.out.println(numberOfNodesEvaluated + " branches evaluated.");
			}

			if (currentState.getMinute() < minimumMinutes && currentState.getExpeditionLocation().equals(valley.getExit())) {
				minimumMinutes = currentState.getMinute();
				minState = currentState;
				System.out.println(minimumMinutes + " is the new best time.");
			}
		}
		System.out.println(numberOfNodesEvaluated + " branches evaluated.");

		return minState;
	}

	private void createNewBranch(Stack<ValleyAndExpeditionState> openNodes, ValleyAndExpeditionState currentState, List<Coordinates> availableOptions, Coordinates coordinates) {
		final ValleyAndExpeditionState next = new ValleyAndExpeditionState();
		next.setMinute(currentState.getMinute() + 1);
		next.setExpeditionLocation(coordinates);
		next.getBlizzardList().addAll(currentState.getBlizzardList().stream()
				.map(blizzard -> new Blizzard(new Coordinates(blizzard.getCoordinates().getX(), blizzard.getCoordinates().getY()), blizzard.getDirection()))
				.collect(Collectors.toList()));
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

	public void printState(Valley valley, ValleyAndExpeditionState state) {
		for (int y = 0; y <= valley.getMaxY() + 1; y++) {
			for (int x = 0; x <= valley.getMaxX() + 1; x++) {
				final Coordinates coordinates = new Coordinates(x, y);
				if (coordinates.equals(state.getExpeditionLocation())) {
					System.out.print("E");
				} else if (state.getBlizzardList().stream().anyMatch(blizzard -> blizzard.getCoordinates().equals(coordinates))) {
					final List<Blizzard> blizzards = state.getBlizzardList().stream().filter(blizzard -> blizzard.getCoordinates().equals(coordinates)).collect(Collectors.toList());
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
}