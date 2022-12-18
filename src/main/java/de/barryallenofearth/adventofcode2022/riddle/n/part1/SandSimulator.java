package de.barryallenofearth.adventofcode2022.riddle.n.part1;

import de.barryallenofearth.adventofcode2022.riddle.n.model.CaveModel;
import de.barryallenofearth.adventofcode2022.riddle.n.model.Coordinates;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SandSimulator {

	public static final Coordinates SAND_START_COORDINATES = new Coordinates(500, 0);

	public static Set<Coordinates> simulateSand(CaveModel caveModel) {
		final Set<Coordinates> sandRestingCoordinates = new HashSet<>();
		do {
			Coordinates currentCoordinates = new Coordinates(SAND_START_COORDINATES.getRow(), SAND_START_COORDINATES.getColumn());
			while (currentCoordinates.getColumn() < caveModel.getMaxY()) {
				final Optional<Coordinates> coordinates = nextSandStep(currentCoordinates, sandRestingCoordinates, caveModel.getRockCoordinates());
				if (coordinates.isEmpty()) {
					sandRestingCoordinates.add(currentCoordinates);
					break;
				}
				currentCoordinates = coordinates.get();
			}
			if (currentCoordinates.getColumn() >= caveModel.getMaxY()) {
				break;
			}
		} while (true);
		System.out.println(sandRestingCoordinates.size() + " could be placed before the sand overflows the structure.");
		return sandRestingCoordinates;
	}

	public static Optional<Coordinates> nextSandStep(Coordinates currentSandPosition, Set<Coordinates> restingSandCoordinates, Set<Coordinates> rockCoordinates) {
		Coordinates down = new Coordinates(currentSandPosition.getRow(), currentSandPosition.getColumn() + 1);
		if (!restingSandCoordinates.contains(down) && !rockCoordinates.contains(down)) {
			return Optional.of(down);
		}
		Coordinates leftDown = new Coordinates(currentSandPosition.getRow() - 1, currentSandPosition.getColumn() + 1);
		if (!restingSandCoordinates.contains(leftDown) && !rockCoordinates.contains(leftDown)) {
			return Optional.of(leftDown);
		}
		Coordinates rightDown = new Coordinates(currentSandPosition.getRow() + 1, currentSandPosition.getColumn() + 1);
		if (!restingSandCoordinates.contains(rightDown) && !rockCoordinates.contains(rightDown)) {
			return Optional.of(rightDown);
		}
		return Optional.empty();
	}
}
