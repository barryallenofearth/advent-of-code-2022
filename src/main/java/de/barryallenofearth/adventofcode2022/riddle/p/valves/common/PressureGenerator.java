package de.barryallenofearth.adventofcode2022.riddle.p.valves.common;

import de.barryallenofearth.adventofcode2022.riddle.p.valves.model.Valve;
import de.barryallenofearth.adventofcode2022.riddle.p.valves.model.ValveConnection;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PressureGenerator {

	public static final int NUMBER_OF_MINUTES = 30;

	public static int findMaxPressureSequence(List<Valve> valves) {
		int totalPressure = 0;

		final Map<ValveConnection, Integer> valveConnectionIntegerMap = determineValveDistances(valves);

		final List<Valve> sortedValves = valves.stream()
				.sorted(Comparator.comparingInt(Valve::getFlowRate).reversed())
				.collect(Collectors.toList());
		int remainingMinutes = NUMBER_OF_MINUTES;
		while (remainingMinutes > 0) {
			remainingMinutes--;
		}

		return totalPressure;
	}

	private static Map<ValveConnection, Integer> determineValveDistances(List<Valve> valves) {

		final Map<ValveConnection, Integer> valveStepDistanceRelation = new HashMap<>();
		for (Valve startingValve : valves) {
			for (Valve stopValve : valves) {
				if (startingValve.equals(stopValve)) {
					continue;
				}
				int stepCount = 0;
				Valve currentValve = startingValve;
				while (!currentValve.equals(stopValve)) {
					if (stepCount > valves.size()) {
						throw new IllegalStateException("The number of steps taken exceeds the number of different valves.");
					}
					if (currentValve.getConnectedValveKeys().contains(stopValve.getKey())) {
						break;
					}
					stepCount++;
				}
				valveStepDistanceRelation.put(new ValveConnection(startingValve, stopValve), stepCount);
			}
		}
		return valveStepDistanceRelation;
	}
}
