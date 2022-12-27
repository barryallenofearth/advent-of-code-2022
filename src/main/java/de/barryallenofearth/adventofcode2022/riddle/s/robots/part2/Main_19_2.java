package de.barryallenofearth.adventofcode2022.riddle.s.robots.part2;

import de.barryallenofearth.adventofcode2022.riddle.s.robots.common.BluePrintReader;
import de.barryallenofearth.adventofcode2022.riddle.s.robots.common.RunFactoryByBluePrint;
import de.barryallenofearth.adventofcode2022.riddle.s.robots.common.model.BluePrint;
import de.barryallenofearth.adventofcode2022.riddle.s.robots.common.model.RobotsAndFactory;

import java.util.List;

public class Main_19_2 {

	public static void main(String[] args) {
		final List<BluePrint> bluePrintList = BluePrintReader.readBluePrints();
		int blueprintID = 1;
		int totalQualityLevel = 1;
		for (BluePrint bluePrint : bluePrintList) {
			if (blueprintID > 3) {
				break;
			}
			System.out.println("Starting to evaluate blueprint " + blueprintID);
			System.out.println(bluePrint.getOrginalString());
			final RobotsAndFactory robotsAndFactory = RunFactoryByBluePrint.runFactory(bluePrint, 32);
			final int numberOfGeodes = robotsAndFactory.getGeodes();
			totalQualityLevel *= numberOfGeodes;
			System.out.println(numberOfGeodes + " geodes produced.");
			System.out.println();
			blueprintID++;
		}
		System.out.println("total quality level is " + totalQualityLevel);
	}
}
