package de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationPair {
	private final Operation operation;

	private final int number;
}
