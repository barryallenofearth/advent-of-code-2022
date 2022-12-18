package de.barryallenofearth.adventofcode2022.riddle.n.part1;

import de.barryallenofearth.adventofcode2022.riddle.n.common.RockCoordinatesReader;
import de.barryallenofearth.adventofcode2022.riddle.n.model.CaveModel;
import de.barryallenofearth.adventofcode2022.riddle.n.model.Coordinates;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class Main_14_1 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		final CaveModel caveModel = RockCoordinatesReader.readRockCoordinates();
		printCaveModel(caveModel);
	}

	private static void printCaveModel(CaveModel caveModel) {
		for (int row = caveModel.getMinX() - 1; row <= caveModel.getMaxX() + 1; row++) {
			for (int column = caveModel.getMinY() - 1; column <= caveModel.getMaxY() + 1; column++) {
				Coordinates currentCoordinates = new Coordinates(row, column);
				if (caveModel.getRockCoordinates().contains(currentCoordinates)) {
					System.out.print("#");
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
	}
}
