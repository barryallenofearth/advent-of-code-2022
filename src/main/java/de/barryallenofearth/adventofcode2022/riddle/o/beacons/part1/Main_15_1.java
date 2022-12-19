package de.barryallenofearth.adventofcode2022.riddle.o.beacons.part1;

import de.barryallenofearth.adventofcode2022.riddle.o.beacons.common.FindPositionsWithNoBeacons;
import de.barryallenofearth.adventofcode2022.riddle.o.beacons.common.SensorBeaconReader;
import de.barryallenofearth.adventofcode2022.riddle.o.beacons.model.SensorClosestBeacon;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main_15_1 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		final List<SensorClosestBeacon> sensorBeaconReaders = SensorBeaconReader.readSensorBeacons();
		final int occupiedPlacesInLine = FindPositionsWithNoBeacons.findOccupiedPlacesInLine(2_000_000, sensorBeaconReaders);
		System.out.println(occupiedPlacesInLine + " fields cannot contain a beacon");
	}
}
