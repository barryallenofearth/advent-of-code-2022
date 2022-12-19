package de.barryallenofearth.adventofcode2022.riddle.n.sand.common;

import de.barryallenofearth.adventofcode2022.riddle.n.sand.model.CaveModel;
import de.barryallenofearth.adventofcode2022.riddle.n.sand.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class RockCoordinatesReader {

	public static final String COORDINATES_SEPARATOR = " -> ";

	public static CaveModel readRockCoordinates() throws IOException, URISyntaxException {
		final CaveModel caveModel = new CaveModel();
		for (String line : RiddleFileReader.readAllLines("riddle-14.txt")) {
			final String[] coordinates = line.split(COORDINATES_SEPARATOR);
			for (int index = 0; index < coordinates.length - 1; index++) {
				final Coordinates startCoordinates = getCoordinates(coordinates[index]);
				final Coordinates stopCoordinates = getCoordinates(coordinates[index + 1]);

				int startX = Math.min(startCoordinates.getRow(), stopCoordinates.getRow());
				int stopX = Math.max(startCoordinates.getRow(), stopCoordinates.getRow());
				int startY = Math.min(startCoordinates.getColumn(), stopCoordinates.getColumn());
				int stopY = Math.max(startCoordinates.getColumn(), stopCoordinates.getColumn());

				evaluateNewMinMaxValues(caveModel, startX, stopX, startY, stopY);

				addRockCoordinates(caveModel, startX, stopX, startY, stopY);
			}
		}

		return caveModel;
	}

	private static void addRockCoordinates(CaveModel caveModel, int startX, int stopX, int startY, int stopY) {
		for (int row = startX; row <= stopX; row++) {
			for (int column = startY; column <= stopY; column++) {
				caveModel.getRockCoordinates().add(new Coordinates(row, column));
			}
		}
	}

	private static Coordinates getCoordinates(String coordinate) {
		final int[] currentCoordinateValues = Arrays.stream(coordinate.split(",")).mapToInt(Integer::parseInt).toArray();
		return new Coordinates(currentCoordinateValues[0], currentCoordinateValues[1]);
	}

	private static void evaluateNewMinMaxValues(CaveModel caveModel, int startX, int stopX, int startY, int stopY) {
		if (startX < caveModel.getMinX()) {
			caveModel.setMinX(startX);
		}
		if (stopX > caveModel.getMaxX()) {
			caveModel.setMaxX(stopX);
		}
		if (startY < caveModel.getMinY()) {
			caveModel.setMinY(startY);
		}
		if (stopY > caveModel.getMaxY()) {
			caveModel.setMaxY(stopY);
		}
	}
}
