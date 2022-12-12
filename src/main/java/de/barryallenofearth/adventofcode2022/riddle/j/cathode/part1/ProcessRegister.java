package de.barryallenofearth.adventofcode2022.riddle.j.cathode.part1;

import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ProcessRegister {

	public static final String NOOP = "noop";

	public static int determineTotalSignalStrength(int startingPoint, int stepSize, int maxValue) throws IOException, URISyntaxException {
		int totalSignalStrength = 0;
		final List<String> strings = RiddleFileReader.readAllLines("riddle-10.txt");
		int registerValue = 1;
		int commandIndex = -1;
		int cyclesToComplete = 0;
		int incrementAfterCompletion = 0;
		int cycle;
		for (cycle = 1; cycle <= maxValue; cycle++) {
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
			if (cycle == startingPoint || (cycle - startingPoint) % stepSize == 0) {
				totalSignalStrength += registerValue * cycle;
				System.out.println(cycle + " " + cyclesToComplete + " " + strings.get(commandIndex) + " " + registerValue + " " + registerValue * cycle);
			}
			cyclesToComplete--;
		}

		return totalSignalStrength;
	}
}
