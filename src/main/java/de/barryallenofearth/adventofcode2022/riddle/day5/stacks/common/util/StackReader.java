package de.barryallenofearth.adventofcode2022.riddle.day5.stacks.common.util;

import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StackReader {

	public static List<Stack<String>> readStacks(String fileName) {
		final List<String> strings = RiddleFileReader.readAllLines(fileName);

		int firstStackRow = determineFirstStackLine(strings);

		final List<Stack<String>> stacks = prepareStackList(strings, firstStackRow);

		fillStacks(stacks, strings, firstStackRow);

		return stacks;
	}



	private static void fillStacks(List<Stack<String>> stacks, List<String> strings, int firstStackRow) {
		for (int index = firstStackRow; index >= 0; index--) {
			String stackRow = strings.get(index);
			final List<String> itemsInRow = splitLine(stackRow);
			for (int stackIndex = 0; stackIndex < itemsInRow.size(); stackIndex++) {
				final String item = itemsInRow.get(stackIndex).trim().replaceAll("[\\[\\]]", "");
				if (!item.isBlank()) {
					stacks.get(stackIndex).push(item);
				}
			}
		}
	}

	private static List<Stack<String>> prepareStackList(List<String> strings, int firstStackRow) {
		final List<Stack<String>> stacks = new ArrayList<>();
		final List<String> itemsInFirstRow = splitLine(strings.get(firstStackRow));
		for (int count = 0; count < itemsInFirstRow.size(); count++) {
			stacks.add(new Stack<>());
		}
		return stacks;
	}

	private static int determineFirstStackLine(List<String> strings) {
		int firstStackRow = 0;
		for (int index = strings.size() - 1; index >= 0; index--) {
			String line = strings.get(index);
			if (line.matches("^(\\s*\\d+\\s*)+$")) {
				firstStackRow = index - 1;
				break;
			}
		}
		return firstStackRow;
	}

	private static List<String> splitLine(String line) {
		final List<String> itemsInRow = new ArrayList<>();
		for (int index = 0; index < line.length(); index = index + 4) {
			final String item;
			if (index + 4 <= line.length()) {
				item = line.substring(index, index + 4);
			} else {
				item = line.substring(index);
			}
			itemsInRow.add(item);
		}
		return itemsInRow;
	}
}
