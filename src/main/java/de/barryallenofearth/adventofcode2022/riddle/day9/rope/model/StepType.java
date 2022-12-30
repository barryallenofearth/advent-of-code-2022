package de.barryallenofearth.adventofcode2022.riddle.day9.rope.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public enum StepType {
    RIGHT("R", coordinates -> coordinates.setColumn(coordinates.getColumn() + 1)),
    LEFT("L", coordinates -> coordinates.setColumn(coordinates.getColumn() - 1)),
    UP("U", coordinates -> coordinates.setRow(coordinates.getRow() - 1)),
    DOWN("D", coordinates -> coordinates.setRow(coordinates.getRow() + 1));

    private final String key;

    private final Consumer<Coordinates> moveRopeEnd;

    public static StepType getByKey(String key) {
        for (StepType value : StepType.values()) {
            if (value.getKey().equals(key)) {
                return value;
            }
        }
        return null;
    }
}
