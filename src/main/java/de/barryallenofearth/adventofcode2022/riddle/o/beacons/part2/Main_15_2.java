package de.barryallenofearth.adventofcode2022.riddle.o.beacons.part2;

import de.barryallenofearth.adventofcode2022.riddle.o.beacons.common.SensorBeaconReader;
import de.barryallenofearth.adventofcode2022.riddle.o.beacons.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.o.beacons.model.SensorClosestBeacon;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public class Main_15_2 {
	public static void main(String[] args) throws IOException, URISyntaxException {

		final List<SensorClosestBeacon> sensorClosestBeacons = SensorBeaconReader.readSensorBeacons();
		final Optional<Coordinates> first = BorderFinder.findSensorBorders(sensorClosestBeacons);
		if (first.isPresent()) {
			Coordinates currentCoordinates = first.get();
			System.out.println("The only possible beacon location is x=" + currentCoordinates.getX() + ", y=" + currentCoordinates.getY());
			System.out.println("The tuning frequency is " + (4_000_000L * (long)(currentCoordinates.getX()) + (long)(currentCoordinates.getY())));
		} else {
			System.out.println("No beacon position could be found.");
		}
	}

}
