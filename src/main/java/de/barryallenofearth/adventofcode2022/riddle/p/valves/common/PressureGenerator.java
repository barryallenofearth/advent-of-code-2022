package de.barryallenofearth.adventofcode2022.riddle.p.valves.common;

import de.barryallenofearth.adventofcode2022.riddle.p.valves.model.Valve;
import de.barryallenofearth.adventofcode2022.riddle.p.valves.model.ValveConnection;

import java.util.*;
import java.util.stream.Collectors;

public class PressureGenerator {

	public static final int NUMBER_OF_MINUTES = 30;

	public static final String STARTING_VALVE = "AA";

	public static int findMaxPressureSequence(List<Valve> valves) {

		int maxPressure = 0;
		final Map<ValveConnection, Integer> valveConnectionIntegerMap = determineValveDistances(valves);

		final List<Valve> sortedValves = valves.stream()
				.sorted(Comparator.comparingInt(Valve::getFlowRate).reversed())
				.collect(Collectors.toList());
		//iterate through all starting valvae
		int totalPressure = 0;
		int remainingMinutes = NUMBER_OF_MINUTES;
		int accumulatedFlowRate = 0;

		Valve currentValve = getByKey(STARTING_VALVE, valves);
		System.out.println("starting valve: " + currentValve.getKey());
		List<String> valveSequence = new ArrayList<>();
		valveSequence.add(currentValve.getKey());
		boolean isInIdleMode = false;
		while (remainingMinutes > 0) {
			if (currentValve.isOpen() && !isInIdleMode) {
				accumulatedFlowRate += currentValve.getFlowRate();
			}
			if (currentValve.getFlowRate() > 0 && !currentValve.isOpen()) {
				totalPressure += accumulatedFlowRate;
				System.out.println("Minute " + (NUMBER_OF_MINUTES - (remainingMinutes - 1)) + ": Open valve " + currentValve.getKey() + " in 1 minutes. releasing pressure=" + accumulatedFlowRate + "; total pressure=" + totalPressure);
				currentValve.setOpen(true);
				valveSequence.add(currentValve.getKey());
				remainingMinutes--;
			} else {
				final Optional<Valve> next = determineNextValve(valves, currentValve, valveConnectionIntegerMap, remainingMinutes);
				if (next.isPresent()) {
					final Integer minutesSpentMoving = valveConnectionIntegerMap.get(new ValveConnection(currentValve.getKey(), next.get().getKey()));
					System.out.println("Minute " + (NUMBER_OF_MINUTES - (remainingMinutes - 1)) + ": Move from " + currentValve.getKey() + " to " + next.get().getKey() + " in " + minutesSpentMoving + " minutes.");

					for (int minute = 0; minute < minutesSpentMoving; minute++) {
						totalPressure += accumulatedFlowRate;
						System.out.println("Minute " + (NUMBER_OF_MINUTES - (remainingMinutes - 1)) + ": moving; releasing pressure=" + accumulatedFlowRate + "; total pressure=" + totalPressure);
						remainingMinutes--;
					}
					currentValve = next.get();
				} else {
					//waiting for the end of the countdown
					isInIdleMode = true;
					totalPressure += accumulatedFlowRate;
					System.out.println("Minute " + (NUMBER_OF_MINUTES - (remainingMinutes - 1)) + ": idle... releasing pressure=" + accumulatedFlowRate + "; total pressure=" + totalPressure);
					remainingMinutes--;
				}
			}
		}

		for (Valve sortedValve : sortedValves) {
			sortedValve.setOpen(false);
		}

		for (String s : valveSequence) {
			System.out.println(s);
		}

		for (Valve valve : findShortestPath(valves, valveConnectionIntegerMap)) {
			System.out.println(valve.getKey());
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
				int stepCount = determineNumberOfSteps(valves, startingValve, stopValve);
				valveStepDistanceRelation.put(new ValveConnection(startingValve.getKey(), stopValve.getKey()), stepCount);
			}
		}
		return valveStepDistanceRelation;
	}

	private static int determineNumberOfSteps(List<Valve> valves, Valve startingValve, Valve stopValve) {
		int stepCount = 0;
		Stack<Stack<Valve>> openNodes = new Stack<>();
		final Stack<Valve> nextNodes = new Stack<>();
		nextNodes.add(startingValve);
		openNodes.push(nextNodes);
		final Set<Valve> closedNodes = new HashSet<>();
		while (!openNodes.isEmpty()) {
			final Stack<Valve> currentValveStack = openNodes.pop();
			final Stack<Valve> nextStepValves = new Stack<>();
			while (!currentValveStack.isEmpty()) {
				final Valve currentValve = currentValveStack.pop();
				if (currentValve.equals(stopValve)) {
					return stepCount;
				}
				if (stepCount > valves.size()) {
					throw new IllegalStateException("The number of steps taken exceeds the number of different valves.");
				}
				currentValve.getConnectedValveKeys().stream()
						.map(key -> getByKey(key, valves))
						.filter(valve -> !closedNodes.contains(valve))
						.forEach(nextStepValves::push);

				closedNodes.add(currentValve);
			}
			if (!nextStepValves.isEmpty()) {
				openNodes.add(nextStepValves);
			}
			stepCount++;
		}
		return stepCount;
	}

	public static Valve getByKey(String key, List<Valve> valves) {
		final Optional<Valve> matchingValve = valves.stream().filter(valve -> valve.getKey().equals(key)).findFirst();
		if (matchingValve.isEmpty()) {
			throw new IllegalStateException("No matching valve for key detected: " + key);
		}
		return matchingValve.get();
	}

	public static Optional<Valve> determineNextValve(List<Valve> valves, Valve currentValve, Map<ValveConnection, Integer> valveConnectionIntegerMap, int remainingMinutes) {
		final Optional<Valve> bestOption = valves.stream()
				.filter(valve -> !valve.isOpen())
				.filter(valve -> !valve.equals(currentValve))
				.filter(valve -> valve.getFlowRate() > 0)
				.filter(valve -> {
					final Integer numberOfMinutesToReach = valveConnectionIntegerMap.get(new ValveConnection(currentValve.getKey(), valve.getKey()));
					return numberOfMinutesToReach != null && remainingMinutes - numberOfMinutesToReach >= 0;
				})
				.max(Comparator.comparingDouble(valve -> {
					final Integer numberOfMinutesToReach = valveConnectionIntegerMap.get(new ValveConnection(currentValve.getKey(), valve.getKey()));
					if (numberOfMinutesToReach != null) {
						return ((double) valve.getFlowRate()) / (numberOfMinutesToReach * numberOfMinutesToReach);
					}
					return Integer.MIN_VALUE;
				}));

		return bestOption;
	}

	public static Queue<Valve> findShortestPath(List<Valve> valves, Map<ValveConnection, Integer> valveConnectionIntegerMap) {
		Queue<Valve> sequence = new LinkedList<>();

		final Valve aaValve = getByKey("AA", valves);
		sequence.add(aaValve);

		Valve currentValve = aaValve;
		final List<Valve> sortedValves = getSortedValves(valves, valveConnectionIntegerMap, currentValve, sequence);
		final int numberOfValvesWithFiniteFlowRate = sortedValves.size();

		while (sequence.size() < numberOfValvesWithFiniteFlowRate) {
			sequence.add(getSortedValves(valves, valveConnectionIntegerMap, currentValve, sequence).get(0));
		}

		return sequence;

	}

	private static List<Valve> getSortedValves(List<Valve> valves, Map<ValveConnection, Integer> valveConnectionIntegerMap, Valve currentValve, Queue<Valve> sequence) {
		final List<Valve> valvesWithFiniteFlowRate = valves.stream()
				.filter(valve -> valve.getFlowRate() > 0)
				.filter(valve -> !sequence.contains(valve))
				.sorted(Comparator.comparingInt(valve -> valveConnectionIntegerMap.get(new ValveConnection(currentValve.getKey(), valve.getKey()))))
				.collect(Collectors.toList());
		return valvesWithFiniteFlowRate;
	}

	private static List<Queue<Valve>> initSequences(Valve aaValve, List<Valve> usefulValves) {

		List<Queue<Valve>> allSequences = new ArrayList<>();
		for (int stackCount = 0; stackCount < usefulValves.size() * usefulValves.size(); stackCount++) {
			Queue<Valve> stack = new LinkedList<>();
			stack.add(aaValve);
			allSequences.add(stack);
		}
		return allSequences;
	}
}
