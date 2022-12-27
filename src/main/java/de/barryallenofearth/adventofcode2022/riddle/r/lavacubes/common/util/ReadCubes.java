package de.barryallenofearth.adventofcode2022.riddle.r.lavacubes.common.util;

import de.barryallenofearth.adventofcode2022.riddle.r.lavacubes.common.model.Cube;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.util.ArrayList;
import java.util.List;

public class ReadCubes {

    public static List<Cube> readCubes() {
        List<Cube> cubes = new ArrayList<>();
        final List<String> strings = RiddleFileReader.readAllLines("riddle-18.txt");
        for (String string : strings) {
            final String[] split = string.split(",");
            cubes.add(new Cube(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
        }
        return cubes;
    }
}
