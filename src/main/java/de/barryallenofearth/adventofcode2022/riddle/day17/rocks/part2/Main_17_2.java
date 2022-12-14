package de.barryallenofearth.adventofcode2022.riddle.day17.rocks.part2;

import de.barryallenofearth.adventofcode2022.riddle.day17.rocks.common.model.Cave;
import de.barryallenofearth.adventofcode2022.riddle.day17.rocks.common.util.HandleFallingRocks;

public class Main_17_2 {

    public static void main(String[] args) {

        final Cave cave = new Cave();
        HandleFallingRocks.handleFallingRocks(cave,1_000_000_000_000L);

        // 1514369501475 is not correct
        // 1514369501465 is not correct
        // 1514369501458 is not correct
        System.out.println("The maximum height reached in the cave is: " + (cave.getCurrentRockHeight()+1));
    }


}
