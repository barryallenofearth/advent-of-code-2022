package de.barryallenofearth.adventofcode2022.riddle.day23.common.util;

import de.barryallenofearth.adventofcode2022.riddle.day23.common.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.day23.common.model.Elf;
import de.barryallenofearth.adventofcode2022.riddle.day23.common.model.MovingDirection;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ElfMover {

	public void moveForXRounds(List<Elf> elves, int numberOfRounds) {
		for (int round = 0; round < numberOfRounds; round++) {
			proposeNextPosition(round, elves);
			//no elf needs to move anymore
			if (elves.stream().noneMatch(elf -> elf.getProposedNextPosition().isPresent())) {
				return;
			}

			preventMovingToSameSpace(elves);
			moveElves(elves);

			System.out.println("after round " + round);
		}
	}

	private void moveElves(List<Elf> elves) {
		for (Elf elf : elves) {
			if (elf.getProposedNextPosition().isEmpty()) {
				continue;
			}
			elf.getCoordinates().setX(elf.getProposedNextPosition().get().getX());
			elf.getCoordinates().setY(elf.getProposedNextPosition().get().getY());
			elf.setProposedNextPosition(Optional.empty());
		}
	}

	private void preventMovingToSameSpace(List<Elf> elves) {
		for (Elf elf : elves) {
			if (elf.getProposedNextPosition().isEmpty()) {
				continue;
			}
			for (Elf comparison : elves) {
				if (comparison.getProposedNextPosition().isEmpty()) {
					continue;
				}
				if (elf.equals(comparison)) {
					continue;
				}

				if (elf.getProposedNextPosition().equals(comparison.getProposedNextPosition())) {
					elf.setProposedNextPosition(Optional.empty());
					comparison.setProposedNextPosition(Optional.empty());
				}
			}
		}
	}

	private void proposeNextPosition(int round, List<Elf> elves) {
		final List<Coordinates> allElfCoordinates = elves.stream()
				.map(Elf::getCoordinates)
				.collect(Collectors.toList());

		for (Elf elf : elves) {
			//no elves around
			int testedDirections = 0;
			MovingDirection[] movingDirections = MovingDirection.values();
			for (int index = 0; index < movingDirections.length; index++) {
				MovingDirection movingDirection = movingDirections[(index + round) % movingDirections.length];
				if (movingDirection.getIsPositionFree().test(elf.getCoordinates(), allElfCoordinates)) {
					testedDirections++;
					if (elf.getProposedNextPosition().isEmpty()) {
						final Coordinates proposedCoordinates = new Coordinates(elf.getCoordinates().getX(), elf.getCoordinates().getY());
						movingDirection.getMove().accept(proposedCoordinates);
						elf.setProposedNextPosition(Optional.of(proposedCoordinates));
					}
				}
			}
			if (testedDirections == movingDirections.length) {
				elf.setProposedNextPosition(Optional.empty());
			}
		}
	}

}
