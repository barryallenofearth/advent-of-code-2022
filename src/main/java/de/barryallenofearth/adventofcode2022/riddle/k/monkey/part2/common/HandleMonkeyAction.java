package de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.common;

import de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.model.Item;
import de.barryallenofearth.adventofcode2022.riddle.k.monkey.part2.model.Monkey;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class HandleMonkeyAction {

    public static void handleInspection(List<Monkey> monkeys, int monkeyIndex) {
        final Monkey monkey = monkeys.get(monkeyIndex);
        while (monkey.getItems().size() > 0) {
            final Item item = monkey.getItems().poll();
            LocalDateTime previousTime = LocalDateTime.now();
            monkey.getOperation().accept(item);
            Duration between = Duration.between(previousTime, LocalDateTime.now());
            System.out.println(between.getNano() + "ns inspection duration");

            monkey.setItemsInspected(monkey.getItemsInspected() + 1);

            final int nextMonkey;
            previousTime = LocalDateTime.now();
            if (item.getWorryLevel().remainder(BigInteger.valueOf(monkey.getTestDivisor())).equals(BigInteger.ZERO)) {
                nextMonkey = monkey.getTestTrueMonkeyIndex();
            } else {
                nextMonkey = monkey.getTestFalseMonkeyIndex();
            }
            between = Duration.between(previousTime, LocalDateTime.now());
            System.out.println(between.getNano() + "ns modulo duration");
            monkeys.get(nextMonkey).getItems().add(item);
        }
    }
}
