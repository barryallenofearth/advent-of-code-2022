package de.barryallenofearth.adventofcode2022.riddle.p.valves.part1;

import de.barryallenofearth.adventofcode2022.riddle.p.valves.common.PressureGenerator;
import de.barryallenofearth.adventofcode2022.riddle.p.valves.common.ValveParser;
import de.barryallenofearth.adventofcode2022.riddle.p.valves.model.Valve;
import de.barryallenofearth.adventofcode2022.riddle.p.valves.model.ValveSequence;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main_16_1 {
	public static void main(String[] args) {

		final List<Valve> valves = ValveParser.readValves();
		final ValveSequence maxPressureSequence = PressureGenerator.findMaxPressureSequence(valves);

		System.out.println(maxPressureSequence.getLogMessages());
		System.out.println("max generated pressure :" + maxPressureSequence.getPressure());

	}
}
