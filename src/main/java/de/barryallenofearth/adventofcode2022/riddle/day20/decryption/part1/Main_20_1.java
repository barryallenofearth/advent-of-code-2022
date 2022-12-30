package de.barryallenofearth.adventofcode2022.riddle.day20.decryption.part1;

import de.barryallenofearth.adventofcode2022.riddle.day20.decryption.common.NumberSequenceReader;
import de.barryallenofearth.adventofcode2022.riddle.day20.decryption.common.NumberSorter;
import de.barryallenofearth.adventofcode2022.riddle.day20.decryption.common.model.EntryWithId;

import java.util.List;

public class Main_20_1 {

	public static void main(String[] args) {
		final List<EntryWithId> numbers = NumberSequenceReader.read();
		final List<Integer> integers = NumberSorter.sortNumbers(numbers);
		final int sum = SumOfRequestedNumbers.sumUp(integers);
		System.out.println(sum + " is the requested sum");

	}
}
