package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.part2.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RotationProperties {
	private final Coordinates3D shiftToAxisVector;

	private final int[][] rotationMatrix;
}
