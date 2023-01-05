package de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum Direction {

	LEFT('<', coordinates -> new Coordinates(coordinates.getX() - 1, coordinates.getY())),
	RIGHT('>', coordinates -> new Coordinates(coordinates.getX() + 1, coordinates.getY())),
	UP('^', coordinates -> new Coordinates(coordinates.getX(), coordinates.getY() - 1)),
	DOWN('v', coordinates -> new Coordinates(coordinates.getX(), coordinates.getY() + 1));

	private final char key;

	private final Function<Coordinates, Coordinates> move;

	public static Direction getByKey(char key) {
		for (Direction direction : Direction.values()) {
			if (direction.getKey() == key) {
				return direction;
			}
		}
		return null;
	}
}
