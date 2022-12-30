package de.barryallenofearth.adventofcode2022.riddle.day4.zonecleaning.common.model;

import lombok.Data;

@Data
public class PairedZoneAssignment {
	private final int[] elfZones1 = new int[2];

	private final int[] elfZones2 = new int[2];
}
