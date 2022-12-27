package de.barryallenofearth.adventofcode2022.riddle.r.lavacubes.part2.model;

import de.barryallenofearth.adventofcode2022.riddle.r.lavacubes.common.model.Cube;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BubbleModel {
    private final List<Cube> bubbleCubes = new ArrayList<>();

    private boolean isBubble = true;
}
