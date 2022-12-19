package de.barryallenofearth.adventofcode2022.riddle.m.distresssignal.part2;

import de.barryallenofearth.adventofcode2022.riddle.m.distresssignal.common.PairsInOrderDetector;
import de.barryallenofearth.adventofcode2022.riddle.m.distresssignal.common.SignalParser;
import de.barryallenofearth.adventofcode2022.riddle.m.distresssignal.model.SignalComponent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main_13_2 {
	public static final String DIVIDER_2 = "[[2]]";

	public static final String DIVIDER_6 = "[[6]]";

	public static void main(String[] args) throws IOException, URISyntaxException {
		final List<SignalComponent> signalComponents = ReadSignals.readSignals();
		final List<SignalComponent> allSubSignals = new ArrayList<>();
		final SignalComponent signalComponent1 = SignalParser.parseSignal(DIVIDER_2, allSubSignals);
		signalComponent1.setOriginalString(DIVIDER_2);
		signalComponents.add(signalComponent1);
		final SignalComponent signalComponent2 = SignalParser.parseSignal(DIVIDER_6, allSubSignals);
		signalComponent2.setOriginalString(DIVIDER_6);
		signalComponents.add(signalComponent2);

		sortSignals(signalComponents);
		final List<String> stringStream = signalComponents.stream()
				.map(SignalComponent::getOriginalString)
				.collect(Collectors.toList());

		final int index2 = stringStream.indexOf(DIVIDER_2) + 1;
		final int index6 = stringStream.indexOf(DIVIDER_6) + 1;

		System.out.println(index2 * index6);
	}

	private static void sortSignals(List<SignalComponent> signalComponents) {
		signalComponents.sort((signal1, signal2) -> {
			final Boolean pairInOrder = PairsInOrderDetector.isPairInOrder(signal1, signal2);
			if (pairInOrder == null) {
				return 0;
			} else if (pairInOrder) {
				return -1;
			} else {
				return 1;
			}
		});
	}
}
