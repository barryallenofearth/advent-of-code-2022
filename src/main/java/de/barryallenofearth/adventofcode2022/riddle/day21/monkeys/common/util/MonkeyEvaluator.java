package de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.common.util;

import de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.common.model.Monkey;
import de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.common.model.OperationType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MonkeyEvaluator {

	public static final String ROOT_MONKEY = "root";

	public static Monkey evaluate(List<Monkey> monkeyList) {

		final Map<Boolean, List<Monkey>> numberAndOperationMonkeys = monkeyList.stream().collect(Collectors.groupingBy(monkey -> monkey.getOperationType() == OperationType.NUMBER));

		Map<String, Monkey> numberMonkeys = numberAndOperationMonkeys.get(true).stream().collect(Collectors.toMap(Monkey::getName, monkey -> monkey));
		List<Monkey> operationMonkeys = numberAndOperationMonkeys.get(false);

		final Monkey rootMonkey = operationMonkeys.stream().filter(monkey -> monkey.getName().equals(ROOT_MONKEY)).findFirst().get();

		while (rootMonkey.getNumber() == null) {
			for (int index = operationMonkeys.size() - 1; index >= 0; index--) {
				Monkey operationMonkey = operationMonkeys.get(index);
				final Monkey monkey1 = numberMonkeys.get(operationMonkey.getMonkeyName1());
				final Monkey monkey2 = numberMonkeys.get(operationMonkey.getMonkeyName2());
				if (monkey1 != null && monkey2 != null) {
					operationMonkey.setNumber(operationMonkey.getOperationType().getOperation().apply(monkey1, monkey2));
					operationMonkeys.remove(operationMonkey);
					numberMonkeys.put(operationMonkey.getName(), operationMonkey);
				}
			}
		}
		return rootMonkey;
	}
}
