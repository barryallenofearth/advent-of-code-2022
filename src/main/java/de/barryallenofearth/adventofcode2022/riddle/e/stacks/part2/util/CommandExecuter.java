package de.barryallenofearth.adventofcode2022.riddle.e.stacks.part2.util;

import de.barryallenofearth.adventofcode2022.riddle.e.stacks.common.model.CommandModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CommandExecuter {

	public static void executeCommands(List<Stack<String>> stacks, List<CommandModel> commandModels) {
		for (CommandModel commandModel : commandModels) {
			final List<String> currentlyMovedItems = new ArrayList<>();
			for (int count = 0; count < commandModel.getNumberOfCrates(); count++) {
				currentlyMovedItems.add(stacks.get(commandModel.getStartingStackIndex()).pop());
			}
			for (int index = commandModel.getNumberOfCrates() - 1; index >= 0; index--) {
				stacks.get(commandModel.getEndingStackIndex()).push(currentlyMovedItems.get(index));
			}
		}
	}
}
