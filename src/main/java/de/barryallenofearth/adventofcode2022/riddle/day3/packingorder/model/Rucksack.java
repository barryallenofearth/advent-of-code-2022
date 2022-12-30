package de.barryallenofearth.adventofcode2022.riddle.day3.packingorder.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Rucksack {
	private final List<String> compartment1 = new ArrayList<>();

	private final List<String> compartment2 = new ArrayList<>();
}
