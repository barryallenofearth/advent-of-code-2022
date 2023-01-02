package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.part2;

import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.MapAndInstructions;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.MyPosition;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.util.AbstrctPathwordFinder;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.part2.model.FoldedCoordinates;

import java.util.*;

public class CubePathWordFinder extends AbstrctPathwordFinder {

	private static final int[][] X_AXIS_COUNTER_CLOCKWISE = { { 1, 0, 0 }, { 0, 0, -1 }, { 0, 1, 0 } };

	private static final int[][] X_AXIS_CLOCKWISE = { { 1, 0, 0 }, { 0, 0, 1 }, { 0, -1, 0 } };

	private static final int[][] Y_AXIS_COUNTER_CLOCKWISE = { { 0, 0, 1 }, { 0, 1, 0 }, { -1, 0, 0 } };

	private static final int[][] Y_AXIS_CLOCKWISE = { { 0, 0, -1 }, { 0, 1, 0 }, { 1, 0, 0 } };

	private static final int[][] Z_AXIS_COUNTER_CLOCKWISE = { { 0, -1, 0 }, { 1, 0, 0 }, { 0, 0, 1 } };

	private static final int[][] Z_AXIS_CLOCKWISE = { { 0, 1, 0 }, { -1, 0, 0 }, { 0, 0, 1 } };

	private final Map<Integer, List<Coordinates>> facingElements = new HashMap<>();

	public void determineFacings(List<Coordinates> boardElements) {
		int maxX = 0;
		int maxY = 0;
		for (Coordinates boardCoordinate : boardElements) {
			if (boardCoordinate.getX() > maxX) {
				maxX = boardCoordinate.getX();
			}
			if (boardCoordinate.getY() > maxY) {
				maxY = boardCoordinate.getY();
			}
		}

		//4 for the example map, 50 for complete map
		final int cubeLength = maxX > 50 ? 50 : 4;

		determineCubeFacings(boardElements, maxX, maxY, cubeLength);

		for (int y = 1; y <= maxY; y++) {
			for (int x = 1; x <= maxX; x++) {
				final Coordinates coordinates = new Coordinates(x, y);
				final Optional<Map.Entry<Integer, List<Coordinates>>> foundEntry = facingElements.entrySet().stream().filter(entry -> entry.getValue().contains(coordinates)).findFirst();
				if (foundEntry.isEmpty()) {
					System.out.print(" ");
				} else {
					final Map.Entry<Integer, List<Coordinates>> collect = foundEntry.get();
					System.out.print(collect.getKey());
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	private void determineCubeFacings(List<Coordinates> boardElements, int maxX, int maxY, int cubeLength) {
		int currentFacing = 0;
		for (int yCube = 1; yCube <= maxY; yCube += cubeLength) {
			for (int xCube = 1; xCube <= maxX; xCube += cubeLength) {
				final Coordinates coordinates = new Coordinates(xCube, yCube);
				if (boardElements.contains(coordinates)) {
					currentFacing++;
					final List<Coordinates> currentList = new ArrayList<>();
					facingElements.put(currentFacing, currentList);
					for (int y = yCube; y < yCube + cubeLength; y++) {
						for (int x = xCube; x < xCube + cubeLength; x++) {
							currentList.add(new Coordinates(x, y));
						}
					}
				}
			}
		}
	}

	private List<FoldedCoordinates> foldCube(int cubeLength) {
		final List<FoldedCoordinates> foldedCoordinateList = new ArrayList<>();

		/*
			1) TODO find borders
			2) fold around border axis with the actual axis in the example being at height 0.5 at the beginning
			 not sure about the part
			3) consider axis shift: result = matrix * (original - offset) + offset
			offset means: matrix requires rotation around the x-Axis, shift border to be equal to x-Axis, then rotate,then shift back.
		 */
		for (Map.Entry<Integer, List<Coordinates>> facing : facingElements.entrySet()) {
			switch (facing.getKey()) {
			case 1:
				for (List<Coordinates> coordinates : facingElements.values()) {

				}
				break;
			}
		}
		return foldedCoordinateList;
	}

	@Override protected boolean checkAndHandleWrappingAround(MapAndInstructions mapAndInstructions, MyPosition myPosition) {
		return false;
	}
}
