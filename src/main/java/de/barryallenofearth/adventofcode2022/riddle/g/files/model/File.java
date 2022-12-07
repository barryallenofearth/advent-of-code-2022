package de.barryallenofearth.adventofcode2022.riddle.g.files.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class File {

    private final String name;

    private final int size;
}
