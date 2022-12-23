package de.barryallenofearth.adventofcode2022.riddle.s.common;

import de.barryallenofearth.adventofcode2022.riddle.s.common.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class RunFactoryByBluePrint {
	public static RobotsAndFactory runFactory(BluePrint bluePrint) {
		final Factory factory = new Factory(bluePrint);
		final RobotsAndFactory robotsAndFactory = new RobotsAndFactory(factory);

		final List<RobotInConstruction> robotsInConstruction = new ArrayList<>();
		for (int minute = 1; minute <= 24; minute++) {

			for (RobotInConstruction robotInConstruction : robotsInConstruction) {
				robotInConstruction.getAddRobot().accept(robotsAndFactory, robotInConstruction.getRobot());
			}
			robotsInConstruction.clear();
			for (Robot robot : robotsAndFactory.getOreRobots()) {
				robot.getAddToFactory().accept(factory);
			}
			for (Robot robot : robotsAndFactory.getClayRobots()) {
				robot.getAddToFactory().accept(factory);
			}
			for (Robot robot : robotsAndFactory.getObsidianRobots()) {
				robot.getAddToFactory().accept(factory);
			}
			for (Robot robot : robotsAndFactory.getGeodeRobots()) {
				robot.getAddToFactory().accept(factory);
			}

			//TODO implement tactics to save for better robots
			produceRobot(robotsInConstruction, robotsAndFactory, Factory::produceGeodeRobot, ((robotsAndFactory1, robot) -> robotsAndFactory1.getGeodeRobots().add(robot)));
			produceRobot(robotsInConstruction, robotsAndFactory, Factory::produceObsidianRobot, ((robotsAndFactory1, robot) -> robotsAndFactory1.getObsidianRobots().add(robot)));
			produceRobot(robotsInConstruction, robotsAndFactory, Factory::produceClayRobot, ((robotsAndFactory1, robot) -> robotsAndFactory1.getClayRobots().add(robot)));
			produceRobot(robotsInConstruction, robotsAndFactory, Factory::produceOreRobot, ((robotsAndFactory1, robot) -> robotsAndFactory1.getOreRobots().add(robot)));

			System.out.println("Minute " + minute + ": " + factory.getOre() + " ore, " + factory.getClay() + " clay, " + factory.getObsidian() + " obsidian, " + factory.getGeodes() + " geodes.");
			System.out.println(robotsAndFactory.getOreRobots().size() + " ore robots, " + robotsAndFactory.getClayRobots().size() + " clay robots, " + robotsAndFactory.getObsidianRobots().size() + " obsidian robots, " + robotsAndFactory
					.getGeodeRobots().size() + " geode robots.");
		}
		return robotsAndFactory;
	}

	private static void produceRobot(List<RobotInConstruction> robotsInConstruction, RobotsAndFactory robotsAndFactory, Function<Factory, Optional<Robot>> createRobot, BiConsumer<RobotsAndFactory, Robot> addRobot) {
		final Optional<Robot> robotProduced = createRobot.apply(robotsAndFactory.getFactory());
		robotProduced.ifPresent(robot -> robotsInConstruction.add(new RobotInConstruction(robot, addRobot)));
	}
}
