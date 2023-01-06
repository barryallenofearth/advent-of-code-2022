package de.barryallenofearth.adventofcode2022.riddle.day17.rocks.part2;

import de.barryallenofearth.adventofcode2022.riddle.day17.rocks.common.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.day17.rocks.common.model.RockType;
import lombok.Data;

import java.util.Set;

@Data
public class Cycle {
	private final long lastMovementIndex;

	private final RockType rockType;

	private final Set<Coordinates> currentState;

}
