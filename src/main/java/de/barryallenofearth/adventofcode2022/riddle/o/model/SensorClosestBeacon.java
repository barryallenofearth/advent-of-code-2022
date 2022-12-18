package de.barryallenofearth.adventofcode2022.riddle.o.model;

import lombok.Data;

@Data
public class SensorClosestBeacon {
	public SensorClosestBeacon(Coordinates sensor, Coordinates beacon) {
		this.sensor = sensor;
		this.beacon = beacon;

		manhattenDistance = Math.abs(sensor.getRow() - beacon.getRow()) + Math.abs(sensor.getColumn() - beacon.getColumn());
	}

	private final Coordinates sensor;

	private final Coordinates beacon;

	private final int manhattenDistance;
}
