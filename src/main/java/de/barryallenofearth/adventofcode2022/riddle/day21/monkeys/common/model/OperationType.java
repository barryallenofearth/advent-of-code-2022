package de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.function.BiFunction;

@Getter
@RequiredArgsConstructor
public enum OperationType {
	NUMBER(null, null),
	ADD("+", ((monkey1, monkey2) -> monkey1.getNumber() + monkey2.getNumber())),
	SUBTRACT("-", ((monkey1, monkey2) -> monkey1.getNumber() - monkey2.getNumber())),
	MULTIPLY("*", ((monkey1, monkey2) -> monkey1.getNumber() * monkey2.getNumber())),
	DIVIDE("/", ((monkey1, monkey2) -> monkey1.getNumber() / monkey2.getNumber()));

	private final String identifier;

	private final BiFunction<Monkey, Monkey, Long> operation;

	public static OperationType getByIndentifier(String identifier) {
		for (OperationType operationType : OperationType.values()) {
			if (Objects.equals(operationType.getIdentifier(), identifier)) {
				return operationType;
			}
		}
		throw new IllegalArgumentException("No Operation Type could be found for identifier '" + identifier + "'");
	}
}
