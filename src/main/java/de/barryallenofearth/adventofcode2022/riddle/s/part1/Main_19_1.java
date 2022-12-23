package de.barryallenofearth.adventofcode2022.riddle.s.part1;

import de.barryallenofearth.adventofcode2022.riddle.s.common.BluePrintReader;
import de.barryallenofearth.adventofcode2022.riddle.s.common.RunFactoryByBluePrint;
import de.barryallenofearth.adventofcode2022.riddle.s.common.model.BluePrint;
import de.barryallenofearth.adventofcode2022.riddle.s.common.model.RobotsAndFactory;

import java.util.List;

public class Main_19_1 {

	public static void main(String[] args) {
		final List<BluePrint> bluePrintList = BluePrintReader.readBluePrints();
		for (BluePrint bluePrint : bluePrintList) {
			final RobotsAndFactory robotsAndFactory = RunFactoryByBluePrint.runFactory(bluePrint);
			System.out.println(robotsAndFactory.getFactory().getGeodes() + " geodes produced.");
		}
		System.out.println(bluePrintList);
	}
}
