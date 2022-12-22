package de.barryallenofearth.adventofcode2022.riddle.q.rocks.part1;

import de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.model.MoveType;
import de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.model.RockType;
import de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.model.Shape;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main_17_1 {

    public static void main(String[] args) throws IOException, URISyntaxException {


        for (MoveType moveType : MoveType.values()) {
            final Shape cross = new Shape(new Coordinates(1, 1), RockType.CROSS);
            cross.move(moveType);
            printShape(cross);
            System.out.println();
            cross.reverse(moveType);
            printShape(cross);
            System.out.println();
        }

    }

    private static void printShape(Shape cross) {
        for (int y = 6; y >= 0; y--) {
            for (int x = 0; x < 7; x++) {
                final Coordinates currentCoordinate = new Coordinates(x, y);
                if (cross.getComponents().contains(currentCoordinate)) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println(y);
        }
    }
}
