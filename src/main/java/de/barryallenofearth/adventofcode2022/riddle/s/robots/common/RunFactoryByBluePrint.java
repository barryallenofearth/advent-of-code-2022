package de.barryallenofearth.adventofcode2022.riddle.s.robots.common;

import de.barryallenofearth.adventofcode2022.riddle.s.robots.common.model.BluePrint;
import de.barryallenofearth.adventofcode2022.riddle.s.robots.common.model.Robot;
import de.barryallenofearth.adventofcode2022.riddle.s.robots.common.model.RobotsAndFactory;

import java.util.Optional;
import java.util.Stack;

public class RunFactoryByBluePrint {
	public static RobotsAndFactory runFactory(BluePrint bluePrint, int maxMinutes) {

		RobotsAndFactory currentRobotsAndFactory = new RobotsAndFactory(bluePrint);

		Stack<RobotsAndFactory> openBranches = new Stack<>();
		openBranches.add(currentRobotsAndFactory);

		int maxOreRequiredForAnyRobot = Math.max(bluePrint.getOreRobot().getOre(), bluePrint.getClayRobot().getOre());
		maxOreRequiredForAnyRobot = Math.max(bluePrint.getObsidianRobot().getOre(), maxOreRequiredForAnyRobot);
		maxOreRequiredForAnyRobot = Math.max(bluePrint.getGeodeRobot().getOre(), maxOreRequiredForAnyRobot);

		RobotsAndFactory maxRobotsAndFactory = currentRobotsAndFactory;
		int maxGeodes = 0;
		int countComplete = 0;
		while (!openBranches.isEmpty()) {

			currentRobotsAndFactory = openBranches.pop();

			while (currentRobotsAndFactory.getCurrentMinute() <= maxMinutes) {
				if (currentRobotsAndFactory.getRobotsInConstruction().isPresent()) {
					currentRobotsAndFactory.setOreRobotSkipped(false);
					currentRobotsAndFactory.setClayRobotSkipped(false);
					currentRobotsAndFactory.setObsidianRobotSkipped(false);
				}
				if (abortBranch(maxGeodes, currentRobotsAndFactory, maxMinutes)) {
					break;
				}
				printMinute(currentRobotsAndFactory);
				buildRobots(currentRobotsAndFactory, openBranches, maxOreRequiredForAnyRobot, maxMinutes);
				collectGoodsAndPrint(currentRobotsAndFactory);
				finishRobotConstruction(currentRobotsAndFactory);

				currentRobotsAndFactory.getLogMessages().append(currentRobotsAndFactory.getOreRobots()).append(" ore robots, ").append(currentRobotsAndFactory.getClayRobots()).append(" clay robots, ")
						.append(currentRobotsAndFactory.getObsidianRobots())
						.append(" obsidian robots, ").append(currentRobotsAndFactory
						.getGeodeRobots()).append(" geode robots.\n");

				currentRobotsAndFactory.setOreRobotSkipped(false);
				currentRobotsAndFactory.setClayRobotSkipped(false);
				currentRobotsAndFactory.setObsidianRobotSkipped(false);

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
			if (countComplete > 300_000_000) {
				break;
			}
		}

		System.out.println(maxRobotsAndFactory.getLogMessages());
		System.out.println(countComplete + " branches tested to completion.");
		return maxRobotsAndFactory;
	}

	static void finishRobotConstruction(RobotsAndFactory currentRobotsAndFactory) {
		if (currentRobotsAndFactory.getRobotsInConstruction().isPresent()) {
			final Robot robot = currentRobotsAndFactory.getRobotsInConstruction().get();
			robot.getAddRobot().accept(currentRobotsAndFactory);
			currentRobotsAndFactory.setRobotsInConstruction(Optional.empty());
			currentRobotsAndFactory.getLogMessages().append("Finished constructing ").append(robot.getName()).append("\n");

		}
	}

	static void printMinute(RobotsAndFactory currentRobotsAndFactory) {
		if (!currentRobotsAndFactory.isOreRobotSkipped() || !currentRobotsAndFactory.isClayRobotSkipped() || !currentRobotsAndFactory.isObsidianRobotSkipped()) {
			currentRobotsAndFactory.getLogMessages().append("\n== Minute ").append(currentRobotsAndFactory.getCurrentMinute()).append(" ==\n");
		}
	}

	static void buildRobots(RobotsAndFactory currentRobotsAndFactory, Stack<RobotsAndFactory> openNodes, int maxOreRequiredForAnyRobot, int maxMinutes) {
		if (currentRobotsAndFactory.getCurrentMinute() == maxMinutes) {
			return;
		}
		if (currentRobotsAndFactory.testIfBuildable(Robot.GEODE_ROBOT)) {
			currentRobotsAndFactory.addRobot(Robot.GEODE_ROBOT);
			return;
		}
		if (currentRobotsAndFactory.getCurrentMinute() == maxMinutes - 1) {
			return;
		}

		if (currentRobotsAndFactory.getBluePrint().getGeodeRobot().getObsidian() > currentRobotsAndFactory.getObsidianRobots()) {
			testAndBuildRobot(currentRobotsAndFactory, openNodes, Robot.OBSIDIAN_ROBOT);
		}
		if (currentRobotsAndFactory.getBluePrint().getObsidianRobot().getClay() > currentRobotsAndFactory.getClayRobots() && !Robot.OBSIDIAN_ROBOT.getIsRobotSkipped().test(currentRobotsAndFactory)) {
			testAndBuildRobot(currentRobotsAndFactory, openNodes, Robot.CLAY_ROBOT);
		}
		if (maxOreRequiredForAnyRobot > currentRobotsAndFactory.getOreRobots() && !Robot.OBSIDIAN_ROBOT.getIsRobotSkipped().test(currentRobotsAndFactory)) {
			testAndBuildRobot(currentRobotsAndFactory, openNodes, Robot.ORE_ROBOT);
		}

	}

	private static void testAndBuildRobot(RobotsAndFactory currentRobotsAndFactory, Stack<RobotsAndFactory> openNodes, Robot clayRobot) {
		if (currentRobotsAndFactory.getRobotsInConstruction().isEmpty() && !clayRobot.getIsRobotSkipped().test(currentRobotsAndFactory) && currentRobotsAndFactory.testIfBuildable(clayRobot)) {
			//create branch where the robot was not constructed
			final RobotsAndFactory newBranch = new RobotsAndFactory(currentRobotsAndFactory);
			clayRobot.getSetRobotSkipped().accept(newBranch);
			openNodes.add(newBranch);

			currentRobotsAndFactory.addRobot(clayRobot);
		}
	}

	static void collectGoodsAndPrint(RobotsAndFactory robotsAndFactory) {
		robotsAndFactory.setOre(robotsAndFactory.getOre() + robotsAndFactory.getOreRobots());
		robotsAndFactory.setClay(robotsAndFactory.getClay() + robotsAndFactory.getClayRobots());
		robotsAndFactory.setObsidian(robotsAndFactory.getObsidian() + robotsAndFactory.getObsidianRobots());
		robotsAndFactory.setGeodes(robotsAndFactory.getGeodes() + robotsAndFactory.getGeodeRobots());

		robotsAndFactory.getLogMessages().append(robotsAndFactory.getOre()).append(" ore, ").append(robotsAndFactory.getClay()).append(" clay, ").append(robotsAndFactory.getObsidian()).append(" obsidian, ").append(robotsAndFactory.getGeodes())
				.append(" geodes.\n");

		robotsAndFactory.setCurrentMinute(robotsAndFactory.getCurrentMinute() + 1);
	}

	private static boolean abortBranch(int maxGeodes, RobotsAndFactory currentRobotsAndFactory, int maxMinutes) {
		//assume you can somehow build a geode robot every turn now. If the amount of geodes collected is still below the maximum you should stop
		final int remainingMinutes = maxMinutes - currentRobotsAndFactory.getCurrentMinute() + 1;

		int maxGeodesObtainable = currentRobotsAndFactory.getGeodes() + currentRobotsAndFactory.getGeodeRobots() * remainingMinutes + (remainingMinutes * (remainingMinutes - 1)) / 2;
		if (maxGeodesObtainable <= maxGeodes) {
			currentRobotsAndFactory.getLogMessages().append("aborted due to geode count conditions");
			return true;
		}
		if (currentRobotsAndFactory.getCurrentMinute() > 5 && currentRobotsAndFactory.getObsidian() == 0 && currentRobotsAndFactory.getGeodeRobots() == 0) {
			int maxObsidianOptainable = currentRobotsAndFactory.getObsidian() * (remainingMinutes - 1) + ((remainingMinutes - 1) * (remainingMinutes - 2)) / 2;
			if (maxObsidianOptainable < currentRobotsAndFactory.getBluePrint().getGeodeRobot().getObsidian()) {
				currentRobotsAndFactory.getLogMessages().append("aborted due to obsidian conditions");
				return true;
			}
		}
		return false;
	}

}
