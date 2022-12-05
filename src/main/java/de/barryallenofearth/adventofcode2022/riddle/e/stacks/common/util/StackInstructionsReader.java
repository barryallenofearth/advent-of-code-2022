package de.barryallenofearth.adventofcode2022.riddle.e.stacks.common.util;

import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class StackInstructionsReader {

	public static final List<Stack<String>> readStacks(String fileName) throws IOException, URISyntaxException {
		final List<String> strings = RiddleFileReader.readAllLines(fileName);

		int firstStackRow = 0;
		for (int index = strings.size() - 1; index >= 0; index--) {
			String line = strings.get(index);
			if (line.matches("^\\s+\\d+\\s+$")) {
				firstStackRow = index - 1;
				break;
			}
		}
		for (int index = firstStackRow; index >= 0; index--) {
			String stackRow = strings.get(index);

		}

		final List<String[]> collect = strings.stream().map(string -> string.split(" ")).collect(Collectors.toList());
		List<Stack<String>> stacks = new ArrayList<>();

		return stacks;
	}
}
