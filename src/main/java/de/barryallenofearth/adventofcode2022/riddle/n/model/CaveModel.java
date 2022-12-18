package de.barryallenofearth.adventofcode2022.riddle.n.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import static de.barryallenofearth.adventofcode2022.riddle.n.part1.SandSimulator.SAND_START_COORDINATES;

@Data
public class CaveModel {

	private final Set<Coordinates> rockCoordinates = new HashSet<>();

	private int minX = SAND_START_COORDINATES.getRow() - 1;

	private int maxX = SAND_START_COORDINATES.getRow() + 1;

	private int minY = SAND_START_COORDINATES.getColumn();

	private int maxY = SAND_START_COORDINATES.getColumn() + 1;

}
