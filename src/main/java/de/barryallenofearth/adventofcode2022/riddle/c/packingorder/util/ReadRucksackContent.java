package de.barryallenofearth.adventofcode2022.riddle.c.packingorder.util;

import de.barryallenofearth.adventofcode2022.riddle.c.packingorder.model.Rucksack;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ReadRucksackContent {
	public static List<Rucksack> readRucksackList(String fileName) throws IOException, URISyntaxException {

		List<String> rucksackEntries = RiddleFileReader.readAllLines(fileName);

		final List<Rucksack> rucksacks = new ArrayList<>();
		for (String entry : rucksackEntries) {
			final int totalNumberOfItems = entry.length();
			final String compartmentString1 = entry.substring(0, totalNumberOfItems / 2);
			final String compartmentString2 = entry.substring(totalNumberOfItems / 2);

			final Rucksack rucksack = new Rucksack();
			rucksacks.add(rucksack);

			convertStringToList(compartmentString1, rucksack.getCompartment1());
			convertStringToList(compartmentString2, rucksack.getCompartment2());

			final int size1 = rucksack.getCompartment1().size();
			final int size2 = rucksack.getCompartment2().size();
			if (size1 != size2) {
				throw new IllegalStateException("Both compartments must be of equal size at all times, but first compartment contained " + size1 + " and the second contained " + size2);
			}
			if (size1 + size2 != totalNumberOfItems) {
				throw new IllegalStateException("Not all items were registered. The first compartment contained " + size1 + " and the second contained " + size2
						+ " meaning that in total " + size1 + size2 + " items were registered, while " + totalNumberOfItems + " were required.");
			}
		}
		return rucksacks;
	}

	private static void convertStringToList(String compartmentString, List<String> compartment) {
		for (int index = 0; index < compartmentString.length(); index++) {
			compartment.add(compartmentString.substring(index, index + 1));
		}
	}

}
