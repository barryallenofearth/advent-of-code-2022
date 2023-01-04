package de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Valley {

	private Coordinates entry;

	private Coordinates exit;

	private final List<Coordinates> fields = new ArrayList<>();
}
