package de.barryallenofearth.adventofcode2022.riddle.h.treehouse.common;

import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class GridReader {

    public static int[][] readTreeGrid() {
        final List<String> strings = RiddleFileReader.readAllLines("riddle-8.txt");
        final int[][] treeGrid = new int[strings.size()][strings.get(0).length()];

        for (int row = 0; row < strings.size(); row++) {
            String string = strings.get(row);
            for (int column = 0; column < string.length(); column++) {
                treeGrid[row][column] = Integer.parseInt(String.valueOf(string.charAt(column)));
            }
        }
        return treeGrid;
    }
}
