package de.barryallenofearth.adventofcode2022.riddle.d.zonecleaning.common.util;

import de.barryallenofearth.adventofcode2022.riddle.d.zonecleaning.common.model.PairedZoneAssignment;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class AssignmentReader {

	public static List<PairedZoneAssignment> readAllAssignments(String fileName) {
		final List<String> allLines = RiddleFileReader.readAllLines(fileName);
		final List<PairedZoneAssignment> assignments = new ArrayList<>();
		for (String line : allLines) {
			final String[] assignmentPerElf = line.split(",");

			final String[] firstElfZoneLimits = assignmentPerElf[0].split("-");
			final String[] secondElfZoneLimits = assignmentPerElf[1].split("-");

			final PairedZoneAssignment pairedZoneAssignment = new PairedZoneAssignment();
			assignments.add(pairedZoneAssignment);

			pairedZoneAssignment.getElfZones1()[0] = Integer.parseInt(firstElfZoneLimits[0]);
			pairedZoneAssignment.getElfZones1()[1] = Integer.parseInt(firstElfZoneLimits[1]);

			pairedZoneAssignment.getElfZones2()[0] = Integer.parseInt(secondElfZoneLimits[0]);
			pairedZoneAssignment.getElfZones2()[1] = Integer.parseInt(secondElfZoneLimits[1]);

		}
		return assignments;
	}
}
