package de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MoveType {
	DOWN("", new Coordinates(0, -1)),
	LEFT("<", new Coordinates(-1, 0)),
	RIGHT(">", new Coordinates(1, 0));

	private final String key;

	private final Coordinates move;

}
