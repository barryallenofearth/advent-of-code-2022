package de.barryallenofearth.adventofcode2022.riddle.l.part1;

import de.barryallenofearth.adventofcode2022.riddle.l.common.HeightMapReader;
import de.barryallenofearth.adventofcode2022.riddle.l.common.PathFinder;
import de.barryallenofearth.adventofcode2022.riddle.l.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.l.model.HeightJourneyModel;
import de.barryallenofearth.adventofcode2022.riddle.l.model.PathToTop;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main_12_1 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        final HeightJourneyModel heightJourneyModel = HeightMapReader.readHeightMap();
        final PathToTop shortestPath = PathFinder.findShortestPath(heightJourneyModel);

        for (int row = 0; row < heightJourneyModel.getHeightMap().length; row++) {
            for (int column = 0; column < heightJourneyModel.getHeightMap()[0].length; column++) {
                final Coordinates coordinates = new Coordinates(row, column);
                if (heightJourneyModel.getStartingPosition().equals(coordinates)) {
                    System.out.print("  Sa ");
                } else if (heightJourneyModel.getTargetPosition().equals(coordinates)) {
                    System.out.print("  Ez ");
                } else if (shortestPath.getFieldsAlreadyVisited().contains(coordinates)) {
                    final int index = shortestPath.getFieldsAlreadyVisited().indexOf(coordinates);
                    System.out.print(" " + (index < 10 ? " " : "") + index + Character.toString(heightJourneyModel.getHeightMap()[row][column] + 'a') + (index < 100 ? " " : ""));
                } else {
                    System.out.print("  ." + Character.toString(heightJourneyModel.getHeightMap()[row][column] + 'a') + " ");
                }
            }
            System.out.println();
        }
        System.out.println(shortestPath.getStepCount() + " steps are required to reach the top.");
    }
}
