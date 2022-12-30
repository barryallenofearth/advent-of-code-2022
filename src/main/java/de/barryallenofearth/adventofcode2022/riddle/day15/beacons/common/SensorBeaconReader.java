package de.barryallenofearth.adventofcode2022.riddle.day15.beacons.common;

import de.barryallenofearth.adventofcode2022.riddle.day15.beacons.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.day15.beacons.model.SensorClosestBeacon;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SensorBeaconReader {
	public static final Pattern SENSOR_PATTERN = Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");

	public static List<SensorClosestBeacon> readSensorBeacons() {
		List<SensorClosestBeacon> sensorBeaconReaders = new ArrayList<>();
		for (String line : RiddleFileReader.readAllLines("riddle-15.txt")) {
			final Matcher matcher = SENSOR_PATTERN.matcher(line);
			if (matcher.matches()) {
				final Coordinates sensor = new Coordinates(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
				final Coordinates beacon = new Coordinates(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
				sensorBeaconReaders.add(new SensorClosestBeacon(sensor, beacon));
			} else {
				System.out.println("beacon could not be read:" + line);
			}
		}
		return sensorBeaconReaders;
	}
}
