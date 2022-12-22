package de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.util;

import de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.model.Cave;
import de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.model.Rock;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

public class HandleFallingRocks {

    public static void handleFallingRocks(Cave cave, long numberOfRocks) {
        final String gasStreams = RiddleFileReader.readAllLines("riddle-17.txt").get(0);
        long lastMovementIndex = 0;
        for (long rockCount = 0; rockCount < numberOfRocks; rockCount++) {
            if (rockCount % 10_000 == 0) {
                System.out.println(rockCount + ": " + ((double) rockCount) / numberOfRocks * 100. + "% processed");
            }
            lastMovementIndex = RockMover.handleNewRock(rockCount, lastMovementIndex, cave, gasStreams);
        }
        printCave(cave);
        System.out.println();


    }

    public static void printCave(Cave cave) {
        for (long y = cave.getCurrentRockHeight() + 5; y >= 0; y--) {
            for (int x = Cave.X_LEFT_BORDER; x <= Cave.X_RIGHT_BORDER; x++) {
                final Coordinates currentCoordinate = new Coordinates(x, y);
                if (x == Cave.X_LEFT_BORDER || x == Cave.X_RIGHT_BORDER) {
                    System.out.print("|");
                } else if (cave.getOccupiedFields().contains(currentCoordinate)) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println(y);
        }
    }

    public static void printCave(Cave cave, Rock rock) {
        for (long y = cave.getCurrentRockHeight() + 5; y >= 0; y--) {
            for (int x = Cave.X_LEFT_BORDER; x <= Cave.X_RIGHT_BORDER; x++) {
                final Coordinates currentCoordinate = new Coordinates(x, y);
                if (x == Cave.X_LEFT_BORDER || x == Cave.X_RIGHT_BORDER) {
                    System.out.print("|");
                } else if (cave.getOccupiedFields().contains(currentCoordinate)) {
                    System.out.print("#");
                } else if (rock.getComponents().contains(currentCoordinate)) {
                    System.out.print("@");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println(y);
        }
    }
}
