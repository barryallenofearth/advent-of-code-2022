package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Instruction {
	private final InstructionType instructionType;

	private final Rotation rotation;

	private final int numberOfSteps;
}
