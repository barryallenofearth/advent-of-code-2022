package de.barryallenofearth.adventofcode2022.riddle.s.common;

import de.barryallenofearth.adventofcode2022.riddle.s.common.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class RunFactoryByBluePrint {
	public static RobotsAndFactory runFactory(BluePrint bluePrint) {
		final Factory factory = new Factory(bluePrint);
		final RobotsAndFactory robotsAndFactory = new RobotsAndFactory(factory);

		final List<RobotInConstruction> robotsInConstruction = new ArrayList<>();
		for (int minute = 1; minute <= 24; minute++) {

			System.out.println();
			System.out.println("== Minute " + minute + " ==");

			for (RobotInConstruction robotInConstruction : robotsInConstruction) {
				robotInConstruction.getAddRobot().accept(robotsAndFactory, robotInConstruction.getRobot());
			}
			robotsInConstruction.clear();

			//TODO implement tactics to save for better robots

			Optional<Robot> optional = produceRobot(robotsInConstruction, robotsAndFactory, Factory::produceGeodeRobot, ((robotsAndFactory1, robot) -> robotsAndFactory1.getGeodeRobots().add(robot)));
			final boolean geodeRobotBuildableNextMinute = isGeodeRobotBuildableNextMinute(bluePrint, factory, robotsAndFactory);
			final boolean geodeRobotBuildable2ndNextMinute = isGeodeRobotBuildable2ndNextMinute(bluePrint, factory, robotsAndFactory);
			final boolean obsidianRobotBuildableNextMinute = isObsidianRobotBuildableNextMinute(bluePrint, factory, robotsAndFactory);
			final boolean obsidianRobotBuildable2ndNextMinute = isObsidianRobotBuildable2ndNextMinute(bluePrint, factory, robotsAndFactory);
			if (optional.isEmpty()) {
				if (!geodeRobotBuildableNextMinute && !geodeRobotBuildable2ndNextMinute) {
					optional = produceRobot(robotsInConstruction, robotsAndFactory, Factory::produceObsidianRobot, ((robotsAndFactory1, robot) -> robotsAndFactory1.getObsidianRobots().add(robot)));
					if (optional.isEmpty()) {
						if (!obsidianRobotBuildableNextMinute && !obsidianRobotBuildable2ndNextMinute) {
							optional = produceRobot(robotsInConstruction, robotsAndFactory, Factory::produceClayRobot, ((robotsAndFactory1, robot) -> robotsAndFactory1.getClayRobots().add(robot)));
							if (optional.isEmpty()) {
								optional = produceRobot(robotsInConstruction, robotsAndFactory, Factory::produceOreRobot, ((robotsAndFactory1, robot) -> robotsAndFactory1.getOreRobots().add(robot)));
								if (optional.isPresent()) {
									System.out.println("start producing a ore collecting robot");
								}
							} else {
								System.out.println("start producing a clay collecting robot");
							}
						}
					} else {
						System.out.println("start producing a obsidian collecting robot");
					}
				}
			} else {
				System.out.println("start producing a geode cracking robot");
			}

			Stream.of(robotsAndFactory.getOreRobots(),
					robotsAndFactory.getClayRobots(),
					robotsAndFactory.getObsidianRobots(),
					robotsAndFactory.getGeodeRobots())
					.flatMap(List::stream)
					.forEach(robot -> robot.getAddToFactory().accept(factory));

			System.out.println(factory.getOre() + " ore, " + factory.getClay() + " clay, " + factory.getObsidian() + " obsidian, " + factory.getGeodes() + " geodes.");
			System.out.println(robotsAndFactory.getOreRobots().size() + " ore robots, " + robotsAndFactory.getClayRobots().size() + " clay robots, " + robotsAndFactory.getObsidianRobots().size() + " obsidian robots, " + robotsAndFactory
					.getGeodeRobots().size() + " geode robots.");
		}
		return robotsAndFactory;
	}

	static boolean isGeodeRobotBuildableNextMinute(BluePrint bluePrint, Factory factory, RobotsAndFactory robotsAndFactory) {
		return factory.getObsidian() + robotsAndFactory.getObsidianRobots().size() >= bluePrint.getGeodeRobot().getObsidian();
	}

	static boolean isGeodeRobotBuildable2ndNextMinute(BluePrint bluePrint, Factory factory, RobotsAndFactory robotsAndFactory) {
		return factory.getObsidian() + 2 * robotsAndFactory.getObsidianRobots().size() >= bluePrint.getGeodeRobot().getObsidian();
	}

	static boolean isObsidianRobotBuildableNextMinute(BluePrint bluePrint, Factory factory, RobotsAndFactory robotsAndFactory) {
		return factory.getClay() + robotsAndFactory.getClayRobots().size() >= bluePrint.getObsidianRobot().getClay();
	}

	static boolean isObsidianRobotBuildable2ndNextMinute(BluePrint bluePrint, Factory factory, RobotsAndFactory robotsAndFactory) {
		return factory.getClay() + 2 * robotsAndFactory.getClayRobots().size() >= bluePrint.getObsidianRobot().getClay();
	}

	private static Optional<Robot> produceRobot(List<RobotInConstruction> robotsInConstruction, RobotsAndFactory robotsAndFactory, Function<Factory, Optional<Robot>> createRobot, BiConsumer<RobotsAndFactory, Robot> addRobot) {
		final Optional<Robot> robotProduced = createRobot.apply(robotsAndFactory.getFactory());
		robotProduced.ifPresent(robot -> robotsInConstruction.add(new RobotInConstruction(robot, addRobot)));
		return robotProduced;
	}
}
