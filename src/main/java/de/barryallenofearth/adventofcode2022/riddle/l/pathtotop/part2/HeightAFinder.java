package de.barryallenofearth.adventofcode2022.riddle.l.pathtotop.part2;

import de.barryallenofearth.adventofcode2022.riddle.l.pathtotop.model.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeightAFinder {

	public static final Pattern PATH_PATTERN = Pattern.compile("\\s*(\\d+)a\\s*");

	public static Coordinates findBestStartingPoint(String[][] pathMap) {
		int originalMinimum = 520;
		int minNumberOfSteps = originalMinimum + 1;
		Coordinates minCoordinates = null;

		final List<Coordinates> lowRangeCandidates = new ArrayList<>();
		for (int row = 0; row < pathMap.length; row++) {
			for (int column = 0; column < pathMap[0].length; column++) {
				final Matcher matcher = PATH_PATTERN.matcher(pathMap[row][column]);
				if (matcher.matches()) {
					final int numberOfSteps = Integer.parseInt(matcher.group(1));
					final Coordinates coordinates = new Coordinates(row, column);
					lowRangeCandidates.add(coordinates);
					if (numberOfSteps < minNumberOfSteps) {
						minNumberOfSteps = numberOfSteps;
						minCoordinates = coordinates;
					}
				}
			}
		}

		for (int row = 0; row < pathMap.length; row++) {
			for (int column = 0; column < pathMap[0].length; column++) {
				final Coordinates coordinates = new Coordinates(row, column);
				if (coordinates.equals(minCoordinates)) {
					System.out.print("-M-");
				} else if (lowRangeCandidates.contains(coordinates)) {
					System.out.print(" # ");
				} else {
					System.out.print(" . ");
				}
			}
			System.out.println();
		}


		System.out.println("The number of steps required was: " + minNumberOfSteps);
		return minCoordinates;
	}
}
