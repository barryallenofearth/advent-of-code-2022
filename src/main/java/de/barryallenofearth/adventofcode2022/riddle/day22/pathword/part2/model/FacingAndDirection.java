package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.part2.model;

import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.Direction;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FacingAndDirection {

	private final int facingID;

	private final Direction direction;

	private final Coordinates coordinates;
}
