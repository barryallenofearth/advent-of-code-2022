package de.barryallenofearth.adventofcode2022.riddle.day24.blizzard.common.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Coordinates {

	private int x;

	private int y;
}
