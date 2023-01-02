package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.part2;

import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.MapAndInstructions;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.MyPosition;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.util.MapAndInstructionReader;

public class Main_22_2 {
	public static void main(String[] args) {
		final MapAndInstructions mapAndInstructions = new MapAndInstructionReader().read();
		final CubePathWordFinder cubePathWordFinder = new CubePathWordFinder();
		cubePathWordFinder.determineFacings(mapAndInstructions.getBoardCoordinates());

		final MyPosition finalPosition = cubePathWordFinder.findFinalPosition(mapAndInstructions);
		System.out.println(finalPosition);
		System.out.println((finalPosition.getCoordinates().getY() * 1000 + finalPosition.getCoordinates().getX() * 4 + finalPosition.getDirection().getScore()) + " is the final code.");
	}
}
