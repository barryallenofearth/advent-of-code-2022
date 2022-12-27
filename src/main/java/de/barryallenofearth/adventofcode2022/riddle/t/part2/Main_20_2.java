package de.barryallenofearth.adventofcode2022.riddle.t.part2;

import de.barryallenofearth.adventofcode2022.riddle.t.common.NumberSequenceReader;
import de.barryallenofearth.adventofcode2022.riddle.t.common.NumberSorter;
import de.barryallenofearth.adventofcode2022.riddle.t.common.model.EntryWithId;
import de.barryallenofearth.adventofcode2022.riddle.t.part1.SumOfRequestedNumbers;

import java.math.BigInteger;
import java.util.List;

public class Main_20_2 {

	public static void main(String[] args) {
		final List<EntryWithId> numbers = NumberSequenceReader.read();
		final int modularWindow = numbers.size() - 1;
		final int decryptionKey = 811589153;
		int modularDecryptionKey = decryptionKey % modularWindow;
		for (EntryWithId number : numbers) {
			number.setValue((number.getValue() % modularWindow) * modularDecryptionKey);
		}
		List<Integer> integers = null;
		for (int index = 0; index < 10; index++) {
			integers = NumberSorter.sortNumbers(numbers);
		}
		final long sum = SumOfRequestedNumbers.sumUp(integers);
		System.out.println(BigInteger.valueOf(sum).multiply(BigInteger.valueOf(decryptionKey)) + " is the requested sum");
	}
}
