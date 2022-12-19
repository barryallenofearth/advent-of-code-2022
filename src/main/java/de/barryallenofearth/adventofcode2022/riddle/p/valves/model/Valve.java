package de.barryallenofearth.adventofcode2022.riddle.p.valves.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Valve {

	private final String key;

	private final int flowRate;

	private final List<String> connectedValveKeys;

	private boolean isOpen = false;

}
