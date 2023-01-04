package de.barryallenofearth.adventofcode2022.riddle.day23.common.model;

import de.barryallenofearth.adventofcode2022.riddle.day23.common.model.Coordinates;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public enum MovingDirections {
	NORTH(coordinates -> coordinates.setY(coordinates.getY() - 1), (elfPosition, allElves) -> {
		final Coordinates north = new Coordinates(elfPosition.getX(), elfPosition.getY() - 1);
		final Coordinates northEast = new Coordinates(elfPosition.getX() + 1, elfPosition.getY() - 1);
		final Coordinates northWest = new Coordinates(elfPosition.getX() - 1, elfPosition.getY() - 1);
		return !allElves.contains(north) && !allElves.contains(northEast) && !allElves.contains(northWest);
	}),
	SOUTH(coordinates -> coordinates.setY(coordinates.getY() + 1), (elfPosition, allElves) -> {
		final Coordinates south = new Coordinates(elfPosition.getX(), elfPosition.getY() + 1);
		final Coordinates southEast = new Coordinates(elfPosition.getX() + 1, elfPosition.getY() + 1);
		final Coordinates southWest = new Coordinates(elfPosition.getX() - 1, elfPosition.getY() + 1);
		return !allElves.contains(south) && !allElves.contains(southEast) && !allElves.contains(southWest);
	}),
	WEST(coordinates -> coordinates.setX(coordinates.getX() - 1), (elfPosition, allElves) -> {
		final Coordinates west = new Coordinates(elfPosition.getX() - 1, elfPosition.getY());
		final Coordinates southWest = new Coordinates(elfPosition.getX() - 1, elfPosition.getY() + 1);
		final Coordinates northWest = new Coordinates(elfPosition.getX() - 1, elfPosition.getY() - 1);
		return !allElves.contains(west) && !allElves.contains(southWest) && !allElves.contains(northWest);
	}),
	EAST(coordinates -> coordinates.setX(coordinates.getX() + 1), (elfPosition, allElves) -> {
		final Coordinates east = new Coordinates(elfPosition.getX() + 1, elfPosition.getY());
		final Coordinates southEast = new Coordinates(elfPosition.getX() + 1, elfPosition.getY() + 1);
		final Coordinates northEast = new Coordinates(elfPosition.getX() + 1, elfPosition.getY() - 1);
		return !allElves.contains(east) && !allElves.contains(southEast) && !allElves.contains(northEast);
	});

	private final Consumer<Coordinates> move;

	private final BiPredicate<Coordinates, List<Coordinates>> isPositionFree;

}
