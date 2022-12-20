package de.barryallenofearth.adventofcode2022.riddle.p.valves.common;

import de.barryallenofearth.adventofcode2022.riddle.p.valves.model.Valve;
import de.barryallenofearth.adventofcode2022.riddle.p.valves.model.ValveConnection;

import java.util.*;
import java.util.stream.Collectors;

public class PressureGenerator {

    public static final int NUMBER_OF_MINUTES = 30;

    public static int findMaxPressureSequence(List<Valve> valves) {
        int totalPressure = 0;

        int accumulatedFlowRate = 0;
        final Map<ValveConnection, Integer> valveConnectionIntegerMap = determineValveDistances(valves);

        final List<Valve> sortedValves = valves.stream()
                .sorted(Comparator.comparingInt(Valve::getFlowRate).reversed())
                .collect(Collectors.toList());
        int remainingMinutes = NUMBER_OF_MINUTES;
        //iterate through all starting valvae
        for (Valve startingValve : sortedValves) {

            Valve currentValve = startingValve;
            while (remainingMinutes > 0) {
                if (currentValve.getFlowRate() > 0 && !currentValve.isOpen()) {
                    accumulatedFlowRate = currentValve.getFlowRate();
                } else {
                    //TODO next step handling
                }
                remainingMinutes--;
                totalPressure += accumulatedFlowRate;
            }
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
                valveStepDistanceRelation.put(new ValveConnection(startingValve, stopValve), stepCount);
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
