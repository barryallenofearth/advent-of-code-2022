package de.barryallenofearth.adventofcode2022.riddle.day16.valves.part2;

import de.barryallenofearth.adventofcode2022.riddle.day16.valves.model.Valve;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValveSequence {

	public ValveSequence(ValveSequence valveSequence, Valve myValve, Valve elephantValve, boolean isHumanBranch, boolean isElephantBranch) {
		this(valveSequence.getRemainingValves(), myValve, elephantValve);
		this.minute = valveSequence.getMinute();
		this.pressure = valveSequence.getPressure();
		this.flowRate = valveSequence.getFlowRate();
		this.isMeInIdleMode = valveSequence.isMeInIdleMode();
		this.isElephantInIdleMode = valveSequence.isElephantInIdleMode();
		this.visitedValves.addAll(valveSequence.getVisitedValves());
		this.openValves.addAll(valveSequence.getOpenValves());
		this.logMessages = new StringBuffer(valveSequence.getLogMessages());
		this.myRemainingSteps = valveSequence.getMyRemainingSteps();
		this.elephantRemainingSteps = valveSequence.getElephantRemainingSteps();
		this.myNextValve = valveSequence.getMyNextValve();
		this.elephantNextValve = valveSequence.getElephantNextValve();
		this.isHumanBranch = isHumanBranch;
		this.isElephantBranch = isElephantBranch;
	}

	public ValveSequence(List<Valve> remainingValves, Valve myValve, Valve elephantValve) {
		this.remainingValves = new ArrayList<>(remainingValves);
		this.remainingValves.remove(myValve);
		this.remainingValves.remove(elephantValve);
		this.visitedValves.add(myValve.getKey());
		this.visitedValves.add(elephantValve.getKey());
		this.myValve = myValve;
		this.elephantValve = elephantValve;
	}

	private boolean isHumanBranch = false;

	private boolean isElephantBranch = false;

	private final List<Valve> remainingValves;

	private int minute = 1;

	private int pressure = 0;

	private int flowRate = 0;

	private boolean isMeInIdleMode = false;

	private boolean isElephantInIdleMode = false;

	private Valve myValve;

	private Valve elephantValve;

	private Valve myNextValve;

	private Valve elephantNextValve;

	private int myRemainingSteps = 0;

	private int elephantRemainingSteps = 0;

	private final List<String> visitedValves = new ArrayList<>();

	private final List<String> openValves = new ArrayList<>();

	private StringBuffer logMessages = new StringBuffer();

	public void addMessage(String message) {
		logMessages.append(message);
	}

	public void decreaseMySteps() {
		this.myRemainingSteps--;
	}

	public void decreaseElephantSteps() {
		this.elephantRemainingSteps--;
	}
}
