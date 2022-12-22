package de.barryallenofearth.adventofcode2022.riddle.r.part2;

import de.barryallenofearth.adventofcode2022.riddle.r.part2.model.CubeWithFacings;
import de.barryallenofearth.adventofcode2022.riddle.r.part2.model.Facing;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FreeOuterSidesCounter {

	public static long countFreeOuterSide(List<CubeWithFacings> cubes) {
		for (CubeWithFacings currentCube : cubes) {
			cubes.forEach(cubeWithFacing -> {
				for (int index = currentCube.getFacings().size() - 1; index >= 0; index--) {
					final Facing facing = currentCube.getFacings().get(index);
					if (facing.getIsInContact().test(currentCube.getCube(), cubeWithFacing.getCube())) {
						currentCube.getFacings().remove(facing);
					} else if (facing.getIsFacingToBubble().test(currentCube.getCube(), cubeWithFacing.getCube())) {
						currentCube.getFacings().remove(facing);
					}
				}
			});
		}
		return cubes.stream().flatMap(cubeWithFacings -> cubeWithFacings.getFacings().stream()).filter(Objects::nonNull).count();
	}
}