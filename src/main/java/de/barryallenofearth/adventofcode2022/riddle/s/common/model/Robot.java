package de.barryallenofearth.adventofcode2022.riddle.s.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum Robot {
	GEODE_ROBOT(RobotsAndFactory::addGeode, BluePrint::getGeodeRobot, robotsAndFactory -> robotsAndFactory.setGeodeRobots(robotsAndFactory.getGeodeRobots() + 1), "geode collecting robot"),
	OBSIDIAN_ROBOT(RobotsAndFactory::addObsidian, BluePrint::getObsidianRobot, robotsAndFactory -> robotsAndFactory.setObsidianRobots(robotsAndFactory.getObsidianRobots() + 1), "obsidian collecting robot"),
	CLAY_ROBOT(RobotsAndFactory::addClay, BluePrint::getClayRobot, robotsAndFactory -> robotsAndFactory.setClayRobots(robotsAndFactory.getClayRobots() + 1), "clay collecting robot"),
	ORE_ROBOT(RobotsAndFactory::addOre, BluePrint::getOreRobot, robotsAndFactory -> robotsAndFactory.setOreRobots(robotsAndFactory.getOreRobots() + 1), "ore collecting robot");

	private final Consumer<RobotsAndFactory> addToFactory;

	private final Function<BluePrint, BluePrint.ItemsConsumed> costs;

	private final Consumer<RobotsAndFactory> addRobot;

	private final String name;
}
