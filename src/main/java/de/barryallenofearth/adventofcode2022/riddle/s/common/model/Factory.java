package de.barryallenofearth.adventofcode2022.riddle.s.common.model;

import lombok.Data;

import java.util.Optional;

@Data
public class Factory {
    private final BluePrint bluePrint;

    private int ore = 0;

    private int clay = 0;

    private int obsidian = 0;

    private int geodes = 0;

    public void addOre() {
        ore++;
    }

    public void addClay() {
        clay++;
    }

    public void addObsidian() {
        obsidian++;
    }

    public void addGeode() {
        geodes++;
    }

    public Optional<Robot> produceOreRobot() {
        if (!testAndCosume(bluePrint.getOreRobot())) {
            return Optional.empty();
        }
        return Optional.of(new Robot(Factory::addOre));
    }

    public Optional<Robot> produceClayRobot() {
        if (!testAndCosume(bluePrint.getClayRobot())) {
            return Optional.empty();
        }
        return Optional.of(new Robot(Factory::addClay));
    }

    public Optional<Robot> produceObsidianRobot() {
        if (!testAndCosume(bluePrint.getObsidianRobot())) {
            return Optional.empty();
        }

        return Optional.of(new Robot(Factory::addObsidian));
    }

    public Optional<Robot> produceGeodeRobot() {
        if (!testAndCosume(bluePrint.getGeodeRobot())) {
            return Optional.empty();
        }
        return Optional.of(new Robot(Factory::addGeode));
    }

    private boolean testAndCosume(BluePrint.ItemsConsumed itemsConsumed) {
        if (ore - itemsConsumed.getOre() < 0 || clay - itemsConsumed.getClay() < 0 || obsidian - itemsConsumed.getObsidian() < 0) {
            return false;
        }

        ore -= itemsConsumed.getOre();
        clay -= itemsConsumed.getClay();
        obsidian -= itemsConsumed.getObsidian();

        return true;
    }


}
