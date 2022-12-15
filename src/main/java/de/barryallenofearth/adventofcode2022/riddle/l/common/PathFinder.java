package de.barryallenofearth.adventofcode2022.riddle.l.common;

import de.barryallenofearth.adventofcode2022.riddle.l.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.l.model.HeightJourneyModel;
import de.barryallenofearth.adventofcode2022.riddle.l.model.PathToTop;

import java.util.*;
import java.util.stream.Collectors;

public class PathFinder {

    public static PathToTop findShortestPath(HeightJourneyModel heightJourneyModel) {
        final Coordinates startingPosition = heightJourneyModel.getStartingPosition();
        final Coordinates targetPositionPosition = heightJourneyModel.getTargetPosition();


        PathToTop currentPath = new PathToTop(startingPosition, targetPositionPosition);
        while (!currentPath.isComplete()) {

            List<Coordinates> possibleNextSteps = getOptions(heightJourneyModel, currentPath);

            final int limit = 1;
            if (possibleNextSteps.size() > limit) {
                possibleNextSteps = possibleNextSteps.stream()
                        .sorted((probedPosition1, probedPosition2) -> {
                            double distanceNumber1 = Math.sqrt(Math.pow(probedPosition1.getRow() - targetPositionPosition.getRow(), 2) + Math.pow(probedPosition1.getColumn() - targetPositionPosition.getColumn(), 2));
                            double distanceNumber2 = Math.sqrt(Math.pow(probedPosition2.getRow() - targetPositionPosition.getRow(), 2) + Math.pow(probedPosition2.getColumn() - targetPositionPosition.getColumn(), 2));
                            return (int) ((distanceNumber1 - distanceNumber2) * 100);
                        })
                        .limit(limit)
                        .collect(Collectors.toList());
            } else if (possibleNextSteps.isEmpty()) {
                while (getOptions(heightJourneyModel, currentPath).isEmpty()) {
                    currentPath.goBack();
                }
                possibleNextSteps = getOptions(heightJourneyModel, currentPath);
            }
            currentPath.setCurrentPosition(possibleNextSteps.get(0));

        }


        return currentPath;

    }

    private static List<Coordinates> getOptions(HeightJourneyModel heightJourneyModel, PathToTop path) {
        List<Coordinates> possibleNextSteps = new ArrayList<>();
        final Coordinates up = new Coordinates(path.getCurrentPosition().getRow() - 1, path.getCurrentPosition().getColumn());
        final Coordinates down = new Coordinates(path.getCurrentPosition().getRow() + 1, path.getCurrentPosition().getColumn());
        final Coordinates left = new Coordinates(path.getCurrentPosition().getRow(), path.getCurrentPosition().getColumn() - 1);
        final Coordinates right = new Coordinates(path.getCurrentPosition().getRow(), path.getCurrentPosition().getColumn() + 1);

        if (isFieldAllowed(heightJourneyModel.getHeightMap(), path.getCurrentPosition(), down, path)) {
            possibleNextSteps.add(down);
        }
        if (isFieldAllowed(heightJourneyModel.getHeightMap(), path.getCurrentPosition(), up, path)) {
            possibleNextSteps.add(up);
        }
        if (isFieldAllowed(heightJourneyModel.getHeightMap(), path.getCurrentPosition(), right, path)) {
            possibleNextSteps.add(right);
        }
        if (isFieldAllowed(heightJourneyModel.getHeightMap(), path.getCurrentPosition(), left, path)) {
            possibleNextSteps.add(left);
        }
        return possibleNextSteps;
    }

    private static boolean isFieldAllowed(int[][] heightMap, Coordinates currentCoordinates, Coordinates probedCoordinates, PathToTop pathToTop) {
        if (probedCoordinates.getRow() < 0 || probedCoordinates.getColumn() < 0 || probedCoordinates.getRow() >= heightMap.length || probedCoordinates.getColumn() >= heightMap[probedCoordinates.getRow()].length) {
            return false;
        }
        int currentHeight = heightMap[currentCoordinates.getRow()][currentCoordinates.getColumn()];
        int nextHeight = heightMap[probedCoordinates.getRow()][probedCoordinates.getColumn()];
        if (nextHeight - currentHeight > 1) {
            return false;
        }
        return !pathToTop.getFieldsAlreadyVisited().contains(probedCoordinates) && !pathToTop.getDeadEnds().contains(probedCoordinates) && !Objects.equals(pathToTop.getLastPositionWhileMovingBackwards(), probedCoordinates);
    }
}

