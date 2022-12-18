package de.barryallenofearth.adventofcode2022.riddle.n.common;

import de.barryallenofearth.adventofcode2022.riddle.n.model.CaveModel;
import de.barryallenofearth.adventofcode2022.riddle.n.model.Coordinates;

import java.util.Set;

import static de.barryallenofearth.adventofcode2022.riddle.n.common.SandSimulator.SAND_START_COORDINATES;

public class PrintCaveHelper {
	public static void printCaveModel(CaveModel caveModel, Set<Coordinates> sandRestingCoordinates) {

		for (int column = 0; column <= caveModel.getMaxY() + 1; column++) {
			for (int row = caveModel.getMinX() - 1; row <= caveModel.getMaxX() + 1; row++) {
				Coordinates currentCoordinates = new Coordinates(row, column);
				if (currentCoordinates.equals(SAND_START_COORDINATES)) {
					System.out.print("+");
				} else if (sandRestingCoordinates.contains(currentCoordinates)) {
					System.out.print("o");
				} else if (caveModel.getRockCoordinates().contains(currentCoordinates)) {
					System.out.print("#");
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
	}
}
