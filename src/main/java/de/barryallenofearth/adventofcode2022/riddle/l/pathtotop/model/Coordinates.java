package de.barryallenofearth.adventofcode2022.riddle.l.pathtotop.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Coordinates {
	private final int row;

	private final int column;
}
