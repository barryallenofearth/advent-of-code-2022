package de.barryallenofearth.adventofcode2022.riddle.t.decryption.part1;

import java.util.List;

public class SumOfRequestedNumbers {
	public static int sumUp(List<Integer> numbers) {
		final int indexOf = numbers.indexOf(0);
		int number1000 = numbers.get((indexOf + 1000) % numbers.size());
		int number2000 = numbers.get((indexOf + 2000) % numbers.size());
		int number3000 = numbers.get((indexOf + 3000) % numbers.size());

		System.out.println(number1000);
		System.out.println(number2000);
		System.out.println(number3000);

		return number1000 + number2000 + number3000;
	}
}
