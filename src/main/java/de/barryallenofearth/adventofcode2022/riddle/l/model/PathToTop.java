package de.barryallenofearth.adventofcode2022.riddle.l.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

@Getter
@EqualsAndHashCode
@ToString
public class PathToTop {

    public PathToTop(Coordinates startingPosition, Coordinates targetPosition) {
        this.targetPosition = targetPosition;
        setCurrentPosition(startingPosition);
    }

    private int stepCount = -1;

    private final Coordinates targetPosition;

    private final Stack<Coordinates> fieldsAlreadyVisited = new Stack<>();

    private final Set<Coordinates> deadEnds = new HashSet<>();

    private Coordinates currentPosition;

    private Coordinates lastPositionWhileMovingBackwards;

    private boolean isComplete;

    public void setCurrentPosition(Coordinates currentPosition) {
        this.currentPosition = currentPosition;
        fieldsAlreadyVisited.add(currentPosition);
        isComplete = targetPosition.equals(currentPosition);
        lastPositionWhileMovingBackwards = null;
        this.stepCount++;
    }

    public void goBack() {
        final Coordinates coordinates = fieldsAlreadyVisited.pop();
        this.deadEnds.add(coordinates);
        this.lastPositionWhileMovingBackwards = this.currentPosition;
        this.currentPosition = coordinates;
        this.isComplete = false;

        stepCount--;
    }

}
