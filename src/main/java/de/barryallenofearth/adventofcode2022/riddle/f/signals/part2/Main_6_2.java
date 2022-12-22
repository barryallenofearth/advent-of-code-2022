package de.barryallenofearth.adventofcode2022.riddle.f.signals.part2;

import de.barryallenofearth.adventofcode2022.riddle.f.signals.common.util.StartSignalFinder;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main_6_2 {
    public static void main(String[] args) {

        final List<String> strings = RiddleFileReader.readAllLines("riddle-6.txt");
        final List<Integer> numberOfReadSymbolToEndOfStartSequence = StartSignalFinder.findNumberOfReadSymbolToEndOfStartSequence(strings, 14);
        for (Integer integer : numberOfReadSymbolToEndOfStartSequence) {
            System.out.println(integer + " symbols need to be read to processed the currents signal first message start sequence");
        }

    }
}
