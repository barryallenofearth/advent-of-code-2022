package de.barryallenofearth.adventofcode2022.riddle.s.common.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class RobotsAndFactory {

    private final Factory factory;

    private final List<Robot> oreRobots = new ArrayList<>() {{
        add(new Robot(Factory::addOre));
    }};

    private final List<Robot> clayRobots = new ArrayList<>();

    private final List<Robot> obsidianRobots = new ArrayList<>();

    private final List<Robot> geodeRobots = new ArrayList<>();


}
