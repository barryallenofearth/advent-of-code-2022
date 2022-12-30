package de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Monkey {

	private final String name;

	private Long number = null;

	private String monkeyName1;

	private String monkeyName2;

	private final OperationType operationType;

}
