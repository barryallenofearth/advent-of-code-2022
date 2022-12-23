package de.barryallenofearth.adventofcode2022.riddle.s.common.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;

@Data
@RequiredArgsConstructor
public class RobotInConstruction {
	private final Robot robot;

	private final BiConsumer<RobotsAndFactory, Robot> addRobot;

}
