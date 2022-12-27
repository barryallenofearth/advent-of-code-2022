package de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2;

import de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.common.HandleMonkeyAction;
import de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.common.ReadInitialMonkeyList;
import de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.model.Monkey;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main_11_2 {
	public static void main(String[] args) {
		final List<Monkey> monkeys = ReadInitialMonkeyList.readMonkeys();
		System.out.println("Total items on the move: " + monkeys.stream().mapToInt((Monkey monkey) -> monkey.getItems().size()).sum());
		for (int round = 0; round < 10_000; round++) {
			for (int index = 0; index < monkeys.size(); index++) {
				HandleMonkeyAction.handleInspection(monkeys, index);
			}
			if (round % 1_000 == 0) {
				printCurrentState(monkeys);
			}
		}
		printCurrentState(monkeys);
	}

	private static void printCurrentState(List<Monkey> monkeys) {
		final List<Monkey> sortedMonkeys = monkeys.stream()
				.sorted(Comparator.comparingInt(Monkey::getItemsInspected).reversed())
				.collect(Collectors.toList());
		for (Monkey monkey : sortedMonkeys) {
			System.out.println("Monkey " + monkey.getMonkeyIndex() + ": " + monkey.getItemsInspected());
		}
		System.out.println("Total items inspected: " + sortedMonkeys.stream().mapToInt(Monkey::getItemsInspected).sum());

		System.out.println("monkey business: " + BigInteger.valueOf(sortedMonkeys.get(0).getItemsInspected()).multiply(BigInteger.valueOf(sortedMonkeys.get(1).getItemsInspected())));
	}
}
