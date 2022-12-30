package de.barryallenofearth.adventofcode2022.riddle.day16.valves.part1;

import de.barryallenofearth.adventofcode2022.riddle.day16.valves.model.Valve;
import de.barryallenofearth.adventofcode2022.riddle.day16.valves.model.ValveConnection;

import java.util.*;
import java.util.stream.Collectors;

public class PressureGenerator {

	public static final int NUMBER_OF_MINUTES = 30;

	public static final String STARTING_VALVE = "AA";

	public static ValveSequence findMaxPressureSequence(List<Valve> valves) {

		final Map<ValveConnection, Integer> valveConnectionIntegerMap = determineValveDistances(valves);

		final List<Valve> openableValves = valves.stream()
				.filter(valve -> valve.getFlowRate() > 0)
				.sorted(Comparator.comparingInt(Valve::getFlowRate).reversed())
				.collect(Collectors.toList());
		//iterate through all starting valve

		final Valve aaValve = getByKey(STARTING_VALVE, valves);
		Stack<ValveSequence> openNodes = new Stack<>();
		final List<Valve> remainingValves = new ArrayList<>(openableValves);
		ValveSequence currentValveSequence = new ValveSequence(remainingValves, aaValve);
		currentValveSequence.getVisitedValves().add(aaValve.getKey());
		openNodes.add(currentValveSequence);

		int maxPressure = 0;
		ValveSequence maxValveSequence = currentValveSequence;

		int processedRoutes = 0;
		while (!openNodes.isEmpty()) {
			currentValveSequence = openNodes.pop();
			openAllOpenValvesInSequnece(openableValves, currentValveSequence);
			while (currentValveSequence.getMinute() <= NUMBER_OF_MINUTES) {
				//opening valve finished => increase flow rate
				openingValveComplete(currentValveSequence);

				if (currentValveSequence.getCurrentValve().getFlowRate() > 0 && !currentValveSequence.getCurrentValve().isOpen()) {
					openValve(currentValveSequence);
				} else {
					//move to next valve or enter idle mode
					final List<Valve> reachableValvesRemaining = getReachableValvesRemaining(valveConnectionIntegerMap, currentValveSequence);
					currentValveSequence.getRemainingValves().clear();
					currentValveSequence.getRemainingValves().addAll(reachableValvesRemaining);
					if (!reachableValvesRemaining.isEmpty()) {
						handleNextValve(valveConnectionIntegerMap, currentValveSequence, openNodes);
					} else {
						//waiting for the end of the countdown
						startIdleMode(currentValveSequence);
					}
				}
			}

			for (Valve sortedValve : openableValves) {
				sortedValve.setOpen(false);
			}
			if (currentValveSequence.getPressure() > maxPressure) {
				maxPressure = currentValveSequence.getPressure();
				maxValveSequence = currentValveSequence;
				System.out.println("new max pressure found: " + maxPressure);
			}
			processedRoutes++;
			if (processedRoutes % 1_000_000 == 0) {
				System.out.println(processedRoutes / 1_000_000 + "x10^6 routes processed.");
			}
		}

		return maxValveSequence;
	}

	private static List<Valve> getReachableValvesRemaining(Map<ValveConnection, Integer> valveConnectionIntegerMap, ValveSequence currentValveSequence) {
		return currentValveSequence.getRemainingValves().stream()
				.filter(valve -> {
					final Integer minutesToReach = valveConnectionIntegerMap.get(new ValveConnection(currentValveSequence.getCurrentValve().getKey(), valve.getKey()));
					return minutesToReach != null && minutesToReach < (NUMBER_OF_MINUTES - currentValveSequence.getMinute() - 1);
				})
				.collect(Collectors.toList());
	}

	private static void openAllOpenValvesInSequnece(List<Valve> sortedValves, ValveSequence currentValveSequence) {
		sortedValves.stream().filter(valve -> currentValveSequence.getOpenValves().contains(valve.getKey())).forEach(valve -> valve.setOpen(true));
	}

	private static void openingValveComplete(ValveSequence currentValveSequence) {
		if (currentValveSequence.getCurrentValve().isOpen() && !currentValveSequence.isInIdleMode()) {
			currentValveSequence.setFlowRate(currentValveSequence.getFlowRate() + currentValveSequence.getCurrentValve().getFlowRate());
		}
	}

	private static void openValve(ValveSequence currentValveSequence) {
		currentValveSequence.setPressure(currentValveSequence.getPressure() + currentValveSequence.getFlowRate());
		currentValveSequence.addMessage("Minute " + currentValveSequence.getMinute()
				+ ": Open valve " + currentValveSequence.getCurrentValve().getKey() + " in 1 minutes. releasing pressure=" + currentValveSequence.getFlowRate() +
				"; total pressure=" + currentValveSequence.getPressure() + "\n");
		currentValveSequence.getCurrentValve().setOpen(true);
		currentValveSequence.getOpenValves().add(currentValveSequence.getCurrentValve().getKey());
		currentValveSequence.setMinute(currentValveSequence.getMinute() + 1);
	}

	private static void handleNextValve(Map<ValveConnection, Integer> valveConnectionIntegerMap, ValveSequence currentValveSequence, Stack<ValveSequence> openNodes) {
		List<Valve> remainingValves = currentValveSequence.getRemainingValves();

		for (int index = remainingValves.size() - 1; index > 0; index--) {
			Valve next = remainingValves.get(index);
			final ValveSequence nextSequence = new ValveSequence(currentValveSequence, next);
			moveToValve(valveConnectionIntegerMap, nextSequence, currentValveSequence.getCurrentValve(), next);
			openNodes.add(nextSequence);
		}
		final Valve nextValve = remainingValves.get(0);
		moveToValve(valveConnectionIntegerMap, currentValveSequence, currentValveSequence.getCurrentValve(), nextValve);

	}

	private static void moveToValve(Map<ValveConnection, Integer> valveConnectionIntegerMap, ValveSequence currentValveSequence, Valve currentValve, Valve next) {
		final Integer minutesSpentMoving = valveConnectionIntegerMap.get(new ValveConnection(currentValve.getKey(), next.getKey()));
		currentValveSequence.addMessage("Minute " + currentValveSequence.getMinute() + ": Move from " + currentValve.getKey() + " to " + next.getKey() + " in " + minutesSpentMoving + " minutes.\n");

		for (int movingMinute = 0; movingMinute < minutesSpentMoving; movingMinute++) {
			currentValveSequence.setPressure(currentValveSequence.getPressure() + currentValveSequence.getFlowRate());
			currentValveSequence.addMessage("Minute " + currentValveSequence.getMinute() + ": moving; releasing pressure=" + currentValveSequence.getFlowRate() + "; total pressure=" + currentValveSequence.getPressure() + "\n");
			currentValveSequence.setMinute(currentValveSequence.getMinute() + 1);
		}
		currentValveSequence.setCurrentValve(next);
		currentValveSequence.getRemainingValves().remove(next);
	}

	static void startIdleMode(ValveSequence currentValveSequence) {
		currentValveSequence.setInIdleMode(true);
		currentValveSequence.setPressure(currentValveSequence.getPressure() + currentValveSequence.getFlowRate());
		currentValveSequence.addMessage("Minute " + currentValveSequence.getMinute() + ": idle... releasing pressure=" + currentValveSequence.getFlowRate() + "; total pressure=" + currentValveSequence.getPressure() + "\n");
		currentValveSequence.setMinute(currentValveSequence.getMinute() + 1);
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

}
