package de.barryallenofearth.adventofcode2022.riddle.day23.spreadout.common.util;

import de.barryallenofearth.adventofcode2022.riddle.day23.spreadout.common.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.day23.spreadout.common.model.Elf;

import java.util.List;

public class CountAndPrintFreeSpaces {

	public int countAndPrint(List<Elf> elves) {

		int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
		for (Elf elf : elves) {
			if (elf.getCoordinates().getX() > maxX) {
				maxX = elf.getCoordinates().getX();
			}
			if (elf.getCoordinates().getX() < minX) {
				minX = elf.getCoordinates().getX();
			}

			if (elf.getCoordinates().getY() > maxY) {
				maxY = elf.getCoordinates().getY();
			}
			if (elf.getCoordinates().getY() < minY) {
				minY = elf.getCoordinates().getY();
			}
		}

		int numberOfFreeSpaces = 0;
		for (int y = minY; y <= maxY; y++) {
			for (int x = minX; x <= maxX; x++) {
				final Coordinates coordinates = new Coordinates(x, y);
				if (elves.stream().anyMatch(elf -> elf.getCoordinates().equals(coordinates))) {
					System.out.print("#");
				} else {
					System.out.print(".");
					numberOfFreeSpaces++;
				}
			}
			System.out.println();
		}
		System.out.println();

		return numberOfFreeSpaces;
	}
}
