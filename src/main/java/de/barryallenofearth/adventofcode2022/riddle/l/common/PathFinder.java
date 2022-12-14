package de.barryallenofearth.adventofcode2022.riddle.l.common;

import de.barryallenofearth.adventofcode2022.riddle.l.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.l.model.HeightJourneyModel;
import de.barryallenofearth.adventofcode2022.riddle.l.model.PathToTop;

import java.util.*;
import java.util.stream.Collectors;

public class PathFinder {

	public static int findShortestPath(HeightJourneyModel heightJourneyModel) {
		final Coordinates startingPosition = heightJourneyModel.getStartingPosition();
		final Coordinates targetPositionPosition = heightJourneyModel.getTargetPosition();

		Queue<PathToTop> allPaths = new LinkedList<>();

		PathToTop pathToTop = new PathToTop(0, new HashSet<>(), new ArrayList<>());
		allPaths.add(pathToTop);
		pathToTop.getFieldsAlreadyVisited().add(startingPosition);
		pathToTop.setCurrentPosition(startingPosition, targetPositionPosition);

		int stepCount = 0;
		List<PathToTop> allCompletePaths = new ArrayList<>();
		while (!allPaths.isEmpty()) {

			PathToTop currentPath = allPaths.poll();
			if (currentPath.isComplete()) {
				continue;
			}

			List<Coordinates> possibleNextSteps = new ArrayList<>();
			final Coordinates left = new Coordinates(currentPath.getCurrentPosition().getRow() - 1, currentPath.getCurrentPosition().getColumn());
			final Coordinates right = new Coordinates(currentPath.getCurrentPosition().getRow() + 1, currentPath.getCurrentPosition().getColumn());
			final Coordinates up = new Coordinates(currentPath.getCurrentPosition().getRow(), currentPath.getCurrentPosition().getColumn() - 1);
			final Coordinates down = new Coordinates(currentPath.getCurrentPosition().getRow(), currentPath.getCurrentPosition().getColumn() + 1);

			String currentMovement = "";
			if (isFieldAllowed(heightJourneyModel.getHeightMap(), currentPath.getCurrentPosition(), left, currentPath.getFieldsAlreadyVisited())) {
				possibleNextSteps.add(left);
				currentMovement = "<";
			}
			if (isFieldAllowed(heightJourneyModel.getHeightMap(), currentPath.getCurrentPosition(), right, currentPath.getFieldsAlreadyVisited())) {
				possibleNextSteps.add(right);
				currentMovement = ">";
			}
			if (isFieldAllowed(heightJourneyModel.getHeightMap(), currentPath.getCurrentPosition(), up, currentPath.getFieldsAlreadyVisited())) {
				possibleNextSteps.add(up);
				currentMovement = "^";
			}
			if (isFieldAllowed(heightJourneyModel.getHeightMap(), currentPath.getCurrentPosition(), down, currentPath.getFieldsAlreadyVisited())) {
				possibleNextSteps.add(down);
				currentMovement = "v";
			}

			if (possibleNextSteps.size() > 2) {
				possibleNextSteps = possibleNextSteps.stream()
						.sorted(Comparator.comparingInt(probedPosition -> Math.abs(probedPosition.getRow() - targetPositionPosition.getRow()) + Math.abs(probedPosition.getColumn() - targetPositionPosition.getColumn())))
						.limit(2)
						.collect(Collectors.toList());
			}
			for (Coordinates possibleNextStep : possibleNextSteps) {
				final ArrayList<String> movement1 = new ArrayList<>(currentPath.getMovement());
				movement1.add(currentMovement);
				final PathToTop furtherPath = new PathToTop(currentPath.getStepCount(), new HashSet<>(currentPath.getFieldsAlreadyVisited()), movement1);
				furtherPath.setCurrentPosition(possibleNextStep, targetPositionPosition);
				if (furtherPath.isComplete()) {
					allCompletePaths.add(furtherPath);
				} else {
					allPaths.add(furtherPath);
				}

			}
			if (stepCount % 1_000 == 0) {
				System.out.println("step: " + stepCount + " complete paths: " + allCompletePaths.size() + ", investigated paths: " + allPaths.size());
				//TODO create real distinct method
				allPaths = new LinkedList<>(allPaths.stream().distinct().collect(Collectors.toList()));
				System.out.println("step: " + stepCount + " complete paths: " + allCompletePaths.size() + ", investigated paths: " + allPaths.size());
			}
			stepCount++;
		}

		final List<PathToTop> collect = allCompletePaths.stream().sorted(Comparator.comparingInt(PathToTop::getStepCount)).collect(Collectors.toList());

		return collect.get(0).getStepCount();

	}

	private static boolean isFieldAllowed(int[][] heightMap, Coordinates currentCoordinates, Coordinates probedCoordinates, Set<Coordinates> fieldsAlreadyVisited) {
		if (probedCoordinates.getRow() < 0 || probedCoordinates.getColumn() < 0 || probedCoordinates.getRow() >= heightMap.length || probedCoordinates.getColumn() >= heightMap[probedCoordinates.getRow()].length) {
			return false;
		}
		int currentHeight = heightMap[currentCoordinates.getRow()][currentCoordinates.getColumn()];
		int nextHeight = heightMap[probedCoordinates.getRow()][probedCoordinates.getColumn()];
		if (nextHeight - currentHeight > 1) {
			return false;
		}
		return !fieldsAlreadyVisited.contains(probedCoordinates);
	}
}

