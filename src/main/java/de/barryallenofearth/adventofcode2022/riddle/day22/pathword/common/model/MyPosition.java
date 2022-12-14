package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model;

import lombok.Data;

@Data
public class MyPosition {
	//y value is initially set, x  needs to be found
	private Coordinates coordinates = new Coordinates(0, 1);

	private Direction direction = Direction.RIGHT;
}
