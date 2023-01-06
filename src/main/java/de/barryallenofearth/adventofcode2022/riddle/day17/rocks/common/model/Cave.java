package de.barryallenofearth.adventofcode2022.riddle.day17.rocks.common.model;

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

        currentRockHeight = 0;
        for (long x = X_LEFT_BORDER ; x <= X_RIGHT_BORDER; x++) {
            final long highestHeightInColumn = getHighestHeightInColumn(x);
            if (highestHeightInColumn < minHeight[0]) {
                minHeight[0] = highestHeightInColumn;
            } else if (highestHeightInColumn > currentRockHeight) {
                currentRockHeight = highestHeightInColumn;
            }
        }

        occupiedFields = occupiedFields.stream()
                .filter(coordinates -> coordinates.getY() >= minHeight[0])
                .collect(Collectors.toSet());
    }

    private long getHighestHeightInColumn(long x) {
        return occupiedFields.stream().filter(coordinates -> coordinates.getX() == x).mapToLong(Coordinates::getY).max().orElse(-1);
    }

}
