package de.barryallenofearth.adventofcode2022.riddle.day18.lavacubes.part2;

import de.barryallenofearth.adventofcode2022.riddle.day18.lavacubes.common.model.Cube;

public class CubeIsNonBubble extends Exception {
    private final Cube neighbor;

    public CubeIsNonBubble(Cube neighbor) {
        this.neighbor = neighbor;
    }
}
