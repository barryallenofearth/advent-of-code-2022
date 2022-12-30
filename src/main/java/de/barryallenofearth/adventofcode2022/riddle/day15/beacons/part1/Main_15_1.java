package de.barryallenofearth.adventofcode2022.riddle.day15.beacons.part1;

import de.barryallenofearth.adventofcode2022.riddle.day15.beacons.common.FindPositionsWithNoBeacons;
import de.barryallenofearth.adventofcode2022.riddle.day15.beacons.common.SensorBeaconReader;
import de.barryallenofearth.adventofcode2022.riddle.day15.beacons.model.SensorClosestBeacon;

import java.util.List;

public class Main_15_1 {
	public static void main(String[] args) {
		final List<SensorClosestBeacon> sensorBeaconReaders = SensorBeaconReader.readSensorBeacons();
		final int occupiedPlacesInLine = FindPositionsWithNoBeacons.findOccupiedPlacesInLine(2_000_000, sensorBeaconReaders);
		System.out.println(occupiedPlacesInLine + " fields cannot contain a beacon");
	}
}
