package de.barryallenofearth.adventofcode2022.riddle.i.rope.part2;

import de.barryallenofearth.adventofcode2022.riddle.i.rope.common.FollowHead;
import de.barryallenofearth.adventofcode2022.riddle.i.rope.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.i.rope.model.StepType;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RopeMovement {

    public static Set<Coordinates> findPositionsTouchedByTail() throws IOException, URISyntaxException {
        final Set<Coordinates> coordinatesTouchedByTail = new HashSet<>();
        final List<String> strings = RiddleFileReader.readAllLines("riddle-9.txt");

        List<Coordinates> ropeKnots = new ArrayList<>();
        for (int knot = 0; knot < 10; knot++) {
            ropeKnots.add(new Coordinates(0, 0));
        }

        for (String string : strings) {
            final String[] instructionArray = string.split(" ");
            final StepType stepType = StepType.getByKey(instructionArray[0]);
            final int numberOfSteps = Integer.parseInt(instructionArray[1]);
            for (int step = 0; step < numberOfSteps; step++) {
                stepType.getMoveRopeEnd().accept(ropeKnots.get(0));
                for (int knot = 0; knot < 9; knot++) {
                    FollowHead.updateTailCoordinates(ropeKnots.get(knot), ropeKnots.get(knot + 1));
                }
                coordinatesTouchedByTail.add(new Coordinates(ropeKnots.get(9).getRow(), ropeKnots.get(9).getColumn()));
            }
        }
        return coordinatesTouchedByTail;
    }
}
