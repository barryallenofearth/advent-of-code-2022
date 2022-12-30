package de.barryallenofearth.adventofcode2022.riddle.day1.foodorder;

import de.barryallenofearth.adventofcode2022.riddle.day1.foodorder.model.Elf;
import de.barryallenofearth.adventofcode2022.riddle.day1.foodorder.service.ElvesWithFoodReader;

import java.util.List;


/**
 * Answer to riddle:
 * https://adventofcode.com/2022/day/1
 *
 */
public class Main {

	public static void main(String[] args) {
		ElvesWithFoodReader elvesWithFoodReader = new ElvesWithFoodReader();
		final List<Elf> elves = elvesWithFoodReader.readFoodCarriedByElfes("riddle-1.txt");

		final Elf elfWithMostFood = elves.get(0);
		System.out.println("The elf carrying the largest amount of calories is '" + elfWithMostFood.getName() + "'.\n"
				+ "The total amount of carried calories is '" + elvesWithFoodReader.sumUpCalories(elfWithMostFood) + "' calories.");
	}
}
