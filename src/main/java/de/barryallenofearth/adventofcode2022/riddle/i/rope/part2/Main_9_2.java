package de.barryallenofearth.adventofcode2022.riddle.i.rope.part2;

import de.barryallenofearth.adventofcode2022.riddle.i.rope.model.Coordinates;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class Main_9_2 {
    public static void main(String[] args) {
        final Set<Coordinates> positionsTouchedByTail = RopeMovement.findPositionsTouchedByTail();

        System.out.println(positionsTouchedByTail.size() + " positions were touched by the ropes tail");
    }
}
