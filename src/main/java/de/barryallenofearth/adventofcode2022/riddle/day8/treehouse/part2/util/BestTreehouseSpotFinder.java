package de.barryallenofearth.adventofcode2022.riddle.day8.treehouse.part2.util;

import de.barryallenofearth.adventofcode2022.riddle.day8.treehouse.Coordinates;

public class BestTreehouseSpotFinder {

    public static Coordinates findBestTreeHouseSpot(int[][] treeGrid) {

        final int gridSize = treeGrid.length;

        int maximumScenicScore = -1;
        Coordinates maximumCoordinates = new Coordinates(0, 0);

        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                final int scenicScore = ViewingDinstanceFinder.findScenicScore(treeGrid, new Coordinates(row, column));
                if (scenicScore > maximumScenicScore) {
                    maximumScenicScore = scenicScore;
                    maximumCoordinates = new Coordinates(row, column);
                }
            }
        }
        System.out.println(maximumScenicScore + " is the maximum scenic score.");
        return maximumCoordinates;
    }

}
