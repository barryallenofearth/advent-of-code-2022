package de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.part2;

import de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.common.model.Monkey;
import de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.common.util.MonkeyParser;

import java.util.List;

public class Main_21_2 {
	public static void main(String[] args) {
		final List<Monkey> monkeyList = MonkeyParser.parseMonkeys();
		final Monkey humn = WhatIsMyNumber.evaluate(monkeyList);

		//3360561285978 too high
		System.out.println(humn.getNumber() + " is the final number of 'monkey' " + humn.getName());
	}
}
