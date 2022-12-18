package de.barryallenofearth.adventofcode2022.riddle.m.part1;

import de.barryallenofearth.adventofcode2022.riddle.m.common.SignalParser;
import de.barryallenofearth.adventofcode2022.riddle.m.model.SignalComponent;
import de.barryallenofearth.adventofcode2022.riddle.m.model.SignalPair;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ReadSignalPairs {

	public static List<SignalPair> signalPairList() throws IOException, URISyntaxException {
		List<SignalPair> signalPairList = new ArrayList<>();

		final List<String> strings = RiddleFileReader.readAllLines("riddle-13.txt");
		int pairIndex = 1;
		SignalPair signalPair = new SignalPair(pairIndex);
		signalPairList.add(signalPair);

		List<SignalComponent> allListSignalComponents = new ArrayList<>();
		for (String string : strings) {
			if (string.trim().isEmpty()) {
				signalPair = new SignalPair(++pairIndex);
				signalPairList.add(signalPair);
			} else if (signalPair.getFirst() == null) {
				signalPair.setOriginalFirst(string);
				signalPair.setFirst(SignalParser.parseSignal(string, allListSignalComponents));
			} else {
				signalPair.setOriginalSecond(string);
				signalPair.setSecond(SignalParser.parseSignal(string, allListSignalComponents));
			}
		}
		return signalPairList;
	}

}
