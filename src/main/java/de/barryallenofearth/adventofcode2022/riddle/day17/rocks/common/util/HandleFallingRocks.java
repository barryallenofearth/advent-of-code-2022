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
import java.util.stream.Collectors;

public class HandleFallingRocks {

	public static final int STEPS_TO_DISPLAY = 1_000_000;

	public static void handleFallingRocks(Cave cave, long numberOfRocks) {
		final String gasStreams = RiddleFileReader.readAllLines("riddle-17.txt").get(0);
		long lastMovementIndex = 0;
		final LocalDateTime startingTime = LocalDateTime.now();
		long cycleLength = 0;
		long cycleIncrement = 0;
		for (long rockCount = 0; rockCount < numberOfRocks; rockCount++) {
			if (rockCount % STEPS_TO_DISPLAY == 0) {
				System.out.println(Duration.between(startingTime, LocalDateTime.now()).getSeconds() + "s " + rockCount / STEPS_TO_DISPLAY + "x10^6: " + ((double) rockCount) / numberOfRocks * 100. + "% processed");
			}
			lastMovementIndex = RockMover.handleNewRock(rockCount, lastMovementIndex, cave, gasStreams);
			final long minHeight = cave.getOccupiedFields().stream().mapToLong(Coordinates::getY).min().getAsLong();
			final Cycle cycle = new Cycle(lastMovementIndex, RockType.values()[(int) (rockCount % RockType.values().length)], cave.getOccupiedFields().stream()
					.map(coordinates -> new Coordinates(coordinates.getX(), coordinates.getY() - minHeight))
					.collect(Collectors.toSet()));
			final Optional<Map.Entry<Cycle, CycleValue>> cycleValue = cave.checkIfStateIsPresent(cycle, rockCount);
			if (cycleValue.isPresent()) {
				final Map.Entry<Cycle, CycleValue> entry = cycleValue.get();
				cycleLength = rockCount - entry.getValue().getRockCount();
				cycleIncrement = cave.getCurrentRockHeight() - entry.getValue().getCurrentHeight();
				System.out.println("Rock count: " + rockCount + " height: " + cave.getCurrentRockHeight());
				System.out.println("cycle length: " + cycleLength + ", cycle increment: " + cycleIncrement);

				final long remainingRockCount = numberOfRocks - rockCount;
				final long remainingCycles = remainingRockCount / cycleLength;

				final long heightAfterCycles = cave.getCurrentRockHeight() + remainingCycles * cycleIncrement;
				cave.getOccupiedFields().stream().forEach(coordinates -> coordinates.setY(coordinates.getY() - cave.getCurrentRockHeight() + heightAfterCycles));
				rockCount = rockCount + remainingCycles * cycleLength;
				System.out.println("remaining cycles were: " + remainingCycles + "\nnew height: " + heightAfterCycles);
				System.out.println("new rockcount: " + rockCount);
			}
		}

	}

}
