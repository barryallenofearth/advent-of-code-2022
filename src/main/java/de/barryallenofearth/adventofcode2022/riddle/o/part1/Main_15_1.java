package de.barryallenofearth.adventofcode2022.riddle.o.part1;

import de.barryallenofearth.adventofcode2022.riddle.o.common.FindPositionsWithNoBeacons;
import de.barryallenofearth.adventofcode2022.riddle.o.common.SensorBeaconReader;
import de.barryallenofearth.adventofcode2022.riddle.o.model.SensorClosestBeacon;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main_15_1 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		final List<SensorClosestBeacon> sensorBeaconReaders = SensorBeaconReader.readSensorBeacons();
		final int occupiedPlacesInLine = FindPositionsWithNoBeacons.findOccupiedPlacesInLine(2000000, sensorBeaconReaders);
		System.out.println(occupiedPlacesInLine);
	}
}
