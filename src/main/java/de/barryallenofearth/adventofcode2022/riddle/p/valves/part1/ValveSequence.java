package de.barryallenofearth.adventofcode2022.riddle.p.valves.part1;

import de.barryallenofearth.adventofcode2022.riddle.p.valves.model.Valve;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValveSequence {

	public ValveSequence(ValveSequence valveSequence, Valve currentValve) {
		this(valveSequence.getRemainingValves(), currentValve);
		this.minute = valveSequence.getMinute();
		this.pressure = valveSequence.getPressure();
		this.flowRate = valveSequence.getFlowRate();
		this.isInIdleMode = valveSequence.isInIdleMode();
		this.visitedValves.addAll(valveSequence.getVisitedValves());
		this.openValves.addAll(valveSequence.getOpenValves());
		this.logMessages = new StringBuffer(valveSequence.getLogMessages());
	}

	public ValveSequence(List<Valve> remainingValves, Valve currentValve) {
		this.remainingValves = new ArrayList<>(remainingValves);
		this.remainingValves.remove(currentValve);
		this.visitedValves.add(currentValve.getKey());
		this.currentValve = currentValve;
	}

	private final List<Valve> remainingValves;

	private int minute = 1;

	private int pressure = 0;

	private int flowRate = 0;

	private boolean isInIdleMode = false;

	private Valve currentValve;

	private final List<String> visitedValves = new ArrayList<>();

	private final List<String> openValves = new ArrayList<>();

	private StringBuffer logMessages = new StringBuffer();

	public void addMessage(String message) {
		logMessages.append(message);
	}

	public void addVisitedValve(Valve valve) {
		visitedValves.add(valve.getKey());
		remainingValves.remove(valve);
	}
}
