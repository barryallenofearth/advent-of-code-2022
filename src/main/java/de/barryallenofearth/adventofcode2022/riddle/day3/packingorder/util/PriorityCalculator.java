package de.barryallenofearth.adventofcode2022.riddle.day3.packingorder.util;

import java.util.List;

public class PriorityCalculator {

	public static int calculateTotalPriority(List<String> commonItems) {
		return commonItems.stream()
				.mapToInt(PriorityCalculator::convertToPriorityNumber)
				.sum();
	}

	private static int convertToPriorityNumber(String letter) {
		int priority = 0;

		//uppercase letters start at 27 for 'A'
		if (letter.toUpperCase().equals(letter)) {
			priority += 26;
			letter = letter.toLowerCase();
		}
		char currentLetter = letter.charAt(0);
		priority += currentLetter - 'a' + 1;

		return priority;

	}
}
