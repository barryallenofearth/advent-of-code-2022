package de.barryallenofearth.adventofcode2022.riddle.day19.robots.common;

import de.barryallenofearth.adventofcode2022.riddle.day19.robots.common.model.BluePrint;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BluePrintReader {
	public static final Pattern ORE_ROBOT_PATTERN = Pattern.compile("Each ore robot costs (\\d+) ore");

	public static final Pattern CLAY_ROBOT_PATTERN = Pattern.compile("Each clay robot costs (\\d+) ore");

	public static final Pattern OBSIDIAN_ROBOT_PATTERN = Pattern.compile("Each obsidian robot costs (\\d+) ore and (\\d++) clay");

	public static final Pattern GEODE_ROBOT_PATTERN = Pattern.compile("Each geode robot costs (\\d+) ore and (\\d+) obsidian");

	public static List<BluePrint> readBluePrints() {
		final List<BluePrint> bluePrintList = new ArrayList<>();
		final List<String> strings = RiddleFileReader.readAllLines("riddle-19.txt");
		for (String string : strings) {
			string = string.replaceAll("Blueprint \\d+:\\s+", "");
			final String[] robotInstructions = string.split("\\s*\\.\\s*");

			final Matcher oreMatcher = ORE_ROBOT_PATTERN.matcher(robotInstructions[0]);
			BluePrint.ItemsConsumed oreRobotConsumption = null;
			if (oreMatcher.matches()) {
				oreRobotConsumption = new BluePrint.ItemsConsumed(Integer.parseInt(oreMatcher.group(1)), 0, 0);
			}

			final Matcher clayMatcher = CLAY_ROBOT_PATTERN.matcher(robotInstructions[1]);
			BluePrint.ItemsConsumed clayRobotConsumption = null;
			if (clayMatcher.matches()) {
				clayRobotConsumption = new BluePrint.ItemsConsumed(Integer.parseInt(clayMatcher.group(1)), 0, 0);
			}

			final Matcher obsidianMatcher = OBSIDIAN_ROBOT_PATTERN.matcher(robotInstructions[2]);
			BluePrint.ItemsConsumed obsidianRobotConsumption = null;
			if (obsidianMatcher.matches()) {
				obsidianRobotConsumption = new BluePrint.ItemsConsumed(Integer.parseInt(obsidianMatcher.group(1)), Integer.parseInt(obsidianMatcher.group(2)), 0);
			}

			final Matcher geodeMatcher = GEODE_ROBOT_PATTERN.matcher(robotInstructions[3]);
			BluePrint.ItemsConsumed geodeRobotConsumption = null;
			if (geodeMatcher.matches()) {
				geodeRobotConsumption = new BluePrint.ItemsConsumed(Integer.parseInt(geodeMatcher.group(1)), 0, Integer.parseInt(geodeMatcher.group(2)));
			}

			bluePrintList.add(new BluePrint(string, oreRobotConsumption, clayRobotConsumption, obsidianRobotConsumption, geodeRobotConsumption));
		}

		return bluePrintList;
	}
}
