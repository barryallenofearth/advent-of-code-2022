package de.barryallenofearth.adventofcode2022.riddle.f.signals.part1;

import de.barryallenofearth.adventofcode2022.riddle.f.signals.common.util.StartSignalFinder;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main_6_1 {
    public static void main(String[] args) throws IOException, URISyntaxException {

        final List<String> strings = RiddleFileReader.readAllLines("riddle-6.txt");
        final List<Integer> numberOfReadSymbolToEndOfStartSequence = StartSignalFinder.findNumberOfReadSymbolToEndOfStartSequence(strings, 4);
        for (Integer integer : numberOfReadSymbolToEndOfStartSequence) {
            System.out.println(integer);
        }

    }
}
