package de.barryallenofearth.adventofcode2022.riddle.day23.part1;

import de.barryallenofearth.adventofcode2022.riddle.day23.common.model.Elf;
import de.barryallenofearth.adventofcode2022.riddle.day23.common.util.CountAndPrintFreeSpaces;
import de.barryallenofearth.adventofcode2022.riddle.day23.common.util.ElfMover;
import de.barryallenofearth.adventofcode2022.riddle.day23.common.util.ElfPositionReader;

import java.util.List;

public class Main_23_1 {
	public static void main(String[] args) {
		final List<Elf> elves = new ElfPositionReader().readElves();

		new ElfMover().moveForXRounds(elves, 10);

		final int numberOfFreeSpaces = new CountAndPrintFreeSpaces().countAndPrint(elves);
		System.out.println("The number of free spaces is: " + numberOfFreeSpaces);
	}
}
