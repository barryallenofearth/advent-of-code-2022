package de.barryallenofearth.adventofcode2022.riddle.day8.treehouse.part1.util;

import de.barryallenofearth.adventofcode2022.riddle.day8.treehouse.Coordinates;

import java.util.HashSet;
import java.util.Set;

public class VisibleTreeFinder {

    public static Set<Coordinates> findAllVisibleTrees(int[][] treeGrid) {
        final Set<Coordinates> visibleTrees = new HashSet<>();
        findCorners(treeGrid, visibleTrees);

        //raster left to right, for every row
        rasterLeftToRight(treeGrid, visibleTrees);
        rasterRightToLeft(treeGrid, visibleTrees);
        rasterTopToBottom(treeGrid, visibleTrees);
        rasterBottomToTop(treeGrid, visibleTrees);

        return visibleTrees;
    }

    private static void rasterLeftToRight(int[][] treeGrid, Set<Coordinates> visibleTrees) {
        for (int row = 0; row < treeGrid.length; row++) {
            int previousMaxTreeHeight = treeGrid[row][0];
            for (int column = 1; column < treeGrid[row].length; column++) {
                final int currentTreeHeight = treeGrid[row][column];

                if (currentTreeHeight > previousMaxTreeHeight) {
                    visibleTrees.add(new Coordinates(row, column));
                    previousMaxTreeHeight = currentTreeHeight;
                }
            }
        }
    }

    private static void rasterRightToLeft(int[][] treeGrid, Set<Coordinates> visibleTrees) {
        for (int row = 0; row < treeGrid.length; row++) {
            final int rowLength = treeGrid[row].length;
            int previousMaxTreeHeight = treeGrid[row][rowLength - 1];
            for (int column = rowLength - 2; column >= 0; column--) {
                final int currentTreeHeight = treeGrid[row][column];

                if (currentTreeHeight > previousMaxTreeHeight) {
                    visibleTrees.add(new Coordinates(row, column));
                    previousMaxTreeHeight = currentTreeHeight;
                }
            }
        }
    }

    private static void rasterTopToBottom(int[][] treeGrid, Set<Coordinates> visibleTrees) {
        for (int column = 0; column < treeGrid.length; column++) {
            int previousMaxTreeHeight = treeGrid[0][column];
            for (int row = 1; row < treeGrid.length; row++) {
                final int currentTreeHeight = treeGrid[row][column];

                if (currentTreeHeight > previousMaxTreeHeight) {
                    visibleTrees.add(new Coordinates(row, column));
                    previousMaxTreeHeight = currentTreeHeight;
                }
            }
        }
    }

    private static void rasterBottomToTop(int[][] treeGrid, Set<Coordinates> visibleTrees) {
        for (int column = 0; column < treeGrid.length; column++) {
            int previousMaxTreeHeight = treeGrid[treeGrid.length - 1][column];
            for (int row = treeGrid.length - 2; row >= 0; row--) {
                final int currentTreeHeight = treeGrid[row][column];

                if (currentTreeHeight > previousMaxTreeHeight) {
                    visibleTrees.add(new Coordinates(row, column));
                    previousMaxTreeHeight = currentTreeHeight;
                }
            }
        }
    }

    private static void findCorners(int[][] treeGrid, Set<Coordinates> visibleTrees) {
        for (int row = 0; row < treeGrid.length; row++) {
            final int columnLength = treeGrid[row].length;
            for (int column = 0; column < columnLength; column++) {
                if (column == 0 || row == 0 || row == treeGrid.length - 1 || column == columnLength - 1) {
                    visibleTrees.add(new Coordinates(row, column));
                }
            }
        }
    }
}
