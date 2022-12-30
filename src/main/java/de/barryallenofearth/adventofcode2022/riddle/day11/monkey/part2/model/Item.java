package de.barryallenofearth.adventofcode2022.riddle.day11.monkey.part2.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Item {
	private final int worryLevel;

	private final List<OperationPair> appliedOperations = new ArrayList<>();
}
