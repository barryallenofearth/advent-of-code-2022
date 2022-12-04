package de.barryallenofearth.adventofcode2022.riddle.d.zonecleaning.part1.util;

import de.barryallenofearth.adventofcode2022.riddle.d.zonecleaning.common.model.PairedZoneAssignment;

import java.util.ArrayList;
import java.util.List;

public class CompleteOverlapFinder {
	public static List<PairedZoneAssignment> findAssignmentWithCompleteZoneOverlap(List<PairedZoneAssignment> allAssignments) {
		final List<PairedZoneAssignment> completeOverlapOfAssignments = new ArrayList<>();
		for (PairedZoneAssignment currentAssignment : allAssignments) {
			if (completeContainment(currentAssignment.getElfZones1(), currentAssignment.getElfZones2())
					|| completeContainment(currentAssignment.getElfZones2(), currentAssignment.getElfZones1())) {
				completeOverlapOfAssignments.add(currentAssignment);
			}
		}

		return completeOverlapOfAssignments;
	}

	private static boolean completeContainment(int[] elfZones1, int[] elfZones2) {
		return elfZones1[0] <= elfZones2[0] && elfZones1[1] >= elfZones2[1];
	}
}
