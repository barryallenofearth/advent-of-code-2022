package de.barryallenofearth.adventofcode2022.riddle.n.common;

import de.barryallenofearth.adventofcode2022.riddle.n.model.CaveModel;
import de.barryallenofearth.adventofcode2022.riddle.n.model.Coordinates;

import java.util.Set;

import static de.barryallenofearth.adventofcode2022.riddle.n.part1.SandSimulator.SAND_START_COORDINATES;

public class PrintCaveHelper {
	public static void printCaveModel(CaveModel caveModel, Set<Coordinates> sandRestingCoordinates) {

		int xSpan = caveModel.getMaxX() - caveModel.getMinX();
		for (int column = 0; column <= caveModel.getMaxY() + 2; column++) {
			for (int row = caveModel.getMinX() - xSpan; row <= caveModel.getMaxX() + 1 + xSpan; row++) {
				Coordinates currentCoordinates = new Coordinates(row, column);
				if (currentCoordinates.equals(SAND_START_COORDINATES)) {
					System.out.print("+");
				} else if (sandRestingCoordinates.contains(currentCoordinates)) {
					System.out.print("o");
				} else if (caveModel.getRockCoordinates().contains(currentCoordinates)) {
					System.out.print("#");
				} else if (column == caveModel.getMaxY() + 2) {
					System.out.print("#");
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
	}
}
