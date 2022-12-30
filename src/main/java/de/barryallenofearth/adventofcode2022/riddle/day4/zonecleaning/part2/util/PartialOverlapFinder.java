package de.barryallenofearth.adventofcode2022.riddle.day4.zonecleaning.part2.util;

import de.barryallenofearth.adventofcode2022.riddle.day4.zonecleaning.common.model.PairedZoneAssignment;

import java.util.ArrayList;
import java.util.List;

public class PartialOverlapFinder {
	public static List<PairedZoneAssignment> findAssignmentWithCompleteZoneOverlap(List<PairedZoneAssignment> allAssignments) {
		final List<PairedZoneAssignment> completeOverlapOfAssignments = new ArrayList<>();
		for (PairedZoneAssignment currentAssignment : allAssignments) {
			if (completeContainment(currentAssignment.getElfZones1(), currentAssignment.getElfZones2())
					|| completeContainment(currentAssignment.getElfZones2(), currentAssignment.getElfZones1())
					|| partialContainment(currentAssignment.getElfZones1(), currentAssignment.getElfZones2())
					|| partialContainment(currentAssignment.getElfZones2(), currentAssignment.getElfZones1())) {
				completeOverlapOfAssignments.add(currentAssignment);

				System.out.println(currentAssignment);
			}
		}

		return completeOverlapOfAssignments;
	}

	private static boolean completeContainment(int[] elfZones1, int[] elfZones2) {
		return elfZones1[0] <= elfZones2[0] && elfZones1[1] >= elfZones2[1];
	}

	private static boolean partialContainment(int[] elfZones1, int[] elfZones2) {
		return elfZones1[0] == elfZones2[0]
				|| elfZones1[1] == elfZones2[1]
				|| (elfZones1[0] < elfZones2[0] && elfZones1[1] >= elfZones2[0]);
	}
}
