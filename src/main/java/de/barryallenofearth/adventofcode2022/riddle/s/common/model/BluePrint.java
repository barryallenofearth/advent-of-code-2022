package de.barryallenofearth.adventofcode2022.riddle.s.common.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BluePrint {

    private final ItemsConsumed oreRobot;

    private final ItemsConsumed clayRobot;

    private final ItemsConsumed obsidianRobot;

    private final ItemsConsumed geodeRobot;

    @Data
    @RequiredArgsConstructor
    public static class ItemsConsumed {
        private final int ore;

        private final int clay;

        private final int obsidian;

    }
}
