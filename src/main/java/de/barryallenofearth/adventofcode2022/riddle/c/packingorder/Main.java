package de.barryallenofearth.adventofcode2022.riddle.c.packingorder;

import de.barryallenofearth.adventofcode2022.riddle.c.packingorder.model.Rucksack;
import de.barryallenofearth.adventofcode2022.riddle.c.packingorder.util.CommonItemFinder;
import de.barryallenofearth.adventofcode2022.riddle.c.packingorder.util.PriorityCalculator;
import de.barryallenofearth.adventofcode2022.riddle.c.packingorder.util.ReadRucksackContent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException, URISyntaxException {
		final List<Rucksack> rucksacks = ReadRucksackContent.readRucksackList("riddle-3.txt");
		final List<String> commonItems = CommonItemFinder.findCommonItems(rucksacks);
		final int totalPriority = PriorityCalculator.calculateTotalPriority(commonItems);

		System.out.println("The total priority is " + totalPriority);

	}
}
