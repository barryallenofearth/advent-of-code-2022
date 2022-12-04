package de.barryallenofearth.adventofcode2022.riddle.c.packingorder.part2.util;

import de.barryallenofearth.adventofcode2022.riddle.c.packingorder.model.Rucksack;

import java.util.*;

public class Badgefinder {
	public static List<String> findBadges(List<Rucksack> allRucksacks) {
		final List<String> badges = new ArrayList<>();

		for (int index = 0; index < allRucksacks.size(); index = index + 3) {

			final Rucksack rucksack1 = allRucksacks.get(index);
			final Rucksack rucksack2 = allRucksacks.get(index + 1);
			final Rucksack rucksack3 = allRucksacks.get(index + 2);

			badges.add(findGroupBadge(rucksack1, rucksack2, rucksack3));

		}
		if (badges.size() != allRucksacks.size() / 3) {
			throw new IllegalStateException("The number of badges found (" + badges.size() + ") is not equal to the number of expected groups of elves (" + allRucksacks.size() / 3);
		}

		return badges;
	}

	private static String findGroupBadge(Rucksack rucksack1, Rucksack rucksack2, Rucksack rucksack3) {
		Set<String> items1 = getDistinctItems(rucksack1);
		Set<String> items2 = getDistinctItems(rucksack2);
		Set<String> items3 = getDistinctItems(rucksack3);

		for (String item : items1) {
			if (items2.contains(item) && items3.contains(item)) {
				return item;
			}
		}

		throw new IllegalStateException("No common item found in these three backpacks:" + rucksack1 + "\n" + rucksack2 + "\n" + rucksack3);
	}

	private static Set<String> getDistinctItems(Rucksack rucksack) {
		Set<String> items = new TreeSet<>(rucksack.getCompartment1());
		items.addAll(rucksack.getCompartment2());
		return items;
	}
}
