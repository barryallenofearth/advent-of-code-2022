package de.barryallenofearth.adventofcode2022.riddle.s.common.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class RobotsAndFactory {

	public RobotsAndFactory(Factory factory) {
		this.factory = factory;
	}

	public RobotsAndFactory(RobotsAndFactory robotsAndFactory) {
		this.factory = new Factory(robotsAndFactory.getFactory().getBluePrint());
		this.factory.setOre(robotsAndFactory.getFactory().getOre());
		this.factory.setClay(robotsAndFactory.getFactory().getClay());
		this.factory.setObsidian(robotsAndFactory.getFactory().getOre());
		this.factory.setGeodes(robotsAndFactory.getFactory().getGeodes());

		robotsAndFactory.getOreRobots().forEach(robot -> getOreRobots().add(new Robot(Factory::addOre)));
		robotsAndFactory.getClayRobots().forEach(robot -> getClayRobots().add(new Robot(Factory::addClay)));
		robotsAndFactory.getObsidianRobots().forEach(robot -> getObsidianRobots().add(new Robot(Factory::addObsidian)));
		robotsAndFactory.getGeodeRobots().forEach(robot -> getGeodeRobots().add(new Robot(Factory::addGeode)));
		this.robotsInConstruction = robotsAndFactory.getRobotsInConstruction();

		this.currentMinute = robotsAndFactory.getCurrentMinute();

		this.logMessages.addAll(robotsAndFactory.getLogMessages());
	}

	private final Factory factory;

	private int currentMinute = 1;

	private final List<String> logMessages = new ArrayList<>();

	private final List<Robot> oreRobots = new ArrayList<>() {{
		add(new Robot(Factory::addOre));
	}};

	private final List<Robot> clayRobots = new ArrayList<>();

	private final List<Robot> obsidianRobots = new ArrayList<>();

	private final List<Robot> geodeRobots = new ArrayList<>();

	private Optional<RobotInConstruction> robotsInConstruction = Optional.empty();

}
