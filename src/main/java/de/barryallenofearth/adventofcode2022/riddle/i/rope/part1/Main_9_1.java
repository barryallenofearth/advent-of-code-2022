package de.barryallenofearth.adventofcode2022.riddle.i.rope.part1;

import de.barryallenofearth.adventofcode2022.riddle.i.rope.model.Coordinates;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class Main_9_1 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        final Set<Coordinates> positionsTouchedByTail = HeadMovement.findPositionsTouchedByTail();

        System.out.println(positionsTouchedByTail.size() + " positions were touched by the ropes tail");
    }
}
