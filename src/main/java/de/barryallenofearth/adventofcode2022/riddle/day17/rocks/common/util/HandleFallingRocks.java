package de.barryallenofearth.adventofcode2022.riddle.day17.rocks.common.util;

import de.barryallenofearth.adventofcode2022.riddle.day17.rocks.common.model.Cave;
import de.barryallenofearth.adventofcode2022.riddle.day17.rocks.common.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.day17.rocks.common.model.Rock;
import de.barryallenofearth.adventofcode2022.riddle.day17.rocks.common.model.RockType;
import de.barryallenofearth.adventofcode2022.riddle.day17.rocks.part2.Cycle;
import de.barryallenofearth.adventofcode2022.riddle.day17.rocks.part2.CycleValue;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class HandleFallingRocks {

	public static final int STEPS_TO_DISPLAY = 1_000_000;

	public static void handleFallingRocks(Cave cave, long numberOfRocks) {
		final String gasStreams = RiddleFileReader.readAllLines("riddle-17.txt").get(0);
		long lastMovementIndex = 0;
		final LocalDateTime startingTime = LocalDateTime.now();
		Map.Entry<Cycle, CycleValue> firstCycle = null;
		for (long rockCount = 0; rockCount < numberOfRocks; rockCount++) {
			if (rockCount % STEPS_TO_DISPLAY == 0) {
				System.out.println(Duration.between(startingTime, LocalDateTime.now()).getSeconds() + "s " + rockCount / STEPS_TO_DISPLAY + "x10^6: " + ((double) rockCount) / numberOfRocks * 100. + "% processed");
			}
			lastMovementIndex = RockMover.handleNewRock(rockCount, lastMovementIndex, cave, gasStreams);
			final Optional<Map.Entry<Cycle, CycleValue>> cycleValue = cave.checkIfStateIsPresent(rockCount, RockType.values()[(int) (rockCount % RockType.values().length)], lastMovementIndex);
			if (cycleValue.isPresent()) {
				final Map.Entry<Cycle, CycleValue> entry = cycleValue.get();
				if (firstCycle == null) {
					firstCycle = entry;
				}
				if (Objects.equals(firstCycle.getKey(), entry.getKey())) {
					System.out.println(rockCount + " " + cave.getCurrentRockHeight());

				}
			}
		}
		printCave(cave);
		System.out.println();

	}

	public static void printCave(Cave cave) {
		for (long y = cave.getCurrentRockHeight() + 5; y >= 0; y--) {
			for (int x = Cave.X_LEFT_BORDER; x <= Cave.X_RIGHT_BORDER; x++) {
				final Coordinates currentCoordinate = new Coordinates(x, y);
				if (x == Cave.X_LEFT_BORDER || x == Cave.X_RIGHT_BORDER) {
					System.out.print("|");
				} else if (cave.getOccupiedFields().contains(currentCoordinate)) {
					System.out.print("#");
				} else {
					System.out.print(".");
				}
			}
			System.out.println(y);
		}
	}

	public static void printCave(Cave cave, Rock rock) {
		for (long y = cave.getCurrentRockHeight() + 5; y >= 0; y--) {
			for (int x = Cave.X_LEFT_BORDER; x <= Cave.X_RIGHT_BORDER; x++) {
				final Coordinates currentCoordinate = new Coordinates(x, y);
				if (x == Cave.X_LEFT_BORDER || x == Cave.X_RIGHT_BORDER) {
					System.out.print("|");
				} else if (cave.getOccupiedFields().contains(currentCoordinate)) {
					System.out.print("#");
				} else if (rock.getComponents().contains(currentCoordinate)) {
					System.out.print("@");
				} else {
					System.out.print(".");
				}
			}
			System.out.println(y);
		}
	}
}
