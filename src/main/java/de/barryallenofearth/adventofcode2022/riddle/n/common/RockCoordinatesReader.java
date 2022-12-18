package de.barryallenofearth.adventofcode2022.riddle.n.common;

import de.barryallenofearth.adventofcode2022.riddle.n.model.CaveModel;
import de.barryallenofearth.adventofcode2022.riddle.n.model.Coordinates;
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
				final int[] start = Arrays.stream(coordinates[0].split(",")).mapToInt(Integer::parseInt).toArray();
				final Coordinates startCoordinates = new Coordinates(start[0], start[1]);

				final int[] stop = Arrays.stream(coordinates[1].split(",")).mapToInt(Integer::parseInt).toArray();
				final Coordinates stopCoordinates = new Coordinates(stop[0], stop[1]);
				int startX = Math.min(startCoordinates.getRow(), stopCoordinates.getRow());
				int stopX = Math.max(startCoordinates.getRow(), stopCoordinates.getRow());
				int startY = Math.min(startCoordinates.getColumn(), stopCoordinates.getColumn());
				int stopY = Math.max(startCoordinates.getColumn(), stopCoordinates.getColumn());
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
				for (int row = startX; row <= stopX; row++) {
					for (int column = startY; column <= stopY; column++) {
						caveModel.getRockCoordinates().add(new Coordinates(row, column));
					}
				}
			}
		}

		return caveModel;
	}
}
