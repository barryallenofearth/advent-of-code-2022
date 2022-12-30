package de.barryallenofearth.adventofcode2022.riddle.day14.sand.part1;

import de.barryallenofearth.adventofcode2022.riddle.day14.sand.common.PrintCaveHelper;
import de.barryallenofearth.adventofcode2022.riddle.day14.sand.common.RockCoordinatesReader;
import de.barryallenofearth.adventofcode2022.riddle.day14.sand.model.CaveModel;
import de.barryallenofearth.adventofcode2022.riddle.day14.sand.model.Coordinates;

import java.util.Set;

public class Main_14_1 {

	public static void main(String[] args) {
		final CaveModel caveModel = RockCoordinatesReader.readRockCoordinates();

		final Set<Coordinates> coordinates = SandSimulator.simulateSand(caveModel);
		PrintCaveHelper.printCaveModel(caveModel, coordinates);
	}


}
