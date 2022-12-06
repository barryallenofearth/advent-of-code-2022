package de.barryallenofearth.adventofcode2022.riddle.f.signals.common.util;

import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class SignalStreamReader {

    public static List<String> readSignal() throws IOException, URISyntaxException {
        final List<String> strings = RiddleFileReader.readAllLines("riddle-6.txt");

        return strings;
    }
}
