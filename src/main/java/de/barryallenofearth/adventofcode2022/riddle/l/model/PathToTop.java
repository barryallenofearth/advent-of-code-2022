package de.barryallenofearth.adventofcode2022.riddle.l.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@EqualsAndHashCode
@ToString
public class PathToTop {

	public PathToTop(int stepCount, Set<Coordinates> fieldsAlreadyVisited, List<String> movement) {
		this.stepCount = stepCount;
		this.fieldsAlreadyVisited = fieldsAlreadyVisited;
		this.movement = movement;
	}

	private int stepCount;

	private final Set<Coordinates> fieldsAlreadyVisited;

	private final List<String> movement;

	private Coordinates currentPosition;

	private boolean isComplete;

	public void setCurrentPosition(Coordinates currentPosition, Coordinates targetPosition) {
		this.currentPosition = currentPosition;
		fieldsAlreadyVisited.add(currentPosition);
		isComplete = targetPosition.equals(currentPosition);
		if (!isComplete) {
			this.stepCount++;
		}
	}
}
