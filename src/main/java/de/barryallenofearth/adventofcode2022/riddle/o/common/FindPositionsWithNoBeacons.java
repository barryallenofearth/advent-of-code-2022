package de.barryallenofearth.adventofcode2022.riddle.o.common;

import de.barryallenofearth.adventofcode2022.riddle.o.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.o.model.SensorClosestBeacon;

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
			String symbolToPrint = ".";
			final Coordinates currentCoordinates = new Coordinates(xValue, yValue);
			for (SensorClosestBeacon sensorClosestBeacon : sensorClosestBeaconList) {
				if (currentCoordinates.equals(sensorClosestBeacon.getBeacon())) {
					symbolToPrint = "B";
					break;
				} else if (currentCoordinates.equals(sensorClosestBeacon.getSensor())) {
					symbolToPrint = "S";
					break;
				}
				final int sensorCurrentLocationDistance = Math.abs(sensorClosestBeacon.getSensor().getY() - yValue) + Math.abs(sensorClosestBeacon.getSensor().getX() - xValue);
				if (sensorCurrentLocationDistance <= sensorClosestBeacon.getManhattenDistance()) {
					symbolToPrint = "#";
					occupiedFields.add(currentCoordinates);
					break;
				}
			}
			//System.out.print(symbolToPrint);
		}

		return occupiedFields.size();
	}
}
