package de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.part1;

import de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.model.InitialState;
import de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.util.ValleyReader;

public class Main_24_1 {
	public static void main(String[] args) {
		final InitialState initialState = new ValleyReader().read();
		System.out.println(initialState.getValley());
	}
}
