package de.barryallenofearth.adventofcode2022.riddle.i.rope.part1;

import de.barryallenofearth.adventofcode2022.riddle.i.rope.common.FollowHead;
import de.barryallenofearth.adventofcode2022.riddle.i.rope.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.i.rope.model.StepType;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HeadMovement {

    public static Set<Coordinates> findPositionsTouchedByTail() {

        final Set<Coordinates> coordinatesTouchedByTail = new HashSet<>();
        final List<String> strings = RiddleFileReader.readAllLines("riddle-9.txt");
        final Coordinates head = new Coordinates(0, 0);
        final Coordinates tail = new Coordinates(0, 0);

        for (String string : strings) {
            final String[] instructionArray = string.split(" ");
            final StepType stepType = StepType.getByKey(instructionArray[0]);
            final int numberOfSteps = Integer.parseInt(instructionArray[1]);
            for (int step = 0; step < numberOfSteps; step++) {
                stepType.getMoveRopeEnd().accept(head);
                FollowHead.updateTailCoordinates(head, tail);
                coordinatesTouchedByTail.add(new Coordinates(tail.getRow(), tail.getColumn()));
            }
        }
        return coordinatesTouchedByTail;
    }
}
