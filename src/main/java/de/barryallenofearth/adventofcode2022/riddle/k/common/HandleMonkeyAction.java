package de.barryallenofearth.adventofcode2022.riddle.k.common;

import de.barryallenofearth.adventofcode2022.riddle.k.model.Item;
import de.barryallenofearth.adventofcode2022.riddle.k.model.Monkey;

import java.util.List;

public class HandleMonkeyAction {

	public static void handleInspection(List<Monkey> monkeys, int monkeyIndex) {
		final Monkey monkey = monkeys.get(monkeyIndex);
		while (monkey.getItems().size() > 0) {
			final Item item = monkey.getItems().poll();
			monkey.getOperation().accept(item);
			monkey.setItemsInspected(monkey.getItemsInspected() + 1);

			item.setWorryLevel((long) ((double) item.getWorryLevel() / 3));
			final int nextMonkey;
			if (item.getWorryLevel() % monkey.getTestDivisor() == 0) {
				nextMonkey = monkey.getTestTrueMonkeyIndex();
			} else {
				nextMonkey = monkey.getTestFalseMonkeyIndex();
			}
			monkeys.get(nextMonkey).getItems().add(item);
		}
	}
}
