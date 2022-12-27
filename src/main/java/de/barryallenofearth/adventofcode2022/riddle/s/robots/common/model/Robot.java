package de.barryallenofearth.adventofcode2022.riddle.s.robots.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum Robot {
	GEODE_ROBOT(RobotsAndFactory::addGeode,
			BluePrint::getGeodeRobot,
			robotsAndFactory -> robotsAndFactory.setGeodeRobots(robotsAndFactory.getGeodeRobots() + 1),
			null,
			null,
			"geode collecting robot"),
	OBSIDIAN_ROBOT(RobotsAndFactory::addObsidian,
			BluePrint::getObsidianRobot,
			robotsAndFactory -> robotsAndFactory.setObsidianRobots(robotsAndFactory.getObsidianRobots() + 1),
			RobotsAndFactory::isObsidianRobotSkipped,
			robotsAndFactory -> robotsAndFactory.setObsidianRobotSkipped(true),
			"obsidian collecting robot"),
	CLAY_ROBOT(RobotsAndFactory::addClay,
			BluePrint::getClayRobot,
			robotsAndFactory -> robotsAndFactory.setClayRobots(robotsAndFactory.getClayRobots() + 1),
			RobotsAndFactory::isClayRobotSkipped,
			robotsAndFactory -> robotsAndFactory.setClayRobotSkipped(true),
			"clay collecting robot"),
	ORE_ROBOT(RobotsAndFactory::addOre,
			BluePrint::getOreRobot,
			robotsAndFactory -> robotsAndFactory.setOreRobots(robotsAndFactory.getOreRobots() + 1),
			RobotsAndFactory::isOreRobotSkipped,
			robotsAndFactory -> robotsAndFactory.setOreRobotSkipped(true),
			"ore collecting robot");

	private final Consumer<RobotsAndFactory> addToFactory;

	private final Function<BluePrint, BluePrint.ItemsConsumed> costs;

	private final Consumer<RobotsAndFactory> addRobot;

	private final Predicate<RobotsAndFactory> isRobotSkipped;

	private final Consumer<RobotsAndFactory> setRobotSkipped;

	private final String name;
}
