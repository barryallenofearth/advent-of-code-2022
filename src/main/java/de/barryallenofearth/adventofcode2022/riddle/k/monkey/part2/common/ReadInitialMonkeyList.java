package de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.common;

import de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.model.Item;
import de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.model.Monkey;
import de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.model.Operation;
import de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.model.OperationPair;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadInitialMonkeyList {

	public static final String MONKEY_ = "Monkey ";

	public static final String STARTING_ITEMS = "  Starting items: ";

	public static final String OPERATION = "  Operation: new = ";

	public static final String TEST_DIVISIBLE_BY = "  Test: divisible by ";

	public static final String IF_TRUE_THROW_TO_MONKEY = "    If true: throw to monkey ";

	public static final String IF_FALSE_THROW_TO_MONKEY = "    If false: throw to monkey ";

	public static final Pattern OPERATION_PATTERN = Pattern.compile("(\\w+)\\s+([+*])\\s+(\\w+)");

	public static final String OLD = "old";

	public static List<Monkey> readMonkeys() {
		final List<String> strings = RiddleFileReader.readAllLines("riddle-11.txt");
		List<Monkey> monkeys = new ArrayList<>();

		Monkey currentMonkey = null;
		for (String string : strings) {
			if (string.startsWith(MONKEY_)) {
				currentMonkey = new Monkey(Integer.parseInt(string.replace(MONKEY_, "").replace(":", "")));
				monkeys.add(currentMonkey);
			} else if (string.startsWith(STARTING_ITEMS)) {
				final String[] split = string.replace(STARTING_ITEMS, "").split(",\\s+");
				for (String item : split) {
					currentMonkey.getItems().add(new Item(Integer.parseInt(item)));
				}
			} else if (string.startsWith(OPERATION)) {
				currentMonkey.setOperationString(string.replace(OPERATION, ""));
			} else if (string.startsWith(TEST_DIVISIBLE_BY)) {
				final String divisor = string.replace(TEST_DIVISIBLE_BY, "");
				currentMonkey.setTestDivisor(Integer.parseInt(divisor));
			} else if (string.startsWith(IF_TRUE_THROW_TO_MONKEY)) {
				currentMonkey.setTestTrueMonkeyIndex(Integer.parseInt(string.replace(IF_TRUE_THROW_TO_MONKEY, "")));
			} else if (string.startsWith(IF_FALSE_THROW_TO_MONKEY)) {
				currentMonkey.setTestFalseMonkeyIndex(Integer.parseInt(string.replace(IF_FALSE_THROW_TO_MONKEY, "")));
			}
		}
		monkeys.forEach(monkey -> monkey.setOperation(parseWorryLevelOperation(monkey)));

		return monkeys;
	}

	public static Consumer<Item> parseWorryLevelOperation(Monkey monkey) {
		final Matcher matcher = OPERATION_PATTERN.matcher(monkey.getOperationString());
		if (matcher.matches()) {
			final String firstSymbol = matcher.group(1);
			final String operator = matcher.group(2);
			final String secondSymbol = matcher.group(3);

			if (operator.equals("*")) {
				if (firstSymbol.equals(OLD) && secondSymbol.equals(OLD)) {
					return item -> item.getAppliedOperations().add(new OperationPair(Operation.SQUARE, 0));
				} else if (firstSymbol.equals(OLD) && secondSymbol.matches("\\d+")) {
					return item -> item.getAppliedOperations().add(new OperationPair(Operation.MULTIPLY, Integer.parseInt(secondSymbol)));
				}
			} else if (operator.equals("+")) {
				if (firstSymbol.equals(OLD) && secondSymbol.matches("\\d+")) {
					return item -> item.getAppliedOperations().add(new OperationPair(Operation.ADD, Integer.parseInt(secondSymbol)));
				}
			}
		}
		return null;
	}
}
