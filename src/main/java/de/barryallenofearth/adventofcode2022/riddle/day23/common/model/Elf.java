package de.barryallenofearth.adventofcode2022.riddle.day23.common.model;

import lombok.Data;

@Data
public class Elf {
	private final Coordinates coordinates;

	private Coordinates proposedNextPosition;
}
