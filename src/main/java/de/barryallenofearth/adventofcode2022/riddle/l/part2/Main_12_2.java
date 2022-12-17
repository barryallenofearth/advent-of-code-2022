package de.barryallenofearth.adventofcode2022.riddle.l.part2;

import de.barryallenofearth.adventofcode2022.riddle.l.common.HeightMapReader;
import de.barryallenofearth.adventofcode2022.riddle.l.common.PathFinder;
import de.barryallenofearth.adventofcode2022.riddle.l.model.HeightJourneyModel;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main_12_2 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        final HeightJourneyModel heightJourneyModel = HeightMapReader.readHeightMap();
        final String[][] shortestPath = PathFinder.findShortestPath(heightJourneyModel, false);
        HeightAFinder.findBestStartingPoint(shortestPath);
    }
}
