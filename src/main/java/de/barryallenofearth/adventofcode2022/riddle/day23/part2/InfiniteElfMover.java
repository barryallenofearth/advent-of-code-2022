package de.barryallenofearth.adventofcode2022.riddle.day23.part2;

import de.barryallenofearth.adventofcode2022.riddle.day23.common.model.Elf;
import de.barryallenofearth.adventofcode2022.riddle.day23.common.util.ElfMover;

import java.util.List;

public class InfiniteElfMover extends ElfMover {
	@Override public int move(List<Elf> elves, int numberOfRounds) {
		int round = 1;
		while (true) {
			proposeNextPosition(round, elves);
			//no elf needs to move anymore
			if (elves.stream().noneMatch(elf -> elf.getProposedNextPosition().isPresent())) {
				return round;
			}

			preventMovingToSameSpace(elves);
			moveElves(elves);

			System.out.println("after round " + round);
			round++;
		}
	}
}
