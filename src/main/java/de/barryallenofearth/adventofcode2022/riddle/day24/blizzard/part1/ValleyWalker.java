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
				} else {
					currentState.getExpeditionLocation().setX(availableOptions.get(0).getX());
					currentState.getExpeditionLocation().setY(availableOptions.get(0).getY());
					for (int possibility = 1; possibility < availableOptions.size(); possibility++) {
						final ValleyAndExpeditionState next = new ValleyAndExpeditionState();
						next.setMinute(currentState.getMinute() + 1);
						next.setExpeditionLocation(availableOptions.get(possibility));
						next.getBlizzardList().addAll(currentState.getBlizzardList().stream()
								.map(blizzard -> new Blizzard(new Coordinates(blizzard.getCoordinates().getX(), blizzard.getCoordinates().getY()), blizzard.getDirection()))
								.collect(Collectors.toList()));
						openNodes.add(next);
					}
				}
				currentState.setMinute(currentState.getMinute() + 1);
			}
			numberOfNodesEvaluated++;
			if (numberOfNodesEvaluated % 1_000 == 0) {
				System.out.println(numberOfNodesEvaluated + " branches evaluated.");
			}
			if (currentState.getMinute() < minimumMinutes) {
				minimumMinutes = currentState.getMinute();
				minState = currentState;
				System.out.println(minimumMinutes + " is the new best time.");
			}
		}

		return minState;
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
}
