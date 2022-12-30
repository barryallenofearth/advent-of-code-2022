package de.barryallenofearth.adventofcode2022.riddle.day11.monkey.part1;

import de.barryallenofearth.adventofcode2022.riddle.day11.monkey.part1.common.HandleMonkeyAction;
import de.barryallenofearth.adventofcode2022.riddle.day11.monkey.part1.common.ReadInitialMonkeyList;
import de.barryallenofearth.adventofcode2022.riddle.day11.monkey.part1.model.Monkey;

import java.util.Comparator;
import java.util.List;

public class Main_11_1 {
    public static void main(String[] args) {
        final List<Monkey> monkeys = ReadInitialMonkeyList.readMonkeys();
        System.out.println("Total items on the move: " + monkeys.stream().mapToInt((Monkey monkey) -> monkey.getItems().size()).sum());
        for (int round = 0; round < 20; round++) {
            for (int index = 0; index < monkeys.size(); index++) {
                HandleMonkeyAction.handleInspection(monkeys, index);
            }
        }
        monkeys.sort(Comparator.comparingInt(Monkey::getItemsInspected).reversed());

        for (Monkey monkey : monkeys) {
            System.out.println("Monkey " + monkey.getMonkeyIndex() + ": " + monkey.getItemsInspected());
        }
        System.out.println("Total items inspected: " + monkeys.stream().mapToInt(Monkey::getItemsInspected).sum());

        System.out.println("monkey business: " + monkeys.get(0).getItemsInspected() * monkeys.get(1).getItemsInspected());
    }
}
