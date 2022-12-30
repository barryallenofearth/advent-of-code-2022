package de.barryallenofearth.adventofcode2022.riddle.day18.lavacubes.part1;

import de.barryallenofearth.adventofcode2022.riddle.day18.lavacubes.common.model.Cube;

import java.util.List;

public class FreeSidesCounter {

    public static long countFreeSides(List<Cube> cubes) {
        long freeSides = 0;
        for (Cube cube : cubes) {
            freeSides += 6 - cubes.stream()
                    .filter(currentCube -> !cube.equals(currentCube))
                    .filter(currentCube -> {
                        if (cube.getX() == currentCube.getX() && cube.getY() == currentCube.getY()) {
                            return Math.abs(cube.getZ() - currentCube.getZ()) == 1;
                        } else if (cube.getZ() == currentCube.getZ() && cube.getY() == currentCube.getY()) {
                            return Math.abs(cube.getX() - currentCube.getX()) == 1;
                        } else if (cube.getZ() == currentCube.getZ() && cube.getX() == currentCube.getX()) {
                            return Math.abs(cube.getY() - currentCube.getY()) == 1;
                        }
                        return false;
                    })
                    .count();
        }

        return freeSides;
    }
}
