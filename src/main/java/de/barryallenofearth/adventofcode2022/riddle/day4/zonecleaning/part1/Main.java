package de.barryallenofearth.adventofcode2022.riddle.day4.zonecleaning.part1;

import de.barryallenofearth.adventofcode2022.riddle.day4.zonecleaning.common.model.PairedZoneAssignment;
import de.barryallenofearth.adventofcode2022.riddle.day4.zonecleaning.common.util.AssignmentReader;
import de.barryallenofearth.adventofcode2022.riddle.day4.zonecleaning.part1.util.CompleteOverlapFinder;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		final List<PairedZoneAssignment> assignments = AssignmentReader.readAllAssignments("riddle-4.txt");
		final List<PairedZoneAssignment> assignmentWithCompleteZoneOverlap = CompleteOverlapFinder.findAssignmentWithCompleteZoneOverlap(assignments);

		System.out.println("The number of assignments where one elf's range is completely contained within the other is " + assignmentWithCompleteZoneOverlap.size());
	}
}
