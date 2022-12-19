package de.barryallenofearth.adventofcode2022.riddle.o.part2;

import de.barryallenofearth.adventofcode2022.riddle.o.common.FindPositionsWithNoBeacons;
import de.barryallenofearth.adventofcode2022.riddle.o.common.SensorBeaconReader;
import de.barryallenofearth.adventofcode2022.riddle.o.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.o.model.SensorClosestBeacon;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
