package de.barryallenofearth.adventofcode2022.riddle.e.stacks.part2;

import de.barryallenofearth.adventofcode2022.riddle.e.stacks.common.model.CommandModel;
import de.barryallenofearth.adventofcode2022.riddle.e.stacks.common.util.InstructionsReader;
import de.barryallenofearth.adventofcode2022.riddle.e.stacks.common.util.StackReader;
import de.barryallenofearth.adventofcode2022.riddle.e.stacks.part2.util.CommandExecuter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Stack;

public class Main_5_2 {
	public static void main(String[] args) {
		final List<Stack<String>> stacks = StackReader.readStacks("riddle-5.txt");
		final List<CommandModel> commandModels = InstructionsReader.readCommands("riddle-5.txt");

		CommandExecuter.executeCommands(stacks, commandModels);

		StringBuilder topItems = new StringBuilder();
		for (Stack<String> stack : stacks) {
			topItems.append(stack.peek());
		}
		System.out.println("The top items of the final configuration are " + topItems);

	}
}
