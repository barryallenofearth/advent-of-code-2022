package de.barryallenofearth.adventofcode2022.riddle.day16.valves.part1;

import de.barryallenofearth.adventofcode2022.riddle.day16.valves.common.ValveParser;
import de.barryallenofearth.adventofcode2022.riddle.day16.valves.model.Valve;

import java.util.List;

public class Main_16_1 {
	public static void main(String[] args) {

		final List<Valve> valves = ValveParser.readValves();
		final ValveSequence maxPressureSequence = PressureGenerator.findMaxPressureSequence(valves);

		System.out.println(maxPressureSequence.getLogMessages());
		System.out.println("max generated pressure :" + maxPressureSequence.getPressure());

	}
}
