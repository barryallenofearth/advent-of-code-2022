package de.barryallenofearth.adventofcode2022.riddle.s.part1;

import de.barryallenofearth.adventofcode2022.riddle.s.common.BluePrintReader;
import de.barryallenofearth.adventofcode2022.riddle.s.common.RunFactoryByBluePrint;
import de.barryallenofearth.adventofcode2022.riddle.s.common.model.BluePrint;
import de.barryallenofearth.adventofcode2022.riddle.s.common.model.RobotsAndFactory;

import java.util.List;

public class Main_19_1 {

	public static void main(String[] args) {
		final List<BluePrint> bluePrintList = BluePrintReader.readBluePrints();
		int blueprintID = 1;
		int totalQualityLevel = 0;
		for (BluePrint bluePrint : bluePrintList) {
			System.out.println("Starting to evaluate bluepring");
			System.out.println(bluePrint.getOrginalString());
			final RobotsAndFactory robotsAndFactory = RunFactoryByBluePrint.runFactory(bluePrint);
			final int numberOfGeodes = robotsAndFactory.getGeodes();
			totalQualityLevel += blueprintID++ * numberOfGeodes;
			System.out.println(numberOfGeodes + " geodes produced.");
			System.out.println();
		}
		System.out.println("total quality level is " + totalQualityLevel);
	}
}
