package de.barryallenofearth.adventofcode2022.riddle.day17.rocks.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MoveType {
    LEFT(new Coordinates(-1, 0)),
    RIGHT(new Coordinates(1, 0)),
    DOWN(new Coordinates(0, -1));

    private final Coordinates move;

    public static MoveType getByKey(char key) {
        if (key == '<') {
            return LEFT;
        }
        return RIGHT;
    }
}
