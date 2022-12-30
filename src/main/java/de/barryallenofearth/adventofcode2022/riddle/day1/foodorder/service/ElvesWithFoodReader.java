package de.barryallenofearth.adventofcode2022.riddle.day1.foodorder.service;

import de.barryallenofearth.adventofcode2022.riddle.day1.foodorder.model.Elf;
import de.barryallenofearth.adventofcode2022.riddle.day1.foodorder.model.Food;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ElvesWithFoodReader {

	public static final String BASE_ELF_NAME = "Elf ";

	/**
	 * Method providing a sorted list of elves extracted from the food list created by the elves containing the amount of calories of each item they carry.
	 * Each elf in that list is separated by a blank line
	 *
	 * @param fileName path to actual file relative to classpath
	 * @return List of elves carrying food sorted by the total amount of carried calories, starting with the elf carrying the largest amount of calories
	 */
	public List<Elf> readFoodCarriedByElfes(String fileName) {
		List<Elf> elvesWithFood = readUnsortedElfList(fileName);

		elvesWithFood.sort(Comparator.comparingInt(this::sumUpCalories).reversed());

		return elvesWithFood;
	}

	private List<Elf> readUnsortedElfList(String fileName) {
		final List<String> elfCaloriesEntries = RiddleFileReader.readAllLines(fileName);

		List<Elf> elvesWithFood = new ArrayList<>();

		//counting is only relevant for naming elves
		int currentElf = 1;
		Elf elf = new Elf(BASE_ELF_NAME + currentElf);
		elvesWithFood.add(elf);

		for (String elfCaloriesLine : elfCaloriesEntries) {

			if (elfCaloriesLine.isEmpty()) {
				elf = new Elf(BASE_ELF_NAME + ++currentElf);
				elvesWithFood.add(elf);
				continue;
			}
			final int calories = Integer.parseInt(elfCaloriesLine);
			elf.getCarriedFood().add(new Food(calories));

		}
		return elvesWithFood;
	}

	public int sumUpCalories(Elf elf) {
		return elf.getCarriedFood()
				.stream()
				.mapToInt(Food::getCalories)
				.sum();
	}
}
