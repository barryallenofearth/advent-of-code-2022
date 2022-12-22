package de.barryallenofearth.adventofcode2022.riddle.c.packingorder.part2;

import de.barryallenofearth.adventofcode2022.riddle.c.packingorder.model.Rucksack;
import de.barryallenofearth.adventofcode2022.riddle.c.packingorder.part2.util.Badgefinder;
import de.barryallenofearth.adventofcode2022.riddle.c.packingorder.util.PriorityCalculator;
import de.barryallenofearth.adventofcode2022.riddle.c.packingorder.util.ReadRucksackContent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		final List<Rucksack> rucksacks = ReadRucksackContent.readRucksackList("riddle-3.txt");
		final List<String> badges = Badgefinder.findBadges(rucksacks);
		final int totalPriority = PriorityCalculator.calculateTotalPriority(badges);
		System.out.println("The total priority of all badges is " + totalPriority);
	}
}
