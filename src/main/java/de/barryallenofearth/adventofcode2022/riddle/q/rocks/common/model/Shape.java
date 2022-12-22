package de.barryallenofearth.adventofcode2022.riddle.q.rocks.common.model;

import lombok.Data;

import java.util.*;

@Data
public class Shape {

    private final List<Coordinates> components;

    public Shape(Coordinates startingCoordinates, RockType rockType) {
        List<Coordinates> components = new ArrayList<>();
        for (Coordinates component : rockType.getComponents()) {
            components.add(new Coordinates(component.getX() + startingCoordinates.getX(), component.getY() + startingCoordinates.getY()));
        }
        this.components = Collections.unmodifiableList(components);
    }

    public void move(MoveType moveType) {
        for (Coordinates component : components) {
            component.setX(component.getX() + moveType.getMove().getX());
            component.setY(component.getY() + moveType.getMove().getY());
        }
    }

    public void reverse(MoveType moveType) {
        for (Coordinates component : components) {
            component.setX(component.getX() - moveType.getMove().getX());
            component.setY(component.getY() - moveType.getMove().getY());
        }
    }
}
