package de.barryallenofearth.adventofcode2022.riddle.day23.spreadout.common.model;

import lombok.Data;

import java.util.Optional;

@Data
public class Elf {
	private final Coordinates coordinates;

	private Optional<Coordinates> proposedNextPosition = Optional.empty();
}
