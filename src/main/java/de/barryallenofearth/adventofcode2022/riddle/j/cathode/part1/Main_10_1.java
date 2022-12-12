package de.barryallenofearth.adventofcode2022.riddle.j.cathode.part1;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main_10_1 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		final int i = ProcessRegister.determineTotalSignalStrength(20, 40,220);
		System.out.println("The total signal strength is " + i);
	}
}
