package de.barryallenofearth.adventofcode2022.riddle.g.files.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Directory {
    private final String name;

    private final List<Directory> subDirectories = new ArrayList<>();

    private final List<File> files = new ArrayList<>();
}
