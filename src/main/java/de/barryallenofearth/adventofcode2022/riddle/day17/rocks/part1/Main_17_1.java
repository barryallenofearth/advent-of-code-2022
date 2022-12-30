package de.barryallenofearth.adventofcode2022.riddle.day17.rocks.part1;

import de.barryallenofearth.adventofcode2022.riddle.day17.rocks.common.model.Cave;
import de.barryallenofearth.adventofcode2022.riddle.day17.rocks.common.util.HandleFallingRocks;

public class Main_17_1 {

    public static void main(String[] args) {

        final Cave cave = new Cave();
        HandleFallingRocks.handleFallingRocks(cave, 2022);

        System.out.println("The maximum height reached in the cave is: " + (cave.getCurrentRockHeight() + 1));
    }


}
