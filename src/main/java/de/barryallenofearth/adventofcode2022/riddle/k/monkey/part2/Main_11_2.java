package de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2;

import de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.common.HandleMonkeyAction;
import de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.common.ReadInitialMonkeyList;
import de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.model.Monkey;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main_11_2 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        final List<Monkey> monkeys = ReadInitialMonkeyList.readMonkeys();
        System.out.println("Total items on the move: " + monkeys.stream().mapToInt((Monkey monkey) -> monkey.getItems().size()).sum());
        LocalDateTime previousTime = LocalDateTime.now();
        for (int round = 0; round < 10_000; round++) {
            for (int index = 0; index < monkeys.size(); index++) {
                HandleMonkeyAction.handleInspection(monkeys, index);
            }
            if (round % 100 == 0) {
                LocalDateTime now = LocalDateTime.now();
                final Duration between = Duration.between(previousTime, now);
                final long minutes = between.getSeconds() / 60;
                final long seconds = between.getSeconds() - minutes * 60;
                System.out.println(minutes + ":" + (seconds >= 10 ? String.valueOf(seconds) : "0" + seconds) + " current round: " + round);
                previousTime = now;
            }
            if (round % 1_000 == 0) {
                printCurrentState(monkeys);
            }
        }
        printCurrentState(monkeys);
    }

    private static void printCurrentState(List<Monkey> monkeys) {
        final List<Monkey> sortedMonkeys = monkeys.stream()
                .sorted(Comparator.comparingInt(Monkey::getItemsInspected).reversed())
                .collect(Collectors.toList());
        for (Monkey monkey : sortedMonkeys) {
            System.out.println("Monkey " + monkey.getMonkeyIndex() + ": " + monkey.getItemsInspected());
        }
        System.out.println("Total items inspected: " + sortedMonkeys.stream().mapToInt(Monkey::getItemsInspected).sum());

        System.out.println("monkey business: " + sortedMonkeys.get(0).getItemsInspected() * sortedMonkeys.get(1).getItemsInspected());
    }
}