package de.barryallenofearth.adventofcode2022.riddle.day6.signals.part1;

import de.barryallenofearth.adventofcode2022.riddle.day6.signals.common.util.StartSignalFinder;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.util.List;

public class Main_6_1 {
    public static void main(String[] args) {

        final List<String> strings = RiddleFileReader.readAllLines("riddle-6.txt");
        final List<Integer> numberOfReadSymbolToEndOfStartSequence = StartSignalFinder.findNumberOfReadSymbolToEndOfStartSequence(strings, 4);
        for (Integer integer : numberOfReadSymbolToEndOfStartSequence) {
            System.out.println(integer + " symbols need to be read to processed the currents signal first sync start sequence");
        }

    }
}
