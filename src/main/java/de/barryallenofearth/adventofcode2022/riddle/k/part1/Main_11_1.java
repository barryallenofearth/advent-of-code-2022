package de.barryallenofearth.adventofcode2022.riddle.k.part1;

import de.barryallenofearth.adventofcode2022.riddle.k.common.HandleMonkeyAction;
import de.barryallenofearth.adventofcode2022.riddle.k.common.ReadInitialMonkeyList;
import de.barryallenofearth.adventofcode2022.riddle.k.model.Monkey;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;

public class Main_11_1 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        final List<Monkey> monkeys = ReadInitialMonkeyList.readMonkeys();
        for (int round = 0; round < 20; round++) {
            for (int index = 0; index < monkeys.size(); index++) {
                HandleMonkeyAction.handleInspection(monkeys, index);
            }
        }
        monkeys.sort(Comparator.comparingInt(Monkey::getItemsInspected).reversed());

        System.out.println("Monkey " + monkeys.get(0).getMonkeyIndex() + ": " + monkeys.get(0).getItemsInspected());
        System.out.println("Monkey " + monkeys.get(1).getMonkeyIndex() + ": " + monkeys.get(1).getItemsInspected());
        System.out.println("monkey business: " + monkeys.get(0).getItemsInspected() * monkeys.get(1).getItemsInspected());
    }
}
