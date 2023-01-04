package de.barryallenofearth.adventofcode2022.riddle.day23.part2;

import de.barryallenofearth.adventofcode2022.riddle.day23.common.model.Elf;
import de.barryallenofearth.adventofcode2022.riddle.day23.common.util.CountAndPrintFreeSpaces;
import de.barryallenofearth.adventofcode2022.riddle.day23.common.util.ElfPositionReader;

import java.util.List;

public class Main_23_2 {
	public static void main(String[] args) {
		final List<Elf> elves = new ElfPositionReader().readElves();

		final int numberOfRounds = new InfiniteElfMover().move(elves, -1);

		final int numberOfFreeSpaces = new CountAndPrintFreeSpaces().countAndPrint(elves);
		System.out.println("Elfes stop moving after round: " + numberOfRounds);
	}
}
