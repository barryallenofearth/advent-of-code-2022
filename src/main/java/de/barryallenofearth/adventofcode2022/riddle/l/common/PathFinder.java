package de.barryallenofearth.adventofcode2022.riddle.l.common;

import de.barryallenofearth.adventofcode2022.riddle.l.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.l.model.HeightJourneyModel;

import java.util.HashSet;
import java.util.Set;

public class PathFinder {

	public int findShortestPath(HeightJourneyModel heightJourneyModel) {
		final Coordinates startingPosition = heightJourneyModel.getStartingPosition();
		final Coordinates targetPositionPosition = heightJourneyModel.getTargetPosition();
		final Set<Coordinates> fieldsAlreadyVisited = new HashSet<>();

		int numberOfSteps = 0;
		int minimumNumberOfSteps = Integer.MAX_VALUE;

		return minimumNumberOfSteps;

	}

	private static boolean isFieldAllowed(int[][] heightMap, Coordinates currentCoordinates, Coordinates probedCoordinates, Set<Coordinates> fieldsAlreadyVisited) {
		int currentHeight = heightMap[currentCoordinates.getRow()][currentCoordinates.getColumn()];
		int nextHeight = heightMap[probedCoordinates.getRow()][probedCoordinates.getColumn()];
		if (nextHeight - currentHeight > 1) {
			return false;
		}
		return !fieldsAlreadyVisited.contains(probedCoordinates);
	}
}

