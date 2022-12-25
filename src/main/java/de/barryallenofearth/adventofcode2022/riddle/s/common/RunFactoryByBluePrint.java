package de.barryallenofearth.adventofcode2022.riddle.s.common;

import de.barryallenofearth.adventofcode2022.riddle.s.common.model.*;

import java.util.Optional;
import java.util.Stack;

public class RunFactoryByBluePrint {
	public static RobotsAndFactory runFactory(BluePrint bluePrint) {

		RobotsAndFactory currentRobotsAndFactory = new RobotsAndFactory(bluePrint);

		Stack<RobotsAndFactory> openBranches = new Stack<>();
		openBranches.add(currentRobotsAndFactory);

		RobotsAndFactory maxRobotsAndFactory = currentRobotsAndFactory;
		int maxGeodes = 0;
		int countComplete = 0;
		while (!openBranches.empty()) {

			currentRobotsAndFactory = openBranches.pop();
			if (abortBranch(maxGeodes, currentRobotsAndFactory)) {
				continue;
			}

			while (currentRobotsAndFactory.getCurrentMinute() <= 24) {
				currentRobotsAndFactory.getLogMessages().append("\n== Minute ").append(currentRobotsAndFactory.getCurrentMinute()).append(" ==\n");

				if (currentRobotsAndFactory.getRobotsInConstruction().isPresent()) {
					currentRobotsAndFactory.getRobotsInConstruction().get().getAddRobot().accept(currentRobotsAndFactory);
					currentRobotsAndFactory.setRobotsInConstruction(Optional.empty());
				}

				for (Robot robot : Robot.values()) {
					testAndBuildRobot(currentRobotsAndFactory, openBranches, robot);
				}
				collectGoodsAndPrint(currentRobotsAndFactory);
			}
			if (currentRobotsAndFactory.getGeodes() > maxGeodes) {
				maxRobotsAndFactory = currentRobotsAndFactory;
				maxGeodes = currentRobotsAndFactory.getGeodes();
				System.out.println("new max way found:" + currentRobotsAndFactory.getGeodes());
			}
			++countComplete;
			if (countComplete % 1_000_000 == 0) {
				System.out.println(countComplete / 1_000_000 + "x10^6 branches tested.");
			}
		}

		System.out.println(maxRobotsAndFactory.getLogMessages());
		System.out.println(countComplete + " branches tested to completion.");
		return maxRobotsAndFactory;
	}

	static void testAndBuildRobot(RobotsAndFactory currentRobotsAndFactory, Stack<RobotsAndFactory> openBranches, Robot robot) {
		//no point in building a robot in last turn
		if (currentRobotsAndFactory.getCurrentMinute() == 24) {
			return;
		}
		//no point in build a non geode robot in second last turn.
		if (currentRobotsAndFactory.getCurrentMinute() == 23 && robot != Robot.GEODE_ROBOT) {
			return;
		}

		if (currentRobotsAndFactory.testIfBuildable(robot)) {
			//always build test robots if possible
			if (robot != Robot.GEODE_ROBOT) {
				saveMaterials(openBranches, currentRobotsAndFactory);
			}
			currentRobotsAndFactory.addRobot(robot);
		}
	}

	static void saveMaterials(Stack<RobotsAndFactory> stack, RobotsAndFactory currentRobotsAndFactory) {
		final RobotsAndFactory robotsAndFactory = new RobotsAndFactory(currentRobotsAndFactory);
		stack.add(robotsAndFactory);
		collectGoodsAndPrint(robotsAndFactory);

	}

	static void collectGoodsAndPrint(RobotsAndFactory robotsAndFactory) {
		robotsAndFactory.collectGoods();

		robotsAndFactory.getLogMessages().append(robotsAndFactory.getOre()).append(" ore, ").append(robotsAndFactory.getClay()).append(" clay, ").append(robotsAndFactory.getObsidian()).append(" obsidian, ").append(robotsAndFactory.getGeodes())
				.append(" geodes.\n");
		robotsAndFactory.getLogMessages().append(robotsAndFactory.getOreRobots()).append(" ore robots, ").append(robotsAndFactory.getClayRobots()).append(" clay robots, ").append(robotsAndFactory.getObsidianRobots())
				.append(" obsidian robots, ").append(robotsAndFactory
				.getGeodeRobots()).append(" geode robots.\n");

		robotsAndFactory.setCurrentMinute(robotsAndFactory.getCurrentMinute() + 1);
	}

	private static boolean abortBranch(int maxGeodes, RobotsAndFactory currentRobotsAndFactory) {
		//assume you can somehow build a geode robot every turn now. If the amount of geodes collected is still below the maximum you should stop
		int maxGeodesImaginable = 0;
		for (int minute = 0; minute < 24 - currentRobotsAndFactory.getCurrentMinute(); minute++) {
			maxGeodesImaginable += currentRobotsAndFactory.getGeodeRobots() + minute;
		}
		if (maxGeodesImaginable <= maxGeodes) {
			return true;
		}

		return false;
	}

}
