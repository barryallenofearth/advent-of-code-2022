package de.barryallenofearth.adventofcode2022.riddle.o.beacons.model;

import lombok.Data;

@Data
public class SensorClosestBeacon {
	public SensorClosestBeacon(Coordinates sensor, Coordinates beacon) {
		this.sensor = sensor;
		this.beacon = beacon;

		manhattenDistance = Math.abs(sensor.getX() - beacon.getX()) + Math.abs(sensor.getY() - beacon.getY());
	}

	private final Coordinates sensor;

	private final Coordinates beacon;

	private final int manhattenDistance;
}
