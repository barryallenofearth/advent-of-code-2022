package de.barryallenofearth.adventofcode2022.riddle.one.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Elf {

	private final String name;

	private final List<Food> carriedFood = new ArrayList<>();
}
