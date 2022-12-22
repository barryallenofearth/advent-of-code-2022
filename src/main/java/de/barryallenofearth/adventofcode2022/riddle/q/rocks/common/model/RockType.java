package de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum RockType {

	HORIZONTAL_BAR(List.of(new Coordinates(0, 0),
			new Coordinates(1, 0),
			new Coordinates(2, 0),
			new Coordinates(3, 0))),
	CROSS(List.of(new Coordinates(1, 0),
			new Coordinates(0, 1),
			new Coordinates(1, 1),
			new Coordinates(2, 1),
			new Coordinates(1, 2))),
	ANGLE(List.of(new Coordinates(0, 0),
			new Coordinates(1, 0),
			new Coordinates(2, 0),
			new Coordinates(2, 1),
			new Coordinates(2, 2))),
	VERTICAL_BAR(List.of(new Coordinates(0, 0),
			new Coordinates(0, 1),
			new Coordinates(0, 2),
			new Coordinates(0, 3))),
	SQUARE(List.of(new Coordinates(0, 0),
			new Coordinates(1, 0),
			new Coordinates(0, 1),
			new Coordinates(1, 1)));

	private final List<Coordinates> components;
}
