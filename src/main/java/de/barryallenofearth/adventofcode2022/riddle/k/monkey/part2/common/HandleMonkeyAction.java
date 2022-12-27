package de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.common;

import de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.model.Item;
import de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.model.Monkey;
import de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.model.Operation;
import de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.model.OperationPair;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class HandleMonkeyAction {

	public static void handleInspection(List<Monkey> monkeys, int monkeyIndex) {
		final Monkey monkey = monkeys.get(monkeyIndex);
		while (monkey.getItems().size() > 0) {
			final Item item = monkey.getItems().poll();
			monkey.getOperation().accept(item);

			monkey.setItemsInspected(monkey.getItemsInspected() + 1);

			final int nextMonkey;
			if (testItem(item, monkey.getTestDivisor())) {
				nextMonkey = monkey.getTestTrueMonkeyIndex();
			} else {
				nextMonkey = monkey.getTestFalseMonkeyIndex();
			}
			monkeys.get(nextMonkey).getItems().add(item);
		}
	}

	private static boolean testItem(Item item, int testDivisor) {
		long modularValue = item.getWorryLevel() % testDivisor;
		List<OperationPair> appliedOperations = item.getAppliedOperations();
		for (OperationPair appliedOperation : appliedOperations) {
			if (appliedOperation.getOperation() == Operation.ADD) {
				modularValue += appliedOperation.getNumber();
			} else if (appliedOperation.getOperation() == Operation.MULTIPLY) {
				modularValue *= appliedOperation.getNumber();
			} else if (appliedOperation.getOperation() == Operation.SQUARE) {
				modularValue = (modularValue * modularValue) % testDivisor;
			}
			modularValue = modularValue % testDivisor;
		}
		return modularValue == 0;
	}
}
