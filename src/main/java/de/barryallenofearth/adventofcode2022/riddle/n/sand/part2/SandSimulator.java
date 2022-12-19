package de.barryallenofearth.adventofcode2022.riddle.n.sand.part2;

import de.barryallenofearth.adventofcode2022.riddle.n.sand.model.CaveModel;
import de.barryallenofearth.adventofcode2022.riddle.n.sand.model.Coordinates;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SandSimulator {

	public static final Coordinates SAND_START_COORDINATES = new Coordinates(500, 0);

	public static Set<Coordinates> simulateSand(CaveModel caveModel) {
		final Set<Coordinates> sandRestingCoordinates = new HashSet<>();
		do {
			Coordinates currentCoordinates = new Coordinates(SAND_START_COORDINATES.getRow(), SAND_START_COORDINATES.getColumn());
			while (!sandRestingCoordinates.contains(currentCoordinates)) {
				final Optional<Coordinates> coordinates = nextSandStep(currentCoordinates, sandRestingCoordinates, caveModel);
				if (coordinates.isEmpty()) {
					sandRestingCoordinates.add(currentCoordinates);
					break;
				}
				currentCoordinates = coordinates.get();
			}
		} while (!sandRestingCoordinates.contains(SAND_START_COORDINATES));
		System.out.println(sandRestingCoordinates.size() + " could be placed before the sand overflows the structure.");
		return sandRestingCoordinates;
	}

	public static Optional<Coordinates> nextSandStep(Coordinates currentSandPosition, Set<Coordinates> restingSandCoordinates, CaveModel caveModel) {
		if (currentSandPosition.getColumn() == caveModel.getMaxY() + 1) {
			return Optional.empty();
		}
		Coordinates down = new Coordinates(currentSandPosition.getRow(), currentSandPosition.getColumn() + 1);
		if (!restingSandCoordinates.contains(down) && !caveModel.getRockCoordinates().contains(down)) {
			return Optional.of(down);
		}
		Coordinates leftDown = new Coordinates(currentSandPosition.getRow() - 1, currentSandPosition.getColumn() + 1);
		if (!restingSandCoordinates.contains(leftDown) && !caveModel.getRockCoordinates().contains(leftDown)) {
			return Optional.of(leftDown);
		}
		Coordinates rightDown = new Coordinates(currentSandPosition.getRow() + 1, currentSandPosition.getColumn() + 1);
		if (!restingSandCoordinates.contains(rightDown) && !caveModel.getRockCoordinates().contains(rightDown)) {
			return Optional.of(rightDown);
		}
		return Optional.empty();
	}
}
