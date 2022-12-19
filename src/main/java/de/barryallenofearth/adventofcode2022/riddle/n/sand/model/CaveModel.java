package de.barryallenofearth.adventofcode2022.riddle.n.sand.model;

import de.barryallenofearth.adventofcode2022.riddle.n.sand.part1.SandSimulator;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CaveModel {

	private final Set<Coordinates> rockCoordinates = new HashSet<>();

	private int minX = SandSimulator.SAND_START_COORDINATES.getRow() - 1;

	private int maxX = SandSimulator.SAND_START_COORDINATES.getRow() + 1;

	private int minY = SandSimulator.SAND_START_COORDINATES.getColumn();

	private int maxY = SandSimulator.SAND_START_COORDINATES.getColumn() + 1;

}
