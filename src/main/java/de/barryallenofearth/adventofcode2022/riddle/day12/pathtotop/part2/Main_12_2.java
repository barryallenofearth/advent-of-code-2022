package de.barryallenofearth.adventofcode2022.riddle.day12.pathtotop.part2;

import de.barryallenofearth.adventofcode2022.riddle.day12.pathtotop.common.HeightMapReader;
import de.barryallenofearth.adventofcode2022.riddle.day12.pathtotop.common.PathFinder;
import de.barryallenofearth.adventofcode2022.riddle.day12.pathtotop.model.HeightJourneyModel;

public class Main_12_2 {
    public static void main(String[] args) {
        final HeightJourneyModel heightJourneyModel = HeightMapReader.readHeightMap();
        final String[][] shortestPath = PathFinder.findShortestPath(heightJourneyModel, false);
        HeightAFinder.findBestStartingPoint(shortestPath);
    }
}
