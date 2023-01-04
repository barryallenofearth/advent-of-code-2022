package de.barryallenofearth.adventofcode2022.riddle.day23.spreadout.part1;

import de.barryallenofearth.adventofcode2022.riddle.day23.spreadout.common.model.Elf;
import de.barryallenofearth.adventofcode2022.riddle.day23.spreadout.common.util.ElfMover;

import java.util.List;

public class RoundBasedElfMover extends ElfMover {

	@Override
	public int move(List<Elf> elves, int numberOfRounds) {
		for (int round = 1; round <= numberOfRounds; round++) {
			proposeNextPosition(round, elves);
			//no elf needs to move anymore
			if (elves.stream().noneMatch(elf -> elf.getProposedNextPosition().isPresent())) {
				return round;
			}

			preventMovingToSameSpace(elves);
			moveElves(elves);

			System.out.println("after round " + round);
		}
		return numberOfRounds;
	}
}