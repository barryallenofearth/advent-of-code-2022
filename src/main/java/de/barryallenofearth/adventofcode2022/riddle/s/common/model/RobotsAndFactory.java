package de.barryallenofearth.adventofcode2022.riddle.s.common.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class RobotsAndFactory {

	public RobotsAndFactory(BluePrint bluePrint) {
		this.bluePrint = bluePrint;
		logMessages = new StringBuffer();
	}

	public RobotsAndFactory(RobotsAndFactory robotsAndFactory) {
		this.bluePrint = robotsAndFactory.getBluePrint();
		this.ore = robotsAndFactory.getOre();
		this.clay = robotsAndFactory.getClay();
		this.obsidian = robotsAndFactory.getObsidian();
		this.geodes = robotsAndFactory.getGeodes();

		this.oreRobots = robotsAndFactory.getOreRobots();
		this.clayRobots = robotsAndFactory.getClayRobots();
		this.obsidianRobots = robotsAndFactory.getObsidianRobots();
		this.geodeRobots = robotsAndFactory.getGeodeRobots();

		this.robotsInConstruction = robotsAndFactory.getRobotsInConstruction().isEmpty() ? Optional.empty() : Optional.of(robotsAndFactory.getRobotsInConstruction().get());

		this.currentMinute = robotsAndFactory.getCurrentMinute();

		this.logMessages = new StringBuffer(robotsAndFactory.getLogMessages());
	}

	private final BluePrint bluePrint;

	private int currentMinute = 1;

	private final StringBuffer logMessages;

	private int oreRobots = 1;

	private int clayRobots = 0;

	private int obsidianRobots = 0;

	private int geodeRobots = 0;

	private Optional<Robot> robotsInConstruction = Optional.empty();

	private int ore = 0;

	private int clay = 0;

	private int obsidian = 0;

	private int geodes = 0;

	public void addOre() {
		ore++;
	}

	public void addClay() {
		clay++;
	}

	public void addObsidian() {
		obsidian++;
	}

	public void addGeode() {
		geodes++;
	}

	public void addRobot(Robot robot) {
		final BluePrint.ItemsConsumed costs = robot.getCosts().apply(this.bluePrint);
		this.ore -= costs.getOre();
		this.clay -= costs.getClay();
		this.obsidian -= costs.getObsidian();

		this.robotsInConstruction = Optional.of(robot);
		logMessages.append("start producing a ").append(robot.getName()).append("\n");
	}

	public boolean testIfBuildable(Robot robot) {
		final BluePrint.ItemsConsumed costs = robot.getCosts().apply(this.bluePrint);
		return costs.getOre() <= this.ore && costs.getClay() <= this.clay && costs.getObsidian() <= this.obsidian;
	}

	public void collectGoods() {
		this.ore += this.oreRobots;
		this.clay += this.clayRobots;
		this.obsidian += this.obsidianRobots;
		this.geodes += this.geodeRobots;

	}

}
