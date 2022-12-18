package de.barryallenofearth.adventofcode2022.riddle.o.common;

import de.barryallenofearth.adventofcode2022.riddle.o.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.o.model.SensorClosestBeacon;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindPositionsWithNoBeacons {

	public static int findOccupiedPlacesInLine(int row, List<SensorClosestBeacon> sensorClosestBeaconList) {
		final int min = sensorClosestBeaconList.stream()
				.mapToInt(sensorClosestBeacon -> sensorClosestBeacon.getSensor().getRow() - sensorClosestBeacon.getManhattenDistance())
				.min().getAsInt();
		final int max = sensorClosestBeaconList.stream()
				.mapToInt(sensorClosestBeacon -> sensorClosestBeacon.getSensor().getRow() + sensorClosestBeacon.getManhattenDistance())
				.max().getAsInt();

		final Set<Coordinates> occupiedFields = new HashSet<>();
		for (SensorClosestBeacon sensorClosestBeacon : sensorClosestBeaconList) {
			for (int column = min - 1; column <= max + 1; column++) {
				final Coordinates currentCoordinates = new Coordinates(row, column);
				if (currentCoordinates.equals(sensorClosestBeacon.getBeacon())) {
					continue;
				}
				final int sensorCurrentLocationDistance = Math.abs(sensorClosestBeacon.getSensor().getRow() - row) + Math.abs(sensorClosestBeacon.getSensor().getColumn() - column);
				if (sensorCurrentLocationDistance <= sensorClosestBeacon.getManhattenDistance()) {
					occupiedFields.add(currentCoordinates);
				}
			}
		}

		return occupiedFields.size();
	}
}
