package de.barryallenofearth.adventofcode2022.riddle.p.valves.part1;

import de.barryallenofearth.adventofcode2022.riddle.p.valves.common.PressureGenerator;
import de.barryallenofearth.adventofcode2022.riddle.p.valves.common.ValveParser;
import de.barryallenofearth.adventofcode2022.riddle.p.valves.model.Valve;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main_16_1 {
	public static void main(String[] args) throws IOException, URISyntaxException {

		final List<Valve> valves = ValveParser.readValves();
		PressureGenerator.findMaxPressureSequence(valves);
		System.out.println(valves);

	}
}
