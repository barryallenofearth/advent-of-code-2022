package de.barryallenofearth.adventofcode2022.riddle.s.common;

import de.barryallenofearth.adventofcode2022.riddle.s.common.model.*;

import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class RunFactoryByBluePrint {
	public static RobotsAndFactory runFactory(BluePrint bluePrint) {
		final RobotsAndFactory robotsAndFactory = new RobotsAndFactory(new Factory(bluePrint));

		Stack<RobotsAndFactory> openBranches = new Stack<>();
		openBranches.add(robotsAndFactory);

		RobotsAndFactory maxRobotsAndFactory = new RobotsAndFactory(new Factory(bluePrint));
		while (!openBranches.empty()) {

			final RobotsAndFactory currentRobotsAndFactory = openBranches.pop();

			while (currentRobotsAndFactory.getCurrentMinute() <= 24) {
				currentRobotsAndFactory.getLogMessages().add("\n== Minute " + currentRobotsAndFactory.getCurrentMinute() + " ==");

				if (currentRobotsAndFactory.getRobotsInConstruction().isPresent()) {
					final RobotInConstruction robotInConstruction = currentRobotsAndFactory.getRobotsInConstruction().get();
					robotInConstruction.getAddRobot().accept(robotsAndFactory, robotInConstruction.getRobot());
					currentRobotsAndFactory.setRobotsInConstruction(Optional.empty());
				}

				Optional<Robot> optional = produceRobot(robotsAndFactory, Factory::produceGeodeRobot, ((robotsAndFactory1, robot) -> robotsAndFactory1.getGeodeRobots().add(robot)));
				if (optional.isEmpty()) {
					if (isObsidianRobotBuildable(bluePrint, currentRobotsAndFactory.getFactory())) {
						saveMaterials(openBranches, currentRobotsAndFactory);
						produceRobot(robotsAndFactory, Factory::produceObsidianRobot, ((robotsAndFactory1, robot) -> robotsAndFactory1.getObsidianRobots().add(robot)));
						robotsAndFactory.getLogMessages().add("start producing a obsidian collecting robot");
					}
					if (isClayRobotBuildable(bluePrint, currentRobotsAndFactory.getFactory()) && currentRobotsAndFactory.getRobotsInConstruction().isEmpty()) {
						saveMaterials(openBranches, currentRobotsAndFactory);
						produceRobot(robotsAndFactory, Factory::produceClayRobot, ((robotsAndFactory1, robot) -> robotsAndFactory1.getClayRobots().add(robot)));
						robotsAndFactory.getLogMessages().add("start producing a clay collecting robot");
					}
					if (isOreRobotBuildable(bluePrint, currentRobotsAndFactory.getFactory()) && currentRobotsAndFactory.getRobotsInConstruction().isEmpty()) {
						saveMaterials(openBranches, currentRobotsAndFactory);
						produceRobot(robotsAndFactory, Factory::produceOreRobot, ((robotsAndFactory1, robot) -> robotsAndFactory1.getOreRobots().add(robot)));
						robotsAndFactory.getLogMessages().add("start producing a ore collecting robot");
					}
				}
				collectOreAndPrint(robotsAndFactory);
			}
			if (currentRobotsAndFactory.getFactory().getGeodes() > maxRobotsAndFactory.getFactory().getGeodes()) {
				maxRobotsAndFactory = currentRobotsAndFactory;
			}
		}
		return maxRobotsAndFactory;
	}

	static void saveMaterials(Stack<RobotsAndFactory> stack, RobotsAndFactory currentRobotsAndFactory) {
		final RobotsAndFactory robotsAndFactory = new RobotsAndFactory(currentRobotsAndFactory);
		stack.add(robotsAndFactory);
		collectOreAndPrint(robotsAndFactory);

	}

	static void collectOreAndPrint(RobotsAndFactory robotsAndFactory) {
		Stream.of(robotsAndFactory.getOreRobots(),
				robotsAndFactory.getClayRobots(),
				robotsAndFactory.getObsidianRobots(),
				robotsAndFactory.getGeodeRobots())
				.flatMap(List::stream)
				.forEach(robot -> robot.getAddToFactory().accept(robotsAndFactory.getFactory()));

		robotsAndFactory.getLogMessages()
				.add(robotsAndFactory.getFactory().getOre() + " ore, " + robotsAndFactory.getFactory().getClay() + " clay, " + robotsAndFactory.getFactory().getObsidian() + " obsidian, " + robotsAndFactory.getFactory().getGeodes() + " geodes.");
		robotsAndFactory.getLogMessages()
				.add(robotsAndFactory.getOreRobots().size() + " ore robots, " + robotsAndFactory.getClayRobots().size() + " clay robots, " + robotsAndFactory.getObsidianRobots().size() + " obsidian robots, " + robotsAndFactory
						.getGeodeRobots().size() + " geode robots.");

		robotsAndFactory.setCurrentMinute(robotsAndFactory.getCurrentMinute() + 1);
	}

	static boolean isObsidianRobotBuildable(BluePrint bluePrint, Factory factory) {
		return factory.getClay() - bluePrint.getObsidianRobot().getClay() >= 0 && factory.getOre() - bluePrint.getObsidianRobot().getOre() >= 0;
	}

	static boolean isClayRobotBuildable(BluePrint bluePrint, Factory factory) {
		return factory.getOre() - bluePrint.getClayRobot().getOre() >= 0;
	}

	static boolean isOreRobotBuildable(BluePrint bluePrint, Factory factory) {
		return factory.getOre() - bluePrint.getOreRobot().getOre() >= 0;
	}

	private static Optional<Robot> produceRobot(RobotsAndFactory robotsAndFactory, Function<Factory, Optional<Robot>> createRobot, BiConsumer<RobotsAndFactory, Robot> addRobot) {
		final Optional<Robot> robotProduced = createRobot.apply(robotsAndFactory.getFactory());
		robotProduced.ifPresent(robot -> robotsAndFactory.setRobotsInConstruction(Optional.of(new RobotInConstruction(robot, addRobot))));
		return robotProduced;
	}
}
