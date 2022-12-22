package de.barryallenofearth.adventofcode2022.riddle.r.part1;

import de.barryallenofearth.adventofcode2022.riddle.r.common.model.Cube;
import de.barryallenofearth.adventofcode2022.riddle.r.common.util.NeighborCounter;
import de.barryallenofearth.adventofcode2022.riddle.r.common.util.ReadCubes;

import java.util.List;

public class Main_18_1 {
    public static void main(String[] args) {
        final List<Cube> cubes = ReadCubes.readCubes();
        System.out.println(NeighborCounter.countFreeSides(cubes));
    }
}
