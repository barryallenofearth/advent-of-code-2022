package de.barryallenofearth.adventofcode2022.riddle.day8.treehouse.part1;

import de.barryallenofearth.adventofcode2022.riddle.day8.treehouse.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.day8.treehouse.common.GridReader;
import de.barryallenofearth.adventofcode2022.riddle.day8.treehouse.part1.util.VisibleTreeFinder;

import java.util.Set;

public class Main_8_1 {
    public static void main(String[] args) {
        final int[][] treeGrid = GridReader.readTreeGrid();

        final Set<Coordinates> visibleTrees = VisibleTreeFinder.findAllVisibleTrees(treeGrid);

        for (int row = 0; row < treeGrid.length; row++) {
            for (int column = 0; column < treeGrid[row].length; column++) {
                if (visibleTrees.contains(new Coordinates(row, column))) {
                    System.out.print("x");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        System.out.println(visibleTrees.size() + " trees can be seen from outside the grid.");
    }
}
