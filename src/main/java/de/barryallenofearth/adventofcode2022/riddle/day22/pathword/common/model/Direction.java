package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public enum Direction {
	RIGHT(">", 0, coordinates -> modfifyX(coordinates, 1), coordinates -> modfifyX(coordinates, -1)),
	DOWN("v", 1, coordinates -> modfifyY(coordinates, 1), coordinates -> modfifyY(coordinates, -1)),
	LEFT("<", 2, coordinates -> modfifyX(coordinates, -1), coordinates -> modfifyX(coordinates, 1)),
	UP("^", 3, coordinates -> modfifyY(coordinates, -1), coordinates -> modfifyY(coordinates, 1));

	private static void modfifyX(Coordinates coordinates, int step) {
		coordinates.setX(coordinates.getX() + step);
	}

	private static void modfifyY(Coordinates coordinates, int step) {
		coordinates.setY(coordinates.getY() + step);
	}

	private final String symbol;

	private final int score;

	private final Consumer<Coordinates> move;

	private final Consumer<Coordinates> reverse;

}
