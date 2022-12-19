package de.barryallenofearth.adventofcode2022.riddle.m.distresssignal.common;

import de.barryallenofearth.adventofcode2022.riddle.m.distresssignal.model.SignalComponent;
import de.barryallenofearth.adventofcode2022.riddle.m.distresssignal.model.SignalPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PairsInOrderDetector {

	public static List<SignalPair> detectPairsInOrder(List<SignalPair> signalPairs) {
		List<SignalPair> pairsInOrder = new ArrayList<>();

		for (SignalPair signalPair : signalPairs) {
			final Boolean pairInOfOrder = isPairInOrder(signalPair.getFirst(), signalPair.getSecond());
			if (pairInOfOrder == null || pairInOfOrder) {
				pairsInOrder.add(signalPair);
				//} else {
				//System.out.println(signalPair.getIndex());
				//System.out.println(signalPair.getOriginalFirst());
				//System.out.println(signalPair.getOriginalSecond());
				//System.out.println();
			}
		}

		return pairsInOrder;
	}

	public static Boolean isPairInOrder(SignalComponent first, SignalComponent second) {

		for (int component = 0; component < first.getComponents().size(); component++) {
			if (component == second.getComponents().size()) {
				//System.out.println("Signal is out of order. List length. reach component index: " + component);
				//System.out.println("First list: " + first);
				//System.out.println("Second list: " + second);
				return false;
			}
			final SignalComponent currentFirst = first.getComponents().get(component);
			final SignalComponent currentSecond = second.getComponents().get(component);
			if (currentFirst.getType() == SignalComponent.Type.INTEGER && currentSecond.getType() == SignalComponent.Type.INTEGER) {
				if (Objects.equals(currentFirst.getInteger(), currentSecond.getInteger())) {
					continue;
				}
				final boolean isInOrder = currentFirst.getInteger() < currentSecond.getInteger();
				//if (!isInOrder) {
				//	System.out.println("Signal is out of order. Integers. " + currentFirst.getInteger() + " > " + currentSecond.getInteger());
				//}
				return isInOrder;
			} else if (currentFirst.getType() == SignalComponent.Type.LIST && currentSecond.getType() == SignalComponent.Type.LIST) {
				final Boolean pairInOfOrder = isPairInOrder(currentFirst, currentSecond);
				if (pairInOfOrder == null) {
					continue;
				}
				//if (!pairInOfOrder) {
				//	System.out.println("Signal is out of order. Integers. " + currentFirst.getInteger() + " > " + currentSecond.getInteger());
				//}
				return pairInOfOrder;
			} else if (currentFirst.getType() == SignalComponent.Type.INTEGER && currentSecond.getType() == SignalComponent.Type.LIST) {
				final SignalComponent listComponent = new SignalComponent();
				listComponent.getComponents().add(currentFirst);
				final Boolean pairInOfOrder = isPairInOrder(listComponent, currentSecond);
				if (pairInOfOrder == null) {
					continue;
				}
				return pairInOfOrder;
			} else if (currentFirst.getType() == SignalComponent.Type.LIST && currentSecond.getType() == SignalComponent.Type.INTEGER) {
				final SignalComponent listComponent = new SignalComponent();
				listComponent.getComponents().add(currentSecond);
				final Boolean pairInOfOrder = isPairInOrder(currentFirst, listComponent);
				if (pairInOfOrder == null) {
					continue;
				}
				return pairInOfOrder;
			}
		}
		return first.getComponents().size() == second.getComponents().size() ? null : first.getComponents().size() < second.getComponents().size();
	}
}
