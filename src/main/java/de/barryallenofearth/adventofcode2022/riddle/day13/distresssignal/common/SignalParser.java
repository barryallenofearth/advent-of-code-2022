package de.barryallenofearth.adventofcode2022.riddle.day13.distresssignal.common;

import de.barryallenofearth.adventofcode2022.riddle.day13.distresssignal.model.SignalComponent;

import java.util.List;
import java.util.Optional;

public class SignalParser {

	public static SignalComponent parseSignal(String string, List<SignalComponent> allListSignalComponents) {
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
				component.ifPresent(value -> signalComponent.getComponents().add(value));
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
