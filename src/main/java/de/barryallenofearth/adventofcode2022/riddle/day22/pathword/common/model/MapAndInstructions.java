package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class MapAndInstructions {
	private final List<Coordinates> boardCoordinates = new ArrayList<>();

	private final List<Coordinates> blockedCoordinates = new ArrayList<>();

	private final List<Instruction> instructions = new ArrayList<>();
}
