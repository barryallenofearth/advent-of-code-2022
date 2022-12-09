package de.barryallenofearth.adventofcode2022.riddle.i.rope.common;

import de.barryallenofearth.adventofcode2022.riddle.i.rope.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.i.rope.model.StepType;

public class FollowHead {

    public static void updateTailCoordinates(Coordinates head, Coordinates tail) {
        if (distanceBetweenHeadAndTail(head, tail) < 2) {
            return;
        }

        if (head.getRow() == tail.getRow()) {
            horizontalMovement(head, tail);
        } else if (head.getColumn() == tail.getColumn()) {
            verticalMovement(head, tail);
        } else {
            horizontalMovement(head, tail);
            verticalMovement(head, tail);
        }
    }

    private static void horizontalMovement(Coordinates head, Coordinates tail) {
        if (head.getColumn() > tail.getColumn()) {
            StepType.RIGHT.getMoveRopeEnd().accept(tail);
        } else {
            StepType.LEFT.getMoveRopeEnd().accept(tail);
        }
    }

    private static void verticalMovement(Coordinates head, Coordinates tail) {
        if (head.getRow() > tail.getRow()) {
            StepType.DOWN.getMoveRopeEnd().accept(tail);
        } else {
            StepType.UP.getMoveRopeEnd().accept(tail);
        }
    }

    public static double distanceBetweenHeadAndTail(Coordinates head, Coordinates tail) {
        return Math.sqrt(Math.pow(head.getRow() - tail.getRow(), 2) + Math.pow(head.getColumn() - tail.getColumn(), 2));
    }
}
