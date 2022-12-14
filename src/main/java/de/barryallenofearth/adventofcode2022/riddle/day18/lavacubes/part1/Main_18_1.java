package de.barryallenofearth.adventofcode2022.riddle.day18.lavacubes.part1;

import de.barryallenofearth.adventofcode2022.riddle.day18.lavacubes.common.model.Cube;
import de.barryallenofearth.adventofcode2022.riddle.day18.lavacubes.common.util.ReadCubes;

import java.util.List;

public class Main_18_1 {
    public static void main(String[] args) {
        final List<Cube> cubes = ReadCubes.readCubes();
        System.out.println(FreeSidesCounter.countFreeSides(cubes));
    }
}
