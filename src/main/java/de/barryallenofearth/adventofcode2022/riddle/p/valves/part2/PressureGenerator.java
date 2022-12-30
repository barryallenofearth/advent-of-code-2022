package de.barryallenofearth.adventofcode2022.riddle.p.valves.part2;

import de.barryallenofearth.adventofcode2022.riddle.p.valves.model.Valve;
import de.barryallenofearth.adventofcode2022.riddle.p.valves.model.ValveConnection;

import java.util.*;
import java.util.stream.Collectors;

public class PressureGenerator {

	public static final int NUMBER_OF_MINUTES = 26;

	public static final String STARTING_VALVE = "AA";

	public static final String YOU = "you";

	public static final String ELEPHANT = "elephant";

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
		ValveSequence sequence = new ValveSequence(remainingValves, aaValve, aaValve);
		sequence.getVisitedValves().add(aaValve.getKey());
		openNodes.add(sequence);

		int maxPressure = 0;
		ValveSequence maxValveSequence = sequence;

		int processedRoutes = 0;
		while (!openNodes.isEmpty()) {
			sequence = openNodes.pop();
			reopenAllOpenValvesInSequnece(openableValves, sequence);
			for (int minute = sequence.getMinute(); minute <= NUMBER_OF_MINUTES; minute++) {
				//if (abortBranch(openableValves, sequence, maxPressure)) {
				//	break;
				//}
				if (!sequence.isHumanBranch() && !sequence.isElephantBranch()) {
					sequence.setMinute(minute);
					sequence.addMessage("== Minute " + sequence.getMinute() + " ==\n");
					//opening valve finished => increase flow rate
					openingValveComplete(sequence);
				}
				if (!sequence.isElephantBranch()) {
					handleMyActions(valveConnectionIntegerMap, openNodes, sequence);
				}

				handleElephantAction(valveConnectionIntegerMap, openNodes, sequence);
				sequence.setPressure(sequence.getPressure() + sequence.getFlowRate());
				sequence.addMessage("total flow rate=" + sequence.getFlowRate() + "; total pressure=" + sequence.getPressure() + "\n\n");

				sequence.setHumanBranch(false);
				sequence.setElephantBranch(false);
			}

			for (Valve sortedValve : openableValves) {
				sortedValve.setOpen(false);
			}
			if (sequence.getPressure() > maxPressure) {
				maxPressure = sequence.getPressure();
				maxValveSequence = sequence;
				System.out.println("new max pressure found: " + maxPressure);
			}
			processedRoutes++;
			if (processedRoutes % 1_000_000 == 0) {
				System.out.println(processedRoutes / 1_000_000 + "x10^6 routes processed.");
			}
		}

		System.out.println(processedRoutes + " routes have been processed.");
		return maxValveSequence;
	}

	private static boolean abortBranch(List<Valve> valves, ValveSequence sequence, int maxPressure) {
		final Stack<Integer> remainingFlowRate = valves.stream()
				.filter(valve -> !sequence.getOpenValves().contains(valve.getKey()))
				.map(Valve::getFlowRate)
				.sorted(Comparator.reverseOrder())
				.collect(Collectors.toCollection(Stack::new));

		int pressure = sequence.getPressure();
		int totalFlowRate = sequence.getFlowRate();
		for (int remainingMinutes = sequence.getMinute(); remainingMinutes <= NUMBER_OF_MINUTES; remainingMinutes++) {
			if (!remainingFlowRate.isEmpty()) {
				totalFlowRate += remainingFlowRate.pop();
				if (!remainingFlowRate.isEmpty()) {
					totalFlowRate += remainingFlowRate.pop();
				}
			}
			pressure += totalFlowRate;
		}

		return pressure < maxPressure;
	}

	private static void handleMyActions(Map<ValveConnection, Integer> valveConnectionIntegerMap, Stack<ValveSequence> openNodes, ValveSequence sequence) {

		if (!sequence.isHumanBranch() && sequence.getMyRemainingSteps() == 0) {
			if (sequence.getMyValve().getFlowRate() > 0 && !sequence.getMyValve().isOpen()) {
				openValve(sequence, sequence.getMyValve(), YOU);
			} else if (sequence.getMyNextValve() == null) {
				//move to next valve or enter idle mode
				final List<Valve> reachableValvesRemaining = getReachableValvesRemaining(valveConnectionIntegerMap, sequence, sequence.getMyValve());
				if (!reachableValvesRemaining.isEmpty()) {
					handleNextMyValve(valveConnectionIntegerMap, sequence, openNodes);
				} else {
					//waiting for the end of the countdown
					sequence.setMeInIdleMode(true);
					sequence.addMessage(YOU + " idle...\n");
				}
			}
		}

		if (sequence.getMyRemainingSteps() > 0) {
			sequence.decreaseMySteps();
			sequence.addMessage(YOU + " are moving. Steps remaining from " + sequence.getMyValve().getKey()
					+ " to " + sequence.getMyNextValve().getKey() + ": " + sequence.getMyRemainingSteps() + "\n");
		}
		if (sequence.getMyRemainingSteps() == 0 && sequence.getMyNextValve() != null) {
			sequence.addMessage(YOU + " reached valve " + sequence.getMyNextValve().getKey() + "\n");
			sequence.setMyValve(sequence.getMyNextValve());
			sequence.setMyNextValve(null);
			sequence.getVisitedValves().add(sequence.getMyValve().getKey());
		}
	}

	private static void handleElephantAction(Map<ValveConnection, Integer> valveConnectionIntegerMap, Stack<ValveSequence> openNodes, ValveSequence sequence) {
		if (!sequence.isElephantBranch() && sequence.getElephantRemainingSteps() == 0) {
			if (sequence.getElephantValve().getFlowRate() > 0 && !sequence.getElephantValve().isOpen()) {
				openValve(sequence, sequence.getElephantValve(), ELEPHANT);
			} else if (sequence.getElephantNextValve() == null) {
				//move to next valve or enter idle mode
				final List<Valve> reachableValvesRemaining = getReachableValvesRemaining(valveConnectionIntegerMap, sequence, sequence.getElephantValve());
				if (!reachableValvesRemaining.isEmpty()) {
					handleNextElephantValve(valveConnectionIntegerMap, sequence, openNodes);
				} else {
					//waiting for the end of the countdown
					sequence.setElephantInIdleMode(true);
					sequence.addMessage(ELEPHANT + " idle...\n");
				}
			}
		}

		if (sequence.getElephantRemainingSteps() > 0) {
			sequence.decreaseElephantSteps();
			sequence.addMessage(ELEPHANT + " is moving. Steps remaining from "
					+ sequence.getElephantValve().getKey() + " to " + sequence.getElephantNextValve().getKey()
					+ ":" + sequence.getElephantRemainingSteps() + "\n");
		}
		if (sequence.getElephantRemainingSteps() == 0 && sequence.getElephantNextValve() != null) {
			sequence.addMessage(ELEPHANT + " reached valve " + sequence.getElephantNextValve().getKey() + "\n");
			sequence.setElephantValve(sequence.getElephantNextValve());
			sequence.setElephantNextValve(null);
			sequence.getVisitedValves().add(sequence.getElephantValve().getKey());
		}

	}

	private static List<Valve> getReachableValvesRemaining(Map<ValveConnection, Integer> valveConnectionIntegerMap, ValveSequence currentValveSequence, Valve currentValve) {
		return currentValveSequence.getRemainingValves().stream()
				.filter(valve -> {
					final Integer minutesToReach = valveConnectionIntegerMap.get(new ValveConnection(currentValve.getKey(), valve.getKey()));
					return minutesToReach != null && minutesToReach <= (NUMBER_OF_MINUTES - currentValveSequence.getMinute() - 1);
				})
				.collect(Collectors.toList());
	}

	private static void reopenAllOpenValvesInSequnece(List<Valve> sortedValves, ValveSequence currentValveSequence) {
		sortedValves.stream().filter(valve -> currentValveSequence.getOpenValves().contains(valve.getKey())).forEach(valve -> valve.setOpen(true));
	}

	private static void openingValveComplete(ValveSequence sequence) {
		if (sequence.getMyValve().isOpen() && !sequence.isMeInIdleMode() && sequence.getMyNextValve() == null) {
			sequence.setFlowRate(sequence.getFlowRate() + sequence.getMyValve().getFlowRate());
			sequence.addMessage("you finished opening valve " + sequence.getMyValve().getKey() + "\n");
		}
		if (sequence.getElephantValve().isOpen() && !sequence.isElephantInIdleMode() && sequence.getElephantNextValve() == null) {
			sequence.setFlowRate(sequence.getFlowRate() + sequence.getElephantValve().getFlowRate());
			sequence.addMessage("elephant finished opening valve " + sequence.getElephantValve().getKey() + "\n");
		}
	}

	private static void openValve(ValveSequence currentValveSequence, Valve valve, String name) {
		currentValveSequence.addMessage(name + " open valve " + valve.getKey() + " in 1 minutes.\n");
		valve.setOpen(true);
		currentValveSequence.getOpenValves().add(valve.getKey());
	}

	private static void handleNextMyValve(Map<ValveConnection, Integer> valveConnectionIntegerMap, ValveSequence sequence, Stack<ValveSequence> openNodes) {
		List<Valve> remainingValves = sequence.getRemainingValves();

		for (int index = remainingValves.size() - 1; index > 0; index--) {
			Valve next = remainingValves.get(index);
			final ValveSequence nextSequence = new ValveSequence(sequence, sequence.getMyValve(), sequence.getElephantValve(), true, false);
			initMoveToValve(valveConnectionIntegerMap, nextSequence, sequence.getMyValve(), next, YOU);
			nextSequence.setMyNextValve(next);

			openNodes.add(nextSequence);
		}
		final Valve nextValve = remainingValves.get(0);
		initMoveToValve(valveConnectionIntegerMap, sequence, sequence.getMyValve(), nextValve, YOU);
		sequence.setMyNextValve(nextValve);

	}

	private static void handleNextElephantValve(Map<ValveConnection, Integer> valveConnectionIntegerMap, ValveSequence sequence, Stack<ValveSequence> openNodes) {
		List<Valve> remainingValves = sequence.getRemainingValves();

		for (int index = remainingValves.size() - 1; index > 0; index--) {
			Valve next = remainingValves.get(index);
			final ValveSequence nextSequence = new ValveSequence(sequence, sequence.getMyValve(), sequence.getElephantValve(), false, true);
			initMoveToValve(valveConnectionIntegerMap, nextSequence, sequence.getElephantValve(), next, ELEPHANT);
			nextSequence.setElephantNextValve(next);

			openNodes.add(nextSequence);
		}
		final Valve nextValve = remainingValves.get(0);
		initMoveToValve(valveConnectionIntegerMap, sequence, sequence.getElephantValve(), nextValve, ELEPHANT);
		sequence.setElephantNextValve(nextValve);

	}

	private static void initMoveToValve(Map<ValveConnection, Integer> valveConnectionIntegerMap, ValveSequence sequence, Valve currentValve, Valve next, String name) {
		final Integer minutesSpentMoving = valveConnectionIntegerMap.get(new ValveConnection(currentValve.getKey(), next.getKey()));
		if (name.equals(YOU)) {
			sequence.setMyRemainingSteps(minutesSpentMoving);
		} else {
			sequence.setElephantRemainingSteps(minutesSpentMoving);
		}
		sequence.addMessage(name + " move from " + currentValve.getKey() + " to " + next.getKey() + " in " + minutesSpentMoving + " minutes.\n");
		sequence.getRemainingValves().remove(next);
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
