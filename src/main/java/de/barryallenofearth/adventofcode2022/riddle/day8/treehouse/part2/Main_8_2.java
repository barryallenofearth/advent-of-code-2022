package de.barryallenofearth.adventofcode2022.riddle.day8.treehouse.part2;

import de.barryallenofearth.adventofcode2022.riddle.day8.treehouse.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.day8.treehouse.common.GridReader;
import de.barryallenofearth.adventofcode2022.riddle.day8.treehouse.part2.util.BestTreehouseSpotFinder;

public class Main_8_2 {
    public static void main(String[] args) {
        final int[][] treeGrid = GridReader.readTreeGrid();
        final Coordinates bestTreeHouseSpot = BestTreehouseSpotFinder.findBestTreeHouseSpot(treeGrid);
        for (int row = 0; row < treeGrid.length; row++) {
            for (int column = 0; column < treeGrid[row].length; column++) {
                if (bestTreeHouseSpot.getRow() == row && bestTreeHouseSpot.getColumn() == column) {
                    System.out.print("H");
                } else if (0 == row || 0 == column || row == treeGrid.length - 1 || column == treeGrid[row].length - 1) {
                    System.out.print("x");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
