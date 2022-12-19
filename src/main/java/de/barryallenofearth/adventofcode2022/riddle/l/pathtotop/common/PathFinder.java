package de.barryallenofearth.adventofcode2022.riddle.l.pathtotop.common;

import de.barryallenofearth.adventofcode2022.riddle.l.pathtotop.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.l.pathtotop.model.HeightJourneyModel;

import java.util.*;

public class PathFinder {

	public static String[][] findShortestPath(HeightJourneyModel heightJourneyModel, boolean useBreakCondition) {
		final Coordinates startingPosition = heightJourneyModel.getTargetPosition();
		final Coordinates targetPosition = heightJourneyModel.getStartingPosition();

		final String[][] pathMap = new String[heightJourneyModel.getHeightMap().length][heightJourneyModel.getHeightMap()[0].length];

		for (int row = 0; row < heightJourneyModel.getHeightMap().length; row++) {
			for (int column = 0; column < heightJourneyModel.getHeightMap()[0].length; column++) {
				pathMap[row][column] = "   x  ";
			}
		}

		final Queue<Set<Coordinates>> candidatesToExamine = new LinkedList<>();

		final Set<Coordinates> initialOpenNodes = new HashSet<>();
		initialOpenNodes.add(startingPosition);
		candidatesToExamine.add(initialOpenNodes);

		int stepCount = 0;
		List<Coordinates> closedNodes = new ArrayList<>();
		while (!candidatesToExamine.isEmpty()) {
			final Set<Coordinates> openNodes = candidatesToExamine.poll();
			final Set<Coordinates> furtherOpenNodes = new HashSet<>();

			for (Coordinates node : openNodes) {
				closedNodes.add(node);
				final String symbol = createStepSymbol(heightJourneyModel, stepCount, node);
				pathMap[node.getRow()][node.getColumn()] = symbol;

				final List<Coordinates> options = getOptions(heightJourneyModel, node, closedNodes);
				furtherOpenNodes.addAll(options);
			}

			if (!furtherOpenNodes.isEmpty()) {
				candidatesToExamine.add(furtherOpenNodes);
			}

			if (useBreakCondition & closedNodes.contains(targetPosition)) {
				printPathMap(pathMap);
				System.out.println("The number of steps required was: " + stepCount);
				return pathMap;
			}
			stepCount++;
		}

		if (useBreakCondition) {
			printPathMap(pathMap);
			System.out.println("No solution was found.");
		}
		return pathMap;
	}

	private static String createStepSymbol(HeightJourneyModel heightJourneyModel, int stepCount, Coordinates node) {
		final char height = (char) (heightJourneyModel.getHeightMap()[node.getRow()][node.getColumn()] + 'a');
		String stepSign = " " + stepCount + height + " ";
		stepSign = stepSign.length() < 5 ? stepSign + " " : stepSign;
		return stepSign.length() < 6 ? " " + stepSign : stepSign;
	}

	private static List<Coordinates> getOptions(HeightJourneyModel heightJourneyModel, Coordinates currentPosition, List<Coordinates> closedNodes) {
		List<Coordinates> possibleNextSteps = new ArrayList<>();
		final Coordinates up = new Coordinates(currentPosition.getRow() - 1, currentPosition.getColumn());
		final Coordinates down = new Coordinates(currentPosition.getRow() + 1, currentPosition.getColumn());
		final Coordinates left = new Coordinates(currentPosition.getRow(), currentPosition.getColumn() - 1);
		final Coordinates right = new Coordinates(currentPosition.getRow(), currentPosition.getColumn() + 1);

		if (isFieldAllowed(heightJourneyModel.getHeightMap(), currentPosition, down, closedNodes)) {
			possibleNextSteps.add(down);
		}
		if (isFieldAllowed(heightJourneyModel.getHeightMap(), currentPosition, up, closedNodes)) {
			possibleNextSteps.add(up);
		}
		if (isFieldAllowed(heightJourneyModel.getHeightMap(), currentPosition, right, closedNodes)) {
			possibleNextSteps.add(right);
		}
		if (isFieldAllowed(heightJourneyModel.getHeightMap(), currentPosition, left, closedNodes)) {
			possibleNextSteps.add(left);
		}
		return possibleNextSteps;
	}

	private static boolean isFieldAllowed(int[][] heightMap, Coordinates currentCoordinates, Coordinates probedCoordinates, List<Coordinates> closedNodes) {
		if (probedCoordinates.getRow() < 0 || probedCoordinates.getColumn() < 0 || probedCoordinates.getRow() >= heightMap.length || probedCoordinates.getColumn() >= heightMap[probedCoordinates.getRow()].length) {
			return false;
		}
		int currentHeight = heightMap[currentCoordinates.getRow()][currentCoordinates.getColumn()];
		int nextHeight = heightMap[probedCoordinates.getRow()][probedCoordinates.getColumn()];
		if (currentHeight - nextHeight > 1) {
			return false;
		}
		return !closedNodes.contains(probedCoordinates);
	}

	public static void printPathMap(String[][] pathMap) {
		for (int row = 0; row < pathMap.length; row++) {
			for (int column = 0; column < pathMap[0].length; column++) {
				System.out.print(pathMap[row][column]);
			}
			System.out.println();
		}
	}
}

