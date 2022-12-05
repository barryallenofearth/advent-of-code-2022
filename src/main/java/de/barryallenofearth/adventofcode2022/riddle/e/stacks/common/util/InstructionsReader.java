package de.barryallenofearth.adventofcode2022.riddle.e.stacks.common.util;

import de.barryallenofearth.adventofcode2022.riddle.e.stacks.common.model.CommandModel;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstructionsReader {

	public static final Pattern COMMAND_PATTERN = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)$");

	public static List<CommandModel> readCommands(String fileName) throws IOException, URISyntaxException {
		final List<CommandModel> commands = new ArrayList<>();
		final List<String> strings = RiddleFileReader.readAllLines(fileName);

		for (String line : strings) {
			final Matcher matcher = COMMAND_PATTERN.matcher(line);
			if (matcher.matches()) {
				commands.add(new CommandModel(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)) - 1, Integer.parseInt(matcher.group(3)) - 1));
			}
		}

		return commands;
	}
}
