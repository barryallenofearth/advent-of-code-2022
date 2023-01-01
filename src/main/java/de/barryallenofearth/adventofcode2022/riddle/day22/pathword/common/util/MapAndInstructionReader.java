package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.util;

import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.*;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapAndInstructionReader {

	public static final Pattern NUMBER_PATTERN = Pattern.compile("^(\\d+)");

	public static final Pattern ROTATION_PATTERN = Pattern.compile("^([RL])");

	public MapAndInstructions read() {
		final MapAndInstructions mapAndInstructions = new MapAndInstructions();

		final List<String> strings = RiddleFileReader.readAllLines("riddle-22.txt");
		readInstructions(mapAndInstructions, strings);
		readCoordinates(mapAndInstructions, strings);

		return mapAndInstructions;
	}

	private void readCoordinates(MapAndInstructions mapAndInstructions, List<String> strings) {
		int y = 1;
		for (String string : strings) {
			for (int x = 1; x <= string.length(); x++) {
				if (string.charAt(x - 1) == ' ') {
					continue;
				} else if (string.charAt(x - 1) == '.') {
					mapAndInstructions.getBoardCoordinates().add(new Coordinates(x, y));
				} else if (string.charAt(x - 1) == '#') {
					mapAndInstructions.getBoardCoordinates().add(new Coordinates(x, y));
					mapAndInstructions.getBlockedCoordinates().add(new Coordinates(x, y));
				}
			}
			y++;
		}

	}

	private void readInstructions(MapAndInstructions mapAndInstructions, List<String> strings) {
		String instructions = strings.get(strings.size() - 1);
		strings.remove(instructions);

		while (instructions.length() > 0) {
			final Matcher numberMatcher = NUMBER_PATTERN.matcher(instructions);
			final Matcher rotationMatcher = ROTATION_PATTERN.matcher(instructions);
			if (numberMatcher.find()) {
				final String numberOfSteps = numberMatcher.group(1);
				mapAndInstructions.getInstructions().add(new Instruction(InstructionType.MOVE_FORWARD, null, Integer.parseInt(numberOfSteps)));
				instructions = instructions.substring(numberOfSteps.length());

			} else if (rotationMatcher.find()) {
				final String rotationType = rotationMatcher.group(1);
				mapAndInstructions.getInstructions().add(new Instruction(InstructionType.ROTATE, Rotation.getByIdentifiert(rotationType), 0));
				instructions = instructions.substring(rotationType.length());
			} else {
				throw new IllegalStateException("Could not parse instruction " + instructions);
			}

		}
	}
}
