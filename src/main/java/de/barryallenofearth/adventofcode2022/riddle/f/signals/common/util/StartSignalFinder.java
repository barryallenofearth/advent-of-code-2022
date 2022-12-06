package de.barryallenofearth.adventofcode2022.riddle.f.signals.common.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StartSignalFinder {

    public static List<Integer> findNumberOfReadSymbolToEndOfStartSequence(List<String> signals) {
        final List<Integer> numberOfReadSymbolsToEndOfSequence = new ArrayList<>();
        for (String signal : signals) {
            Set<Character> currentSequence = new HashSet<>();
            int index = 0;
            while (currentSequence.size() < 4 || index == signal.length()) {
                currentSequence.clear();
                currentSequence.add(signal.charAt(index));
                currentSequence.add(signal.charAt(index + 1));
                currentSequence.add(signal.charAt(index + 2));
                currentSequence.add(signal.charAt(index + 3));
                index++;
            }
            numberOfReadSymbolsToEndOfSequence.add(index + 3);
        }
        return numberOfReadSymbolsToEndOfSequence;
    }
}
