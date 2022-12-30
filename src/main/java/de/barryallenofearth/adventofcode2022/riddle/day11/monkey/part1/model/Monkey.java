package de.barryallenofearth.adventofcode2022.riddle.day11.monkey.part1.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

@Data
@RequiredArgsConstructor
public class Monkey {
    private final int monkeyIndex;

    private final Queue<Item> items = new LinkedList<>();

    private int itemsInspected = 0;

    private int testDivisor;

    private Consumer<Item> operation;

    private int testTrueMonkeyIndex;

    private int testFalseMonkeyIndex;

}
