package de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.util;

import de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.model.*;

public class RockMover {
    public static long handleNewRock(long rockIndex, long lastMovementIndex, Cave cave, String gasStreams) {
        long startX = Cave.X_LEFT_BORDER + 3;
        long startY = cave.getCurrentRockHeight() + 4;
        final RockType rockType = RockType.values()[(int) (rockIndex % RockType.values().length)];


        final Rock rock = new Rock(new Coordinates(startX, startY), rockType);
        MoveType moveType = null;
        do {

            if (lastMovementIndex % 2 == 0) {
                final int ventIndex = ((int) (lastMovementIndex / 2)) % gasStreams.length();
                moveType = MoveType.getByKey(gasStreams.charAt(ventIndex));
            } else {
                moveType = MoveType.DOWN;
            }
            lastMovementIndex++;

            rock.move(moveType);
            if ((moveType == MoveType.LEFT || moveType == MoveType.RIGHT) && isRockFallingInBorderCollision(rock, cave)) {
                rock.reverse(moveType);
            }
//            HandleFallingRocks.printCave(cave, rock);
//            System.out.println(moveType);
//            System.out.println();
        } while (moveType != MoveType.DOWN || isRockFalling(rock, cave));
        rock.reverse(moveType);
        cave.addRock(rock);
        if (lastMovementIndex == -1) {
            lastMovementIndex = gasStreams.length() - 1;
        }

        return lastMovementIndex;

    }

    public static boolean isRockFallingInBorderCollision(Rock rock, Cave cave) {
        if (rock.getComponents().stream().anyMatch(coordinates -> coordinates.getX() <= Cave.X_LEFT_BORDER)) {
            return true;
        } else if (rock.getComponents().stream().anyMatch(coordinates -> coordinates.getX() >= Cave.X_RIGHT_BORDER)) {
            return true;
        }
        return rock.getComponents().stream().anyMatch(coordinates -> cave.getOccupiedFields().contains(coordinates));
    }

    private static boolean isRockFalling(Rock rock, Cave cave) {
        if (rock.getComponents().stream().anyMatch(coordinates -> coordinates.getY() < 0)) {
            return false;
        }
        return rock.getComponents().stream().noneMatch(coordinates -> cave.getOccupiedFields().contains(coordinates));
    }
}
