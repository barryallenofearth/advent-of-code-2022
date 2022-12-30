package de.barryallenofearth.adventofcode2022.riddle.day18.lavacubes.part2.model;

import de.barryallenofearth.adventofcode2022.riddle.day18.lavacubes.common.model.Cube;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiPredicate;

@Getter
@RequiredArgsConstructor
public enum Facing {
	LEFT((cube1, cube2) -> areYAndZEqual(cube1, cube2) && cube1.getX() - cube2.getX() == 1),
	RIGHT((cube1, cube2) -> areYAndZEqual(cube1, cube2) && cube1.getX() - cube2.getX() == -1),
	BOTTOM((cube1, cube2) -> areXAndYEqual(cube1, cube2) && cube1.getZ() - cube2.getZ() == 1),
	TOP((cube1, cube2) -> areXAndYEqual(cube1, cube2) && cube1.getZ() - cube2.getZ() == -1),
	FRONT((cube1, cube2) -> areXAndZEqual(cube1, cube2) && cube1.getY() - cube2.getY() == 1),
	BACK((cube1, cube2) -> areXAndZEqual(cube1, cube2) && cube1.getY() - cube2.getY() == -1);

	private static boolean areYAndZEqual(Cube cube1, Cube cube2) {
		return cube1.getY() == cube2.getY() && cube1.getZ() == cube2.getZ();
	}

	private static boolean areXAndYEqual(Cube cube1, Cube cube2) {
		return cube1.getY() == cube2.getY() && cube1.getX() == cube2.getX();
	}

	private static boolean areXAndZEqual(Cube cube1, Cube cube2) {
		return cube1.getZ() == cube2.getZ() && cube1.getX() == cube2.getX();
	}

	private final BiPredicate<Cube, Cube> isInContact;

}
