package de.barryallenofearth.adventofcode2022.riddle.p.valves.common;

import de.barryallenofearth.adventofcode2022.riddle.p.valves.model.Valve;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValveParser {

	public static final Pattern VALVE_PATTERN = Pattern.compile("^Valve ([A-Z]+) has flow rate=(\\d+); tunnels? leads? to valves? ([A-Z\\s,]+)$");

	public static List<Valve> readValves() throws IOException, URISyntaxException {
		final List<String> strings = RiddleFileReader.readAllLines("riddle-16.txt");
		final List<Valve> valves = new ArrayList<>();

		for (String string : strings) {
			final Matcher matcher = VALVE_PATTERN.matcher(string);
			if (matcher.matches()) {
				final String valveKey = matcher.group(1);
				final int flowRate = Integer.parseInt(matcher.group(2));
				final List<String> connectedValveKeys = Arrays.asList(matcher.group(3).split("\\s*,\\s*"));
				valves.add(new Valve(valveKey, flowRate, connectedValveKeys));
			} else {
				throw new IllegalArgumentException("Valve '" + string + "' could not be parsed.");
			}
		}
		return valves;
	}
}
