package de.barryallenofearth.adventofcode2022.riddle.m.common;

import de.barryallenofearth.adventofcode2022.riddle.m.model.SignalComponent;
import de.barryallenofearth.adventofcode2022.riddle.m.model.SignalPair;

import java.util.ArrayList;
import java.util.List;

public class PairsInOrderDetector {

	public static List<SignalPair> detectPairsInOrder(List<SignalPair> signalPairs) {
		List<SignalPair> pairsInOrder = new ArrayList<>();

		for (SignalPair signalPair : signalPairs) {
			if (isPairInOfOrder(signalPair)) {
				pairsInOrder.add(signalPair);
			}
		}

		return pairsInOrder;
	}

	public static boolean isPairInOfOrder(SignalPair signalPair) {
		final SignalComponent first = signalPair.getFirst();
		final SignalComponent second = signalPair.getSecond();

		if (first.getComponents().size() > second.getComponents().size()) {
			return false;
		}

		for (int component = 0; component < first.getComponents().size(); component++) {
			final SignalComponent currentFirst = first.getComponents().get(component);
			final SignalComponent currentSecond = second.getComponents().get(component);
			if (currentFirst.getType() == SignalComponent.Type.INTEGER && currentSecond.getType() == SignalComponent.Type.INTEGER) {
				if (currentFirst.getInteger() > currentSecond.getInteger()) {
					return false;
				}
			} else if (currentFirst.getType() == SignalComponent.Type.INTEGER && currentSecond.getType() == SignalComponent.Type.LIST) {
				final SignalPair newListPair = new SignalPair(signalPair.getIndex());
				final SignalComponent signalComponent = new SignalComponent();
				signalComponent.getComponents().add(currentFirst);
				newListPair.setFirst(signalComponent);
				newListPair.setSecond(currentSecond);
				return isPairInOfOrder(newListPair);
			} else if (currentFirst.getType() == SignalComponent.Type.LIST && currentSecond.getType() == SignalComponent.Type.INTEGER) {
				final SignalPair newListPair = new SignalPair(signalPair.getIndex());
				newListPair.setFirst(currentFirst);
				final SignalComponent signalComponent = new SignalComponent();
				signalComponent.getComponents().add(currentSecond);
				newListPair.setSecond(signalComponent);
				return isPairInOfOrder(newListPair);
			} else if (currentFirst.getType() == SignalComponent.Type.LIST && currentSecond.getType() == SignalComponent.Type.LIST) {
				final SignalPair newListPair = new SignalPair(signalPair.getIndex());
				newListPair.setFirst(currentFirst);
				newListPair.setSecond(currentSecond);
				return isPairInOfOrder(newListPair);
			}
		}
		return true;
	}
}
