package de.barryallenofearth.adventofcode2022.riddle.day9.rope.part1;

import de.barryallenofearth.adventofcode2022.riddle.day9.rope.model.Coordinates;

import java.util.Set;

public class Main_9_1 {
    public static void main(String[] args) {
        final Set<Coordinates> positionsTouchedByTail = HeadMovement.findPositionsTouchedByTail();

        System.out.println(positionsTouchedByTail.size() + " positions were touched by the ropes tail");
    }
}
