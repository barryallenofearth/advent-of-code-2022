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
		RobotsAndFactory currentRobotsAndFactory = new RobotsAndFactory(bluePrint);

		Stack<RobotsAndFactory> openBranches = new Stack<>();
		openBranches.add(currentRobotsAndFactory);

		RobotsAndFactory maxRobotsAndFactory = currentRobotsAndFactory;
		int countComplete = 0;
		while (!openBranches.empty()) {

			currentRobotsAndFactory = openBranches.pop();

			while (currentRobotsAndFactory.getCurrentMinute() <= 24) {
				currentRobotsAndFactory.getLogMessages().append("\n== Minute ").append(currentRobotsAndFactory.getCurrentMinute()).append(" ==\n");

				if (currentRobotsAndFactory.getRobotsInConstruction().isPresent()) {
					final Robot robotInConstruction = currentRobotsAndFactory.getRobotsInConstruction().get();
					robotInConstruction.getAddRobot().accept(currentRobotsAndFactory);
					currentRobotsAndFactory.setRobotsInConstruction(Optional.empty());
				}
				for (Robot robot : Robot.values()) {
					testAndBuildRobot(currentRobotsAndFactory, openBranches, robot);
				}
				collectOreAndPrint(currentRobotsAndFactory);
			}
			if (currentRobotsAndFactory.getGeodes() > maxRobotsAndFactory.getGeodes()) {
				maxRobotsAndFactory = currentRobotsAndFactory;
				System.out.println("new max way found:" + currentRobotsAndFactory.getGeodes());
			}
			++countComplete;
			if (countComplete % 1_000_000 == 0) {
				System.out.println(countComplete);
			}
		}

		System.out.println(maxRobotsAndFactory.getLogMessages());
		System.out.println(countComplete + " branches tested.");
		return maxRobotsAndFactory;
	}

	static void testAndBuildRobot(RobotsAndFactory currentRobotsAndFactory, Stack<RobotsAndFactory> openBranches, Robot oreRobot) {
		if (currentRobotsAndFactory.getRobotsInConstruction().isEmpty() && currentRobotsAndFactory.testIfBuildable(oreRobot)) {
			saveMaterials(openBranches, currentRobotsAndFactory);
			currentRobotsAndFactory.addRobot(oreRobot);
		}
	}

	static void saveMaterials(Stack<RobotsAndFactory> stack, RobotsAndFactory currentRobotsAndFactory) {
		final RobotsAndFactory robotsAndFactory = new RobotsAndFactory(currentRobotsAndFactory);
		stack.add(robotsAndFactory);
		collectOreAndPrint(robotsAndFactory);

	}

	static void collectOreAndPrint(RobotsAndFactory robotsAndFactory) {
		robotsAndFactory.collectGoods();

		robotsAndFactory.getLogMessages().append(robotsAndFactory.getOre()).append(" ore, ").append(robotsAndFactory.getClay()).append(" clay, ").append(robotsAndFactory.getObsidian()).append(" obsidian, ").append(robotsAndFactory.getGeodes())
				.append(" geodes.\n");
		robotsAndFactory.getLogMessages().append(robotsAndFactory.getOreRobots()).append(" ore robots, ").append(robotsAndFactory.getClayRobots()).append(" clay robots, ").append(robotsAndFactory.getObsidianRobots())
				.append(" obsidian robots, ").append(robotsAndFactory
				.getGeodeRobots()).append(" geode robots.\n");

		robotsAndFactory.setCurrentMinute(robotsAndFactory.getCurrentMinute() + 1);
	}

}
