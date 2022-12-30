package de.barryallenofearth.adventofcode2022.riddle.day8.treehouse.part2.util;

import de.barryallenofearth.adventofcode2022.riddle.day8.treehouse.Coordinates;

public class ViewingDinstanceFinder {

    public static int findScenicScore(int[][] treeGrid, Coordinates startCoordinates) {

        final int down = scanDown(treeGrid, startCoordinates);
        final int up = scanUp(treeGrid, startCoordinates);
        final int right = scanRight(treeGrid, startCoordinates);
        final int left = scanLeft(treeGrid, startCoordinates);

        return left * right * up * down;
    }

    private static int scanDown(int[][] treeGrid, Coordinates startCoordinates) {
        final int originalTree = treeGrid[startCoordinates.getRow()][startCoordinates.getColumn()];
        int up = 0;
        for (int row = startCoordinates.getRow() + 1; row < treeGrid.length; row++) {
            final int scannedTree = treeGrid[row][startCoordinates.getColumn()];
            if (originalTree >= scannedTree) {
                up++;
            }
            if (originalTree <= scannedTree) {
                return up;
            }
        }
        return up;
    }

    private static int scanUp(int[][] treeGrid, Coordinates startCoordinates) {
        final int originalTree = treeGrid[startCoordinates.getRow()][startCoordinates.getColumn()];
        int down = 0;
        for (int row = startCoordinates.getRow() - 1; row >= 0; row--) {
            final int scannedTree = treeGrid[row][startCoordinates.getColumn()];
            if (originalTree >= scannedTree) {
                down++;
            }
            if (originalTree <= scannedTree) {
                return down;
            }
        }
        return down;

    }

    private static int scanRight(int[][] treeGrid, Coordinates startCoordinates) {
        int right = 0;
        final int originalTree = treeGrid[startCoordinates.getRow()][startCoordinates.getColumn()];
        for (int column = startCoordinates.getColumn() + 1; column < treeGrid.length; column++) {
            final int scannedTree = treeGrid[startCoordinates.getRow()][column];
            if (originalTree >= scannedTree) {
                right++;
            }
            if (originalTree <= scannedTree) {
                return right;
            }
        }
        return right;
    }

    private static int scanLeft(int[][] treeGrid, Coordinates startCoordinates) {
        int down = 0;
        final int originalTree = treeGrid[startCoordinates.getRow()][startCoordinates.getColumn()];
        for (int column = startCoordinates.getColumn() - 1; column >= 0; column--) {
            final int scannedTree = treeGrid[startCoordinates.getRow()][column];
            if (originalTree >= scannedTree) {
                down++;
            }
            if (originalTree <= scannedTree) {
                return down;
            }
        }
        return down;
    }

}
