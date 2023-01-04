package de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.util;

import de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.model.*;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.util.ArrayList;
import java.util.List;

public class ValleyReader {

	public InitialState read() {
		final List<String> strings = RiddleFileReader.readAllLines("riddle-24.txt");

		Valley valley = new Valley();
		final List<Blizzard> blizzardList = new ArrayList<>();
		for (int y = 0; y < strings.size(); y++) {
			String line = strings.get(y);
			for (int x = 0; x < line.length(); x++) {
				final char currentChar = line.charAt(x);
				final Coordinates coordinates = new Coordinates(x, y);
				if (currentChar != '#') {
					valley.getFields().add(coordinates);
					if (y == 0) {
						valley.setEntry(coordinates);
					} else if (y == strings.size() - 1) {
						valley.setExit(coordinates);
					}

					final Direction direction = Direction.getByKey(currentChar);
					if (direction != null) {
						blizzardList.add(new Blizzard(new Coordinates(x, y), direction));
					}
				}
			}

		}

		valley.setMaxY(strings.size() - 2);
		valley.setMaxX(strings.get(0).length() - 2);
		return new InitialState(valley, blizzardList);
	}
}
