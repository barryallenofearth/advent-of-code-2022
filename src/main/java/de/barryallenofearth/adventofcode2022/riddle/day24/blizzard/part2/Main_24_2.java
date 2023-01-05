package de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.part2;

import de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.model.ExpeditionState;
import de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.model.InitialState;
import de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.model.Valley;
import de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.util.ValleyReader;
import de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.util.ValleyWalker;

public class Main_24_2 {
	public static void main(String[] args) {

		final InitialState initialState = new ValleyReader().read();
		System.out.println(initialState.getValley());
		final ValleyWalker valleyWalker = new ValleyWalker();
		ExpeditionState expeditionState = valleyWalker.walkInMinimumTime(initialState, 0);
		System.out.println(expeditionState.getMinute() + " needed to reach the exit.");

		final Valley valley = initialState.getValley();
		Coordinates entry = valley.getEntry();
		valley.setEntry(valley.getExit());
		valley.setExit(entry);
		expeditionState = valleyWalker.walkInMinimumTime(initialState, expeditionState.getMinute());
		System.out.println(expeditionState.getMinute() + " needed to reach the entrance.");

		entry = valley.getEntry();
		valley.setEntry(valley.getExit());
		valley.setExit(entry);

		expeditionState = valleyWalker.walkInMinimumTime(initialState, expeditionState.getMinute());
		System.out.println(expeditionState.getMinute() + " needed to reach the exit.");
	}
}
