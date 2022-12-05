package de.barryallenofearth.adventofcode2022.riddle.e.stacks.common.model;

import lombok.Data;

@Data
public class CommandModel {

	private final int numberOfCrates;

	private final int startingStackIndex;

	private final int endingStackIndex;
}
