package de.barryallenofearth.adventofcode2022.riddle.day23.spreadout.common.util;

import de.barryallenofearth.adventofcode2022.riddle.day23.spreadout.common.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.day23.spreadout.common.model.Elf;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.util.ArrayList;
import java.util.List;

public class ElfPositionReader {

	public List<Elf> readElves() {
		final List<Elf> elves = new ArrayList<>();
		final List<String> strings = RiddleFileReader.readAllLines("riddle-23.txt");

		for (int y = 0; y < strings.size(); y++) {
			final String line = strings.get(y);
			for (int x = 0; x < line.length(); x++) {
				if (line.charAt(x) == '#') {
					elves.add(new Elf(new Coordinates(x, y)));
				}
			}
		}
		return elves;
	}
}
