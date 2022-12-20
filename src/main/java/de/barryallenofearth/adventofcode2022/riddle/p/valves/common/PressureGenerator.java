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
        while (remainingMinutes > 0) {
            if (currentValve.getFlowRate() > 0 && !currentValve.isOpen()) {
                accumulatedFlowRate += currentValve.getFlowRate();
                currentValve.setOpen(true);
                remainingMinutes--;
            } else {
                final Optional<Valve> next = determineNextValve(valves, currentValve, valveConnectionIntegerMap, remainingMinutes);
                if (next.isPresent()) {
                    final Integer minutesSpentMoving = valveConnectionIntegerMap.get(new ValveConnection(currentValve.getKey(), next.get().getKey()));
                    totalPressure += accumulatedFlowRate * minutesSpentMoving;
                    remainingMinutes -= minutesSpentMoving;
                    currentValve = next.get();
                    System.out.println(currentValve.getKey());
                } else {
                    //waiting for the end of the countdown
                    remainingMinutes--;
                }
            }
//                System.out.println("current flow rate :" + accumulatedFlowRate);
            totalPressure += accumulatedFlowRate;
        }
        System.out.println("total generated pressure :" + totalPressure);
        if (totalPressure > maxPressure) {
            maxPressure = totalPressure;
        }
        for (Valve sortedValve : sortedValves) {
            sortedValve.setOpen(false);
        }
        System.out.println("max generated pressure :" + maxPressure);
        return maxPressure;
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
                        return ((double) valve.getFlowRate()) / numberOfMinutesToReach;
                    }
                    return Integer.MIN_VALUE;
                }));

        return bestOption;
    }

    public static List<Queue<Valve>> getAllPermutations(List<Valve> valves) {
        final Valve aaValve = getByKey(STARTING_VALVE, valves);

        final List<Valve> usefulValves = valves.stream()
                .filter(valve -> valve.getFlowRate() > 0)
                .filter(valve -> !valve.getKey().equals(aaValve.getKey()))
                .collect(Collectors.toList());

        final List<Queue<Valve>> allSequences = initSequences(aaValve, usefulValves);
        for (int valveIndex = 0; valveIndex < usefulValves.size(); valveIndex++) {
            Valve valve = usefulValves.get(valveIndex);
            for (int sequenceIndex = valveIndex * usefulValves.size(); sequenceIndex < usefulValves.size(); sequenceIndex++) {
                Queue<Valve> sequence = allSequences.get(sequenceIndex);
                sequence.add(valve);
            }
        }


        return allSequences;
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
