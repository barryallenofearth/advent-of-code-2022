package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.part2.model;

import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.Coordinates;
import lombok.Data;

@Data
public class FoldedCoordinates {
	private final Coordinates3D foldedCoordinates;

	private final Coordinates originalCoordinates;
}
