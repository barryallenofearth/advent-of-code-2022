package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.part1;

import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.MapAndInstructions;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.util.MapAndInstructionReader;

public class Main_22_1 {
	public static void main(String[] args) {
		final MapAndInstructions mapAndInstructions = new MapAndInstructionReader().read();
		System.out.println(mapAndInstructions);
	}
}
