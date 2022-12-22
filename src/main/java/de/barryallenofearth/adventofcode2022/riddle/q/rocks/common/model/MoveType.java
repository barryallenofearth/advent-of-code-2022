package de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MoveType {
    LEFT('<', new Coordinates(-1, 0)),
    RIGHT('>', new Coordinates(1, 0)),
    DOWN(null, new Coordinates(0, -1));

    private final Character key;

    private final Coordinates move;

    public static MoveType getByKey(char key) {
        for (MoveType moveType : MoveType.values()) {
            if (key == moveType.getKey()) {
                return moveType;
            }
        }
        return null;
    }
}
