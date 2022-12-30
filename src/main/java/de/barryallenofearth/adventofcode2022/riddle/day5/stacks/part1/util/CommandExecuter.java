package de.barryallenofearth.adventofcode2022.riddle.day5.stacks.part1.util;

import de.barryallenofearth.adventofcode2022.riddle.day5.stacks.common.model.CommandModel;

import java.util.List;
import java.util.Stack;

public class CommandExecuter {

	public static void executeCommands(List<Stack<String>> stacks, List<CommandModel> commandModels) {
		for (CommandModel commandModel : commandModels) {
			for (int count = 0; count < commandModel.getNumberOfCrates(); count++) {
				String currentCrate = stacks.get(commandModel.getStartingStackIndex()).pop();
				stacks.get(commandModel.getEndingStackIndex()).push(currentCrate);
			}
		}
	}
}
