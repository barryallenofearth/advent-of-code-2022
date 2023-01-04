package de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.model;

import lombok.Data;

import java.util.List;

@Data
public class InitialState {
	private final Valley valley;

	private final List<Blizzard> blizzardList;
}
