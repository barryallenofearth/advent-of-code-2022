package de.barryallenofearth.adventofcode2022.riddle.m.common;

import de.barryallenofearth.adventofcode2022.riddle.m.model.SignalComponent;
import de.barryallenofearth.adventofcode2022.riddle.m.model.SignalPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PairsInOrderDetector {

	public static List<SignalPair> detectPairsInOrder(List<SignalPair> signalPairs) {
		List<SignalPair> pairsInOrder = new ArrayList<>();

		for (SignalPair signalPair : signalPairs) {
			final Boolean pairInOfOrder = isPairInOrder(signalPair);
			if (pairInOfOrder == null || pairInOfOrder) {
				pairsInOrder.add(signalPair);
			}
		}

		return pairsInOrder;
	}

	public static Boolean isPairInOrder(SignalPair signalPair) {
		final SignalComponent first = signalPair.getFirst();
		final SignalComponent second = signalPair.getSecond();

		for (int component = 0; component < first.getComponents().size(); component++) {
			if (component == second.getComponents().size()) {
				return false;
			}
			final SignalComponent currentFirst = first.getComponents().get(component);
			final SignalComponent currentSecond = second.getComponents().get(component);
			if (currentFirst.getType() == SignalComponent.Type.INTEGER && currentSecond.getType() == SignalComponent.Type.INTEGER) {
				if (Objects.equals(currentFirst.getInteger(), currentSecond.getInteger())) {
					continue;
				}
				return currentFirst.getInteger() < currentSecond.getInteger();
			} else if (currentFirst.getType() == SignalComponent.Type.LIST && currentSecond.getType() == SignalComponent.Type.LIST) {
				final SignalPair newListPair = new SignalPair(signalPair.getIndex());
				newListPair.setFirst(currentFirst);
				newListPair.setSecond(currentSecond);
				final Boolean pairInOfOrder = isPairInOrder(newListPair);
				if (pairInOfOrder == null) {
					continue;
				}
				return pairInOfOrder;
			} else if (currentFirst.getType() == SignalComponent.Type.INTEGER && currentSecond.getType() == SignalComponent.Type.LIST) {
				final SignalPair newListPair = new SignalPair(signalPair.getIndex());
				final SignalComponent listComponent = new SignalComponent();
				listComponent.getComponents().add(currentFirst);
				newListPair.setFirst(listComponent);
				newListPair.setSecond(currentSecond);
				final Boolean pairInOfOrder = isPairInOrder(newListPair);
				if (pairInOfOrder == null) {
					continue;
				}
				return pairInOfOrder;
			} else if (currentFirst.getType() == SignalComponent.Type.LIST && currentSecond.getType() == SignalComponent.Type.INTEGER) {
				final SignalPair newListPair = new SignalPair(signalPair.getIndex());
				newListPair.setFirst(currentFirst);
				final SignalComponent listComponent = new SignalComponent();
				listComponent.getComponents().add(currentSecond);
				newListPair.setSecond(listComponent);
				final Boolean pairInOfOrder = isPairInOrder(newListPair);
				if (pairInOfOrder == null) {
					continue;
				}
				return pairInOfOrder;
			}
		}
		return null;
	}
}
