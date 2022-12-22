package de.barryallenofearth.adventofcode2022.riddle.r.part2;

import de.barryallenofearth.adventofcode2022.riddle.r.common.model.Cube;

import java.util.List;

public class FreeOuterSidesCounter {

    public static long countFreeOuterSide(List<Cube> cubes) {
        long freeOuterSides = 0;
        for (Cube cube : cubes) {
            final long count = cubes.stream().filter(currentCube -> {
                        if (cube.getX() == currentCube.getX() && cube.getY() == currentCube.getY()) {
                            return Math.abs(cube.getZ() - currentCube.getZ()) == 1;
                        } else if (cube.getZ() == currentCube.getZ() && cube.getY() == currentCube.getY()) {
                            return Math.abs(cube.getX() - currentCube.getX()) == 1;
                        } else if (cube.getZ() == currentCube.getZ() && cube.getX() == currentCube.getX()) {
                            return Math.abs(cube.getY() - currentCube.getY()) == 1;
                        }
                        return false;
                    })
                    .filter(cube -> true)
                    .count();
        }
        return freeOuterSides;
    }
}
