package de.barryallenofearth.adventofcode2022.riddle.o.common;

import de.barryallenofearth.adventofcode2022.riddle.o.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.o.model.SensorClosestBeacon;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SensorBeaconReader {
	public static final Pattern SENSOR_PATTERN = Pattern.compile("Sensor at x=(\\d+), y=(\\d+): closest beacon is at x=(\\d+), y=(\\d+)");

	public static List<SensorClosestBeacon> readSensorBeacons() throws IOException, URISyntaxException {
		List<SensorClosestBeacon> sensorBeaconReaders = new ArrayList<>();
		for (String line : RiddleFileReader.readAllLines("riddle-15.txt")) {
			final Matcher matcher = SENSOR_PATTERN.matcher(line);
			if (matcher.matches()) {
				sensorBeaconReaders.add(new SensorClosestBeacon(new Coordinates(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))),
						new Coordinates(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)))));
			}
		}
		return sensorBeaconReaders;
	}
}
