package de.barryallenofearth.adventofcode2022.riddle.m.part2;

import de.barryallenofearth.adventofcode2022.riddle.m.common.SignalParser;
import de.barryallenofearth.adventofcode2022.riddle.m.model.SignalComponent;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ReadSignals {

	public static List<SignalComponent> readSignals() throws IOException, URISyntaxException {
		final List<String> strings = RiddleFileReader.readAllLines("riddle-13.txt");
		final List<SignalComponent> allSignals = new ArrayList<>();
		final List<SignalComponent> allSubSignals = new ArrayList<>();
		for (String string : strings) {
			if (string.isBlank()) {
				continue;
			}
			final SignalComponent signalComponent = SignalParser.parseSignal(string, allSubSignals);
			signalComponent.setOriginalString(string);
			allSignals.add(signalComponent);
		}
		return allSignals;
	}
}
