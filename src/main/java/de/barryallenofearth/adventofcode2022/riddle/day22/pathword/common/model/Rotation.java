package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum Rotation {
	LEFT("L", direction -> {
		switch (direction) {
		case LEFT:
			return Direction.DOWN;
		case RIGHT:
			return Direction.UP;
		case DOWN:
			return Direction.RIGHT;
		case UP:
			return Direction.LEFT;
		}
		throw new IllegalArgumentException("no direction case matched: " + direction);
	}),
	RIGHT("R", direction -> {
		switch (direction) {
		case LEFT:
			return Direction.UP;
		case RIGHT:
			return Direction.DOWN;
		case DOWN:
			return Direction.LEFT;
		case UP:
			return Direction.RIGHT;
		}
		throw new IllegalArgumentException("no direction case matched: " + direction);
	});

	private final String identifier;

	private final Function<Direction, Direction> rotate;

	public static Rotation getByIdentifiert(String identifier) {
		for (Rotation value : Rotation.values()) {
			if (value.getIdentifier().equals(identifier)) {
				return value;
			}
		}
		throw new IllegalArgumentException("no rotation found for identifier " + identifier);
	}
}
