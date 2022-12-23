package de.barryallenofearth.adventofcode2022.riddle.r.part2;

import de.barryallenofearth.adventofcode2022.riddle.r.common.model.Cube;

public class CubeIsNonBubble extends Exception {
    private final Cube neighbor;

    public CubeIsNonBubble(Cube neighbor) {
        this.neighbor = neighbor;
    }
}
