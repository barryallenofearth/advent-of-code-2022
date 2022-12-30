package de.barryallenofearth.adventofcode2022.riddle.day10.cathode.part2;

import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.util.List;

public class DisplayScreen {
	public static final String NOOP = "noop";

	public static void printImage() {
		final List<String> strings = RiddleFileReader.readAllLines("riddle-10.txt");
		int registerValue = 1;
		int commandIndex = -1;
		int cyclesToComplete = 0;
		int incrementAfterCompletion = 0;
		int cycle;
		for (cycle = 1; cycle <= 240; cycle++) {
			if (cyclesToComplete == 0) {
				registerValue += incrementAfterCompletion;
				incrementAfterCompletion = 0;
				commandIndex++;
				if (commandIndex == strings.size()) {
					break;
				}
				final String currentCommand = strings.get(commandIndex);
				if (currentCommand.startsWith("addx ")) {
					incrementAfterCompletion = Integer.parseInt(currentCommand.replace("addx ", ""));
					cyclesToComplete = 2;
				} else if (currentCommand.equals(NOOP)) {
					cyclesToComplete = 1;
				}
			}
			if (cycle % 40 >= registerValue && cycle % 40 <= registerValue + 2) {
				System.out.print('#');
			} else {
				System.out.print('.');
			}
			if (cycle % 40 == 0) {
				System.out.println();
			}
			cyclesToComplete--;
		}

	}
}
