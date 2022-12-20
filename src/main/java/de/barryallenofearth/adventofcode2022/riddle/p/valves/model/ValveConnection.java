package de.barryallenofearth.adventofcode2022.riddle.p.valves.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ValveConnection {

	private final String startingValveKey;

	private final String stopValveKey;

}
