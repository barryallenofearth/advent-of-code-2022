package de.barryallenofearth.adventofcode2022.riddle.m.part1;

import de.barryallenofearth.adventofcode2022.riddle.m.common.ReadSignalPairs;
import de.barryallenofearth.adventofcode2022.riddle.m.model.SignalPair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main_13_1 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		final List<SignalPair> signalPairList = ReadSignalPairs.signalPairList();
		for (SignalPair signalPair : signalPairList) {
			System.out.println(signalPair);
		}
	}
}
