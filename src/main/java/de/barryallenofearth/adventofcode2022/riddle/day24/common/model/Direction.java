package de.barryallenofearth.adventofcode2022.riddle.day24.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public enum Direction {

	LEFT('<', coordinates -> coordinates.setX(coordinates.getX() - 1)),
	RIGHT('>', coordinates -> coordinates.setX(coordinates.getX() + 1)),
	UP('^', coordinates -> coordinates.setY(coordinates.getY() - 1)),
	DOWN('v', coordinates -> coordinates.setY(coordinates.getY() + 1));

	private final char key;

	private final Consumer<Coordinates> move;
}
