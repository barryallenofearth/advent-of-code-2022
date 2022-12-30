package de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.part2;

import de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.common.model.Monkey;
import de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.common.model.OperationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class WhatIsMyNumber {

	public static final String ROOT_MONKEY = "root";

	public static final String HUMN = "humn";

	public static Monkey evaluate(List<Monkey> monkeyList) {
		Monkey humn = monkeyList.stream().filter(monkey -> monkey.getName().equals(HUMN)).findFirst().get();
		humn.setNumber(null);

		final Map<Boolean, List<Monkey>> numberAndOperationMonkeys = monkeyList.stream().collect(Collectors.groupingBy(monkey -> monkey.getOperationType() == OperationType.NUMBER));

		Map<String, Monkey> numberMonkeys = numberAndOperationMonkeys.get(true).stream().collect(Collectors.toMap(Monkey::getName, monkey -> monkey));
		List<Monkey> operationMonkeys = numberAndOperationMonkeys.get(false);
		operationMonkeys.add(numberMonkeys.get(HUMN));
		numberMonkeys.remove(HUMN);

		final Monkey rootMonkey = determineKnownRootMonkeyNumber(numberMonkeys, operationMonkeys);
		long previousNumber = numberMonkeys.get(rootMonkey.getMonkeyName1()) == null ? numberMonkeys.get(rootMonkey.getMonkeyName2()).getNumber() : numberMonkeys.get(rootMonkey.getMonkeyName1()).getNumber();
		final List<Monkey> humnBranch = determineHumnBranch(numberMonkeys, operationMonkeys, rootMonkey);
		for (int index = 0; index < humnBranch.size() - 1; index++) {
			Monkey branch = humnBranch.get(index);
			humnBranch.get(index + 1).setNumber(evaluate(numberMonkeys, previousNumber, branch));
			previousNumber = humnBranch.get(index + 1).getNumber();

		}
		return humn;
	}

	private static long evaluate(Map<String, Monkey> numberMonkeys, long previousNumber, Monkey branch) {
		final Monkey operationMonkey1 = numberMonkeys.get(branch.getMonkeyName1());
		final Monkey operationMonkey2 = numberMonkeys.get(branch.getMonkeyName2());

		if (branch.getOperationType() == OperationType.MULTIPLY) {
			if (operationMonkey1 == null) {
				return previousNumber / operationMonkey2.getNumber();
			} else {
				return previousNumber / operationMonkey1.getNumber();
			}
		} else if (branch.getOperationType() == OperationType.DIVIDE) {
			if (operationMonkey1 == null) {
				return previousNumber * operationMonkey2.getNumber();
			} else {
				return previousNumber * operationMonkey1.getNumber();
			}
		} else if (branch.getOperationType() == OperationType.ADD) {
			if (operationMonkey1 == null) {
				return previousNumber - operationMonkey2.getNumber();
			} else {
				return previousNumber - operationMonkey1.getNumber();
			}
		} else if (branch.getOperationType() == OperationType.SUBTRACT) {
			if (operationMonkey1 == null) {
				return previousNumber + operationMonkey2.getNumber();
			} else {
				return operationMonkey1.getNumber() - previousNumber;
			}
		}
		throw new IllegalStateException("No operation found for Monkey " + branch);
	}

	private static List<Monkey> determineHumnBranch(Map<String, Monkey> numberMonkeys, List<Monkey> operationMonkeys, Monkey rootMonkey) {
		List<Monkey> openMonkeys = new ArrayList<>();
		EvaluatedAndOpenMonkey evaluatedAndOpenMonkey = getEvaluatedAndOpenMonkey(numberMonkeys, operationMonkeys, rootMonkey);
		openMonkeys.add(evaluatedAndOpenMonkey.getOpen());
		while (!evaluatedAndOpenMonkey.getOpen().getName().equals(HUMN)) {
			evaluatedAndOpenMonkey = getEvaluatedAndOpenMonkey(numberMonkeys, operationMonkeys, evaluatedAndOpenMonkey.getOpen());
			openMonkeys.add(evaluatedAndOpenMonkey.getOpen());
		}
		return openMonkeys;
	}

	private static EvaluatedAndOpenMonkey getEvaluatedAndOpenMonkey(Map<String, Monkey> numberMonkeys, List<Monkey> operationMonkeys, Monkey parentMonkey) {
		if (numberMonkeys.get(parentMonkey.getMonkeyName1()) == null) {
			final Optional<Monkey> openMonkey = operationMonkeys.stream()
					.filter(monkey -> monkey.getName().equals(parentMonkey.getMonkeyName1()))
					.findFirst();
			if (openMonkey.isEmpty()) {
				throw new IllegalStateException(parentMonkey.getName() + " operation monkey1 " + parentMonkey.getMonkeyName1() + " could not be found.");
			}
			return new EvaluatedAndOpenMonkey(numberMonkeys.get(parentMonkey.getMonkeyName2()), openMonkey.get());
		} else if (numberMonkeys.get(parentMonkey.getMonkeyName2()) == null) {
			final Optional<Monkey> openMonkey = operationMonkeys.stream()
					.filter(monkey -> monkey.getName().equals(parentMonkey.getMonkeyName2()))
					.findFirst();
			if (openMonkey.isEmpty()) {
				throw new IllegalStateException(parentMonkey.getName() + " operation monkey2 " + parentMonkey.getMonkeyName2() + " could not be found.");
			}
			return new EvaluatedAndOpenMonkey(numberMonkeys.get(parentMonkey.getMonkeyName1()), openMonkey.get());
		} else {
			throw new IllegalStateException("monkey " + parentMonkey.getName() + " already completely evaluated");
		}
	}

	private static Monkey determineKnownRootMonkeyNumber(Map<String, Monkey> numberMonkeys, List<Monkey> operationMonkeys) {
		final Monkey rootMonkey = operationMonkeys.stream().filter(monkey -> monkey.getName().equals(ROOT_MONKEY)).findFirst().get();

		int numberMonkeysCount;
		do {
			numberMonkeysCount = numberMonkeys.size();
			for (int index = operationMonkeys.size() - 1; index >= 0; index--) {
				Monkey operationMonkey = operationMonkeys.get(index);
				if (operationMonkey.getName().equals(HUMN) || operationMonkey.getMonkeyName1().equals(HUMN) || operationMonkey.getMonkeyName2().equals(HUMN)) {
					continue;
				}
				final Monkey monkey1 = numberMonkeys.get(operationMonkey.getMonkeyName1());
				final Monkey monkey2 = numberMonkeys.get(operationMonkey.getMonkeyName2());
				if (monkey1 != null && monkey2 != null) {
					operationMonkey.setNumber(operationMonkey.getOperationType().getOperation().apply(monkey1, monkey2));
					operationMonkeys.remove(operationMonkey);
					numberMonkeys.put(operationMonkey.getName(), operationMonkey);
				}
			}
		} while (numberMonkeysCount != numberMonkeys.size());
		return rootMonkey;
	}
}
