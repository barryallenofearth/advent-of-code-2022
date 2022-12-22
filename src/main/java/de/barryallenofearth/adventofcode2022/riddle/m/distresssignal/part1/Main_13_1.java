package de.barryallenofearth.adventofcode2022.riddle.m.distresssignal.part1;

import de.barryallenofearth.adventofcode2022.riddle.m.distresssignal.common.PairsInOrderDetector;
import de.barryallenofearth.adventofcode2022.riddle.m.distresssignal.model.SignalPair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

public class Main_13_1 {
	public static void main(String[] args) {
		final List<SignalPair> signalPairList = ReadSignalPairs.signalPairList();

		final List<SignalPair> pairsOutOfOrder = PairsInOrderDetector.detectPairsInOrder(signalPairList);
		System.out.println(pairsOutOfOrder.stream().map(signalPair -> String.valueOf(signalPair.getIndex())).collect(Collectors.joining(",")) + " elements are in order.");
		System.out.println(pairsOutOfOrder.stream().mapToInt(SignalPair::getIndex).sum() + " is the sum of the indexes with signals out of order.");

	}
}
