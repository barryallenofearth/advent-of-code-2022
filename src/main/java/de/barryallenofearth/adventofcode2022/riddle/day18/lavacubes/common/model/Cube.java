package de.barryallenofearth.adventofcode2022.riddle.day18.lavacubes.common.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Cube {
    private final int x;

    private final int y;

    private final int z;
}
