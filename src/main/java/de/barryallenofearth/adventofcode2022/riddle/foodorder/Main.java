package de.barryallenofearth.adventofcode2022.riddle.foodorder;

import de.barryallenofearth.adventofcode2022.riddle.foodorder.model.Elf;
import de.barryallenofearth.adventofcode2022.riddle.foodorder.service.ElvesWithFoodReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException, URISyntaxException {
		ElvesWithFoodReader elvesWithFoodReader = new ElvesWithFoodReader();
		final List<Elf> elves = elvesWithFoodReader.readFoodCarriedByElfes("/riddle-input/riddle-1.txt");

		final Elf elfWithMostFood = elves.get(0);
		System.out.println("The elf carrying the largest amount of calories is '" + elfWithMostFood.getName() + "'.\n"
				+ "The total amount of carried calories is '" + elvesWithFoodReader.sumUpCalories(elfWithMostFood) + "' calories.");
	}
}
