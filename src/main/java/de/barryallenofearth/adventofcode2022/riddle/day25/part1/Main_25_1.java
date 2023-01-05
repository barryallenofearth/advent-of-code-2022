package de.barryallenofearth.adventofcode2022.riddle.day25.part1;

import de.barryallenofearth.adventofcode2022.riddle.day25.common.util.SnafuConverter;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.util.List;

public class Main_25_1 {
	public static void main(String[] args) {
		final List<String> strings = RiddleFileReader.readAllLines("riddle-25.txt");
		final SnafuConverter snafuConverter = new SnafuConverter();
		final List<Long> integers = snafuConverter.convertToDecimal(strings);

		final long sum = integers.stream().mapToLong(Long::longValue).sum();
		System.out.println(sum + " is the sum of the required fuel.");

		System.out.println(snafuConverter.convertToSnafuNumber(sum) + " is the sum as a snafu number.");

	}
}
