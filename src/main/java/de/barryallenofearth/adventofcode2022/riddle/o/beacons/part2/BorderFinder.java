package de.barryallenofearth.adventofcode2022.riddle.o.beacons.part2;

import de.barryallenofearth.adventofcode2022.riddle.o.beacons.common.FindPositionsWithNoBeacons;
import de.barryallenofearth.adventofcode2022.riddle.o.beacons.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.o.beacons.model.SensorClosestBeacon;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BorderFinder {
	public static final int MIN_SCAN = 0;

	public static final int MAX_SCAN = 4_000_000;

	public static Optional<Coordinates> findSensorBorders(List<SensorClosestBeacon> sensorClosestBeacons) {
		Set<Coordinates> circleCoordinate = new HashSet<>();
		for (SensorClosestBeacon sensorClosestBeacon : sensorClosestBeacons) {
			System.out.println("Current beacon: " + sensorClosestBeacons.indexOf(sensorClosestBeacon));
			final int minY = sensorClosestBeacon.getSensor().getY() - sensorClosestBeacon.getManhattenDistance();
			final int maxY = sensorClosestBeacon.getSensor().getY() + sensorClosestBeacon.getManhattenDistance();
			for (int yValue = minY; yValue <= maxY; yValue++) {
				if (yValue < MIN_SCAN || yValue > MAX_SCAN) {
					continue;
				}
				int xWidth = 2 * sensorClosestBeacon.getManhattenDistance();
				if (yValue < sensorClosestBeacon.getSensor().getY()) {
					xWidth = 2 * (yValue - minY) + 1;
				} else if (yValue > sensorClosestBeacon.getSensor().getY()) {
					xWidth = 2 * (maxY - yValue) + 1;
				}
				final int minX = sensorClosestBeacon.getSensor().getX() - xWidth / 2 - 1;
				final int maxX = sensorClosestBeacon.getSensor().getX() + xWidth / 2 + 1;
				//System.out.println("Scan from " + minX + "," + minY + " to " + maxX + "," + maxY);
				if (minX >= MIN_SCAN && minX <= MAX_SCAN) {
					final Coordinates coordinates = new Coordinates(minX, yValue);
					if (FindPositionsWithNoBeacons.isFieldAvailable(sensorClosestBeacons, coordinates)) {
						return Optional.of(coordinates);
					}
				}
				if (maxX >= MIN_SCAN && maxX <= MAX_SCAN) {
					final Coordinates coordinates = new Coordinates(maxX, yValue);
					if (FindPositionsWithNoBeacons.isFieldAvailable(sensorClosestBeacons, coordinates)) {
						return Optional.of(coordinates);
					}
				}
			}

		}
		return Optional.empty();
	}
}
