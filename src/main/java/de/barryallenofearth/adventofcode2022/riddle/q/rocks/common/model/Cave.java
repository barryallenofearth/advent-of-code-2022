package de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class Cave {

    public static final int X_LEFT_BORDER = -1;

    public static final int X_RIGHT_BORDER = 7;

    private long currentRockHeight = -1;

    private Set<Coordinates> occupiedFields = new HashSet<>();

    public void addRock(Rock shape) {
        occupiedFields.addAll(shape.getComponents());

        final long[] minHeight = {Integer.MAX_VALUE};
        for (long x = X_LEFT_BORDER + 1; x < X_RIGHT_BORDER - 1; x++) {
            final long highestHeightInColumn = getHighestHeightInColumn(x);
            if (highestHeightInColumn < minHeight[0]) {
                minHeight[0] = highestHeightInColumn;
            }
        }

        occupiedFields = occupiedFields.stream()
                .filter(coordinates -> coordinates.getY() >= minHeight[0])
                .collect(Collectors.toSet());
        determineMaxHeight();
    }

    private long getHighestHeightInColumn(long x) {
        return occupiedFields.stream().filter(coordinates -> coordinates.getX() == x).mapToLong(Coordinates::getY).max().orElse(-1);
    }

    private void determineMaxHeight() {
        currentRockHeight = 0;
        for (Coordinates occupiedField : occupiedFields) {
            if (occupiedField.getY() > currentRockHeight) {
                currentRockHeight = occupiedField.getY();
            }
        }

    }
}
