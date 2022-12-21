package de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum RockType {

	HORIZONTAL_BAR(Set.of(new Coordinates(0, 0),
			new Coordinates(1, 0),
			new Coordinates(2, 0),
			new Coordinates(3, 0))),
	CROSS(Set.of(new Coordinates(1, 0),
			new Coordinates(0, 1),
			new Coordinates(1, 1),
			new Coordinates(2, 1),
			new Coordinates(1, 2))),
	ANGLE(Set.of(new Coordinates(0, 0),
			new Coordinates(1, 0),
			new Coordinates(2, 0),
			new Coordinates(0, 1),
			new Coordinates(0, 2))),
	VERTICAL_BAR(Set.of(new Coordinates(0, 0),
			new Coordinates(0, 1),
			new Coordinates(0, 2),
			new Coordinates(0, 3))),
	SQUARE(Set.of(new Coordinates(0, 0),
			new Coordinates(1, 0),
			new Coordinates(0, 1),
			new Coordinates(1, 1)));

	private final Set<Coordinates> components;
}
