package de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.part1;

import de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.common.model.Monkey;
import de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.common.util.MonkeyParser;

import java.util.List;

public class Main_21_1 {
	public static void main(String[] args) {
		final List<Monkey> monkeyList = MonkeyParser.parseMonkeys();
		final Monkey root = MonkeyEvaluator.evaluate(monkeyList);

		System.out.println(root.getNumber() + " is the final number of monkey " + root.getName());
	}
}
