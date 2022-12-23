package de.barryallenofearth.adventofcode2022.riddle.r.part2;

import de.barryallenofearth.adventofcode2022.riddle.r.common.util.ReadCubes;
import de.barryallenofearth.adventofcode2022.riddle.r.part2.model.CubeWithFacings;

import java.util.List;
import java.util.stream.Collectors;

public class Main_18_2 {
    public static void main(String[] args) {
        final List<CubeWithFacings> cubesWithFacings = ReadCubes.readCubes().stream()
                .map(CubeWithFacings::new)
                .collect(Collectors.toList());

        final long freeOuterSide = FreeOuterSidesCounter.countFreeOuterSide(cubesWithFacings);
        System.out.println(freeOuterSide + " are on the outside of the particle.");
    }

}
