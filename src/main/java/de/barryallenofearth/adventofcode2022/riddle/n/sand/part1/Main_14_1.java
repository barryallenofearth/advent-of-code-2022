package de.barryallenofearth.adventofcode2022.riddle.n.sand.part1;

import de.barryallenofearth.adventofcode2022.riddle.n.sand.common.PrintCaveHelper;
import de.barryallenofearth.adventofcode2022.riddle.n.sand.common.RockCoordinatesReader;
import de.barryallenofearth.adventofcode2022.riddle.n.sand.model.CaveModel;
import de.barryallenofearth.adventofcode2022.riddle.n.sand.model.Coordinates;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class Main_14_1 {

	public static void main(String[] args) {
		final CaveModel caveModel = RockCoordinatesReader.readRockCoordinates();

		final Set<Coordinates> coordinates = SandSimulator.simulateSand(caveModel);
		PrintCaveHelper.printCaveModel(caveModel, coordinates);
	}


}
