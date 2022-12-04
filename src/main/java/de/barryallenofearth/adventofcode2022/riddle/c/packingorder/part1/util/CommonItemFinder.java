package de.barryallenofearth.adventofcode2022.riddle.c.packingorder.part1.util;

import de.barryallenofearth.adventofcode2022.riddle.c.packingorder.model.Rucksack;

import java.util.ArrayList;
import java.util.List;

public class CommonItemFinder {

	public static List<String> findCommonItems(List<Rucksack> rucksacks) {
		final List<String> commonItems = new ArrayList<>();
		for (Rucksack rucksack : rucksacks) {
			for (String item : rucksack.getCompartment1()) {
				if (rucksack.getCompartment2().contains(item)) {
					commonItems.add(item);
					break;
				}
			}
		}
		if (commonItems.size() != rucksacks.size()) {
			throw new IllegalStateException("The number of rucksacks (" + rucksacks.size() + ") is not equal to the number of common items (" + commonItems.size() + ")");
		}
		return commonItems;
	}
}
