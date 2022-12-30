package de.barryallenofearth.adventofcode2022.riddle.day12.pathtotop.model;

import lombok.Data;

@Data
public class HeightJourneyModel {

	private Coordinates startingPosition;

	private Coordinates targetPosition;

	private int[][] heightMap;
}
