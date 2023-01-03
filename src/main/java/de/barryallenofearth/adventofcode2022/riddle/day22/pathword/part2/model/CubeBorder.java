package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.part2.model;

import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.Direction;
import lombok.Data;

import java.util.List;

@Data
public class CubeBorder {
	private final int facingValue;

	private final Direction directionToLeave;

	private final List<FoldedCoordinates> cornerCoordinates;
}
