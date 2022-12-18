package de.barryallenofearth.adventofcode2022.riddle.n.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CaveModel {

	private final Set<Coordinates> rockCoordinates = new HashSet<>();

	private int minX = Integer.MAX_VALUE;

	private int maxX = Integer.MIN_VALUE;

	private int minY = Integer.MAX_VALUE;

	private int maxY = Integer.MIN_VALUE;

}
