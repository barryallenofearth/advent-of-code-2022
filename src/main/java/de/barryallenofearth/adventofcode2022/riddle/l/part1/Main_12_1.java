package de.barryallenofearth.adventofcode2022.riddle.l.part1;

import de.barryallenofearth.adventofcode2022.riddle.l.common.HeightMapReader;
import de.barryallenofearth.adventofcode2022.riddle.l.model.HeightJourneyModel;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main_12_1 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		final HeightJourneyModel heightJourneyModel = HeightMapReader.readHeightMap();

		for (int row = 0; row < heightJourneyModel.getHeightMap().length; row++) {
			for (int column = 0; column < heightJourneyModel.getHeightMap()[row].length; column++) {
				System.out.print(Character.toChars(heightJourneyModel.getHeightMap()[row][column] + 'a'));
			}
			System.out.println();
		}
	}
}
