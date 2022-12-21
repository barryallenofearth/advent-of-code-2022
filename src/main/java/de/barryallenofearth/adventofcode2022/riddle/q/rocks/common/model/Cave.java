package de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Cave {

	private int currentRockHeight = 0;

	private final Set<Coordinates> occupiedFields = new HashSet<>();

	public void addShape(Shape shape) {
		occupiedFields.addAll(shape.getComponents());
		for (Coordinates occupiedField : occupiedFields) {
			if (occupiedField.getY() > currentRockHeight) {
				currentRockHeight = occupiedField.getY();
			}
		}
	}
}
