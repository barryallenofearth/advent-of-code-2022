package de.barryallenofearth.adventofcode2022.riddle.day17.rocks.common.model;

import de.barryallenofearth.adventofcode2022.riddle.day17.rocks.part2.Cycle;
import de.barryallenofearth.adventofcode2022.riddle.day17.rocks.part2.CycleValue;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class Cave {

	public static final int X_LEFT_BORDER = -1;

	public static final int X_RIGHT_BORDER = 7;

	private long currentRockHeight = -1;

	private Set<Coordinates> occupiedFields = new HashSet<>();

	private final Map<Cycle, CycleValue> knownStates = new HashMap<>(2048);

	public void addRock(Rock shape) {
		occupiedFields.addAll(shape.getComponents());

		final long[] minHeight = { Integer.MAX_VALUE };

		currentRockHeight = 0;
		for (long x = X_LEFT_BORDER + 1; x < X_RIGHT_BORDER; x++) {
			final long highestHeightInColumn = getHighestHeightInColumn(x);
			if (highestHeightInColumn < minHeight[0]) {
				minHeight[0] = highestHeightInColumn;
			}
			if (highestHeightInColumn > currentRockHeight) {
				currentRockHeight = highestHeightInColumn;
			}
		}

		occupiedFields = occupiedFields.stream()
				.filter(coordinates -> coordinates.getY() > minHeight[0] - 10)
				.collect(Collectors.toSet());
	}

	private long getHighestHeightInColumn(long x) {
		return occupiedFields.stream().filter(coordinates -> coordinates.getX() == x).mapToLong(Coordinates::getY).max().orElse(-1);
	}

	public Optional<Map.Entry<Cycle, CycleValue>> checkIfStateIsPresent(Cycle cycle, long rockCount) {

		final Optional<Map.Entry<Cycle, CycleValue>> cycleValue = knownStates.entrySet().stream().filter(entry -> entry.getKey().equals(cycle)).findFirst();
		if (cycleValue.isPresent()) {
			return cycleValue;
		}
		if (knownStates.size() < 2048) {
			knownStates.put(cycle, new CycleValue(rockCount, currentRockHeight));
		}

		return Optional.empty();
	}

}
