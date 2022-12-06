package de.barryallenofearth.adventofcode2022.riddle.f.signals.common.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StartSignalFinder {

    public static List<Integer> findNumberOfReadSymbolToEndOfStartSequence(List<String> signals, int numberOfDistinctSymbols) {
        final List<Integer> numberOfReadSymbolsToEndOfSequence = new ArrayList<>();
        for (String signal : signals) {
            Set<Character> currentSequence = new HashSet<>();
            int index = 0;
            while (currentSequence.size() < numberOfDistinctSymbols || index == signal.length()) {
                currentSequence.clear();
                for (int count = 0; count < numberOfDistinctSymbols; count++) {
                    currentSequence.add(signal.charAt(index + count));
                }
                index++;
            }
            numberOfReadSymbolsToEndOfSequence.add(index + numberOfDistinctSymbols - 1);
        }
        return numberOfReadSymbolsToEndOfSequence;
    }
}
