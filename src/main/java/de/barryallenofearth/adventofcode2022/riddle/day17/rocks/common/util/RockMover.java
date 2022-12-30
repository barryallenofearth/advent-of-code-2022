package de.barryallenofearth.adventofcode2022.riddle.day17.rocks.common.util;

import de.barryallenofearth.adventofcode2022.riddle.day17.rocks.common.model.*;

public class RockMover {
    public static long handleNewRock(long rockIndex, long lastMovementIndex, Cave cave, String gasStreams) {
        int startX = Cave.X_LEFT_BORDER + 3;
        long startY = cave.getCurrentRockHeight() + 1;//start 3 steps lower and only perform horizontal movement since no collisions are possible
        final RockType rockType = RockType.values()[(int) (rockIndex % RockType.values().length)];

        final Rock rock = new Rock(new Coordinates(startX, startY), rockType);
        lastMovementIndex = initialHorizontalMoves(lastMovementIndex, gasStreams, rock);
        MoveType moveType = null;
        do {

            if (lastMovementIndex % 2 == 0) {
                final int ventIndex = ((int) (lastMovementIndex / 2)) % gasStreams.length();
                if (ventIndex == 0) {
                    lastMovementIndex = 0;
                }
                moveType = MoveType.getByKey(gasStreams.charAt(ventIndex));
            } else {
                moveType = MoveType.DOWN;
            }
            lastMovementIndex++;

            rock.move(moveType);
            if ((moveType == MoveType.LEFT || moveType == MoveType.RIGHT) && isCollidingHorizontally(rock, cave)) {
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

    private static long initialHorizontalMoves(long lastMovementIndex, String gasStreams, Rock rock) {
        //3 vertical steps and alternating with 3 horizontal steps and then again horizontal with collision probability
        for (int horizontalSteps = 0; horizontalSteps < 4; horizontalSteps++) {
            final int ventIndex = ((int) (lastMovementIndex / 2)) % gasStreams.length();
            if (ventIndex == 0) {
                lastMovementIndex = 0;
            }
            MoveType horizontalMove = MoveType.getByKey(gasStreams.charAt(ventIndex));
            lastMovementIndex++;
            if (horizontalSteps != 3) {
                lastMovementIndex++;
            }
            rock.move(horizontalMove);
            if (isInBorderCollision(rock)) {
                rock.reverse(horizontalMove);
            }
        }
        return lastMovementIndex;
    }

    public static boolean isCollidingHorizontally(Rock rock, Cave cave) {
        if (isInBorderCollision(rock)) {
            return true;
        }
        return rock.getComponents().stream().anyMatch(coordinates -> cave.getOccupiedFields().contains(coordinates));
    }

    private static boolean isInBorderCollision(Rock rock) {
        if (rock.getComponents().stream().anyMatch(coordinates -> coordinates.getX() <= Cave.X_LEFT_BORDER)) {
            return true;
        } else if (rock.getComponents().stream().anyMatch(coordinates -> coordinates.getX() >= Cave.X_RIGHT_BORDER)) {
            return true;
        }
        return false;
    }

    private static boolean isRockFalling(Rock rock, Cave cave) {
        if (rock.getComponents().stream().anyMatch(coordinates -> coordinates.getY() < 0)) {
            return false;
        }
        return rock.getComponents().stream().noneMatch(coordinates -> cave.getOccupiedFields().contains(coordinates));
    }
}
