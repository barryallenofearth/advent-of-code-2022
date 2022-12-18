package de.barryallenofearth.adventofcode2022.riddle.m.common;

import de.barryallenofearth.adventofcode2022.riddle.m.model.SignalComponent;
import de.barryallenofearth.adventofcode2022.riddle.m.model.SignalPair;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
				signalPair.setFirst(parseSignal(string, allListSignalComponents));
			} else {
				signalPair.setOriginalSecond(string);
				signalPair.setSecond(parseSignal(string, allListSignalComponents));
			}
		}
		return signalPairList;
	}

	private static SignalComponent parseSignal(String string, List<SignalComponent> allListSignalComponents) {
		int indexOfLastOpeningBracket = getIndexOfLastOpeningBracket(string);
		if (indexOfLastOpeningBracket == 0) {
			return parseSimpleListSignal(string, allListSignalComponents);
		}
		final int closingBraceIndex = findClosingBraceIndex(string, indexOfLastOpeningBracket);
		final String simpleList = string.substring(indexOfLastOpeningBracket, closingBraceIndex + 1);
		final SignalComponent signalComponent = parseSimpleListSignal(simpleList, allListSignalComponents);

		final String newSignal = string.substring(0, indexOfLastOpeningBracket) + signalComponent.getUuid() + string.substring(closingBraceIndex + 1);
		return parseSignal(newSignal, allListSignalComponents);
	}

	private static SignalComponent parseSimpleListSignal(String string, List<SignalComponent> allListSignalComponents) {
		final SignalComponent signalComponent = new SignalComponent();
		for (String item : string.substring(1, string.length() - 1).split("\\s*,\\s*")) {
			if (item.matches("\\d+")) {
				final SignalComponent number = new SignalComponent(SignalComponent.Type.INTEGER);
				number.setInteger(Integer.valueOf(item));
				signalComponent.getComponents().add(number);
			} else if (!string.isBlank()) {
				final Optional<SignalComponent> component = allListSignalComponents.stream()
						.filter(signal -> signal.getUuid().equals(item))
						.findFirst();
				if (component.isPresent()) {
					signalComponent.getComponents().add(component.get());
				}
			}
		}
		allListSignalComponents.add(signalComponent);
		return signalComponent;
	}

	private static int getIndexOfLastOpeningBracket(String string) {
		int indexOfLastOpeningBracket = 0;
		for (int index = 0; index < string.length(); index++) {
			if (string.charAt(index) == '[') {
				indexOfLastOpeningBracket = index;
			}
		}
		return indexOfLastOpeningBracket;
	}

	public static int findClosingBraceIndex(String json, int redStartingIndex) {
		int closingIndex = 0;
		int rectBracesNeeded = 1;
		int currentIndex = redStartingIndex + 1;
		while (rectBracesNeeded > 0) {
			if (json.charAt(currentIndex) == ']') {
				rectBracesNeeded--;
				closingIndex = currentIndex;
			} else if (json.charAt(currentIndex) == '[') {
				rectBracesNeeded++;
			}
			currentIndex++;
		}
		return closingIndex;
	}

}
