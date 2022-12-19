package de.barryallenofearth.adventofcode2022.riddle.l.pathtotop.common;

import de.barryallenofearth.adventofcode2022.riddle.l.pathtotop.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.l.pathtotop.model.HeightJourneyModel;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class HeightMapReader {

	public static HeightJourneyModel readHeightMap() throws IOException, URISyntaxException {
		final List<String> strings = RiddleFileReader.readAllLines("riddle-12.txt");
		final int numberOfColumns = strings.get(0).length();
		final int numberOfRows = strings.size();

		HeightJourneyModel heightJourneyModel = new HeightJourneyModel();
		heightJourneyModel.setHeightMap(new int[numberOfRows][numberOfColumns]);
		for (int row = 0; row < numberOfRows; row++) {
			heightJourneyModel.getHeightMap()[row] = new int[numberOfColumns];
		}
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				char currentChar = strings.get(row).charAt(column);
				if (currentChar == 'S') {
					heightJourneyModel.setStartingPosition(new Coordinates(row, column));
					currentChar = 'a';
				} else if (currentChar == 'E') {
					heightJourneyModel.setTargetPosition(new Coordinates(row, column));
					currentChar = 'z';
				}
				final int height = currentChar - 'a';
				heightJourneyModel.getHeightMap()[row][column] = height;
			}
		}
		return heightJourneyModel;
	}
}
