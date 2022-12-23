package de.barryallenofearth.adventofcode2022.riddle.s.common.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@Data
@RequiredArgsConstructor
public class Robot {

    private final Consumer<Factory> addToFactory;
}
