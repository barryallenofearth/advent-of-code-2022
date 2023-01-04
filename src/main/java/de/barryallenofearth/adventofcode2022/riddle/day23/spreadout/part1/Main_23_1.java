package de.barryallenofearth.adventofcode2022.riddle.day23.spreadout.part1;

import de.barryallenofearth.adventofcode2022.riddle.day23.spreadout.common.model.Elf;
import de.barryallenofearth.adventofcode2022.riddle.day23.spreadout.common.util.CountAndPrintFreeSpaces;
import de.barryallenofearth.adventofcode2022.riddle.day23.spreadout.common.util.ElfPositionReader;

import java.util.List;

public class Main_23_1 {
	public static void main(String[] args) {
		final List<Elf> elves = new ElfPositionReader().readElves();

		new RoundBasedElfMover().move(elves, 10);

		final int numberOfFreeSpaces = new CountAndPrintFreeSpaces().countAndPrint(elves);
		System.out.println("The number of free spaces is: " + numberOfFreeSpaces);
	}
}
