package de.barryallenofearth.adventofcode2022.riddle.l.pathtotop.part1;

import de.barryallenofearth.adventofcode2022.riddle.l.pathtotop.common.HeightMapReader;
import de.barryallenofearth.adventofcode2022.riddle.l.pathtotop.common.PathFinder;
import de.barryallenofearth.adventofcode2022.riddle.l.pathtotop.model.HeightJourneyModel;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main_12_1 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		final HeightJourneyModel heightJourneyModel = HeightMapReader.readHeightMap();
		PathFinder.findShortestPath(heightJourneyModel, true);

	}
}
