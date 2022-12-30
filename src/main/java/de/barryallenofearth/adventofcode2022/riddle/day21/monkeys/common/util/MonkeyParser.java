package de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.common.util;

import de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.common.model.Monkey;
import de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.common.model.OperationType;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonkeyParser {

	public static final Pattern NUMBER_MONKEY = Pattern.compile("^(\\w+)\\s*:\\s*(\\d+)\\s*$");

	public static final Pattern OPERATION_MONKEY = Pattern.compile("^(\\w+)\\s*:\\s*(\\w+)\\s*([-+/*])\\s*(\\w+)\\s*$");

	public static List<Monkey> parseMonkeys() {
		List<Monkey> monkeyList = new ArrayList<>();

		final List<String> strings = RiddleFileReader.readAllLines("riddle-21.txt");
		for (String string : strings) {
			Matcher numberMatcher = NUMBER_MONKEY.matcher(string);
			if (numberMatcher.matches()) {
				final Monkey monkey = new Monkey(numberMatcher.group(1), OperationType.NUMBER);
				monkey.setNumber(Long.parseLong(numberMatcher.group(2)));
				monkeyList.add(monkey);
				continue;
			}
			Matcher operationMatcher = OPERATION_MONKEY.matcher(string);
			if (operationMatcher.matches()) {
				Monkey monkey = new Monkey(operationMatcher.group(1), null, operationMatcher.group(2), operationMatcher.group(4), OperationType.getByIndentifier(operationMatcher.group(3)));
				monkeyList.add(monkey);
				continue;
			}
			throw new IllegalStateException("Monkey could not be parsed: " + string);
		}
		return monkeyList;
	}
}
