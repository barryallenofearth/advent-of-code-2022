package de.barryallenofearth.adventofcode2022.riddle.day15.beacons.common;

import de.barryallenofearth.adventofcode2022.riddle.day15.beacons.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.day15.beacons.model.SensorClosestBeacon;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindPositionsWithNoBeacons {

	public static int findOccupiedPlacesInLine(int yValue, List<SensorClosestBeacon> sensorClosestBeaconList) {
		final int min = sensorClosestBeaconList.stream()
				.mapToInt(sensorClosestBeacon -> sensorClosestBeacon.getSensor().getX() - sensorClosestBeacon.getManhattenDistance())
				.min().getAsInt();
		final int max = sensorClosestBeaconList.stream()
				.mapToInt(sensorClosestBeacon -> sensorClosestBeacon.getSensor().getX() + sensorClosestBeacon.getManhattenDistance())
				.max().getAsInt();

		System.out.println("scanning from x=" + min + " to " + max);
		final Set<Coordinates> occupiedFields = new HashSet<>();
		for (int xValue = min - 1; xValue <= max + 1; xValue++) {
			final Coordinates currentCoordinates = new Coordinates(xValue, yValue);
			boolean isAvailable = isFieldAvailable(sensorClosestBeaconList, currentCoordinates);
			if (!isAvailable) {
				occupiedFields.add(currentCoordinates);
			}
		}

		return occupiedFields.size();
	}

	public static boolean isFieldAvailable(List<SensorClosestBeacon> sensorClosestBeaconList, Coordinates currentCoordinates) {
		for (SensorClosestBeacon sensorClosestBeacon : sensorClosestBeaconList) {
			final int sensorCurrentLocationDistance = Math.abs(sensorClosestBeacon.getSensor().getY() - currentCoordinates.getY()) + Math.abs(sensorClosestBeacon.getSensor().getX() - currentCoordinates.getX());
			if (sensorCurrentLocationDistance <= sensorClosestBeacon.getManhattenDistance()) {
				return false;
			}
		}
		return true;
	}
}
