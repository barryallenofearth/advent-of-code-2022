package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.part2;

import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.Direction;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.MapAndInstructions;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.MyPosition;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.util.AbstractPathwordFinder;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.part2.model.CubeBorder;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.part2.model.FoldedCoordinates;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.part2.model.FoldingCubeCorner;

import java.util.*;
import java.util.stream.Collectors;

public class CubePathWordFinder extends AbstractPathwordFinder {

	private static final int[][] X_AXIS_COUNTER_CLOCKWISE = { { 1, 0, 0 }, { 0, 0, -1 }, { 0, 1, 0 } };

	private static final int[][] X_AXIS_CLOCKWISE = { { 1, 0, 0 }, { 0, 0, 1 }, { 0, -1, 0 } };

	private static final int[][] Y_AXIS_COUNTER_CLOCKWISE = { { 0, 0, 1 }, { 0, 1, 0 }, { -1, 0, 0 } };

	private static final int[][] Y_AXIS_CLOCKWISE = { { 0, 0, -1 }, { 0, 1, 0 }, { 1, 0, 0 } };

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
		foldCube(cubeLength, boardElements);
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

	private List<FoldedCoordinates> foldCube(int cubeLength, List<Coordinates> boardElements) {
		final List<FoldedCoordinates> foldedCoordinateList = new ArrayList<>();

		final List<CubeBorder> borders = findBorders();
		final List<FoldingCubeCorner> foldingLines = findFoldingLines(borders, boardElements);

		final Map<Integer, List<FoldingCubeCorner>> facingIDWithBorders = foldingLines.stream().collect(Collectors.groupingBy(foldingCubeCorner -> foldingCubeCorner.getCubeBorder1().getFacingValue()));
		final List<FoldingCubeCorner> firstFacingFoldingLines = facingIDWithBorders.get(1);
		for (FoldingCubeCorner firstFacingFoldingLine : firstFacingFoldingLines) {
			final FoldedCoordinates startingCoordinates1 = firstFacingFoldingLine.getCubeBorder1().getCornerCoordinates().get(0);
			final FoldedCoordinates startingCoordinates2 = firstFacingFoldingLine.getCubeBorder2().getCornerCoordinates().get(0);
			final FoldedCoordinates stopCoordinates1 = firstFacingFoldingLine.getCubeBorder1().getCornerCoordinates().get(firstFacingFoldingLine.getCubeBorder1().getCornerCoordinates().size() - 1);
			final FoldedCoordinates stopCoordinates2 = firstFacingFoldingLine.getCubeBorder2().getCornerCoordinates().get(firstFacingFoldingLine.getCubeBorder2().getCornerCoordinates().size() - 1);

			final FoldedCoordinates startingMiddle = new FoldedCoordinates((startingCoordinates2.getX() - startingCoordinates1.getX()) / 2 + startingCoordinates1.getX(),
					(startingCoordinates2.getY() - startingCoordinates1.getY()) / 2 + startingCoordinates1.getY(),
					(startingCoordinates2.getZ() - startingCoordinates1.getZ()) / 2 + startingCoordinates1.getZ(), null);
			final FoldedCoordinates stopMiddle = new FoldedCoordinates((stopCoordinates2.getX() - stopCoordinates1.getX()) / 2 + stopCoordinates1.getX(),
					(stopCoordinates2.getY() - stopCoordinates1.getY()) / 2 + stopCoordinates1.getY(),
					(stopCoordinates2.getZ() - stopCoordinates1.getZ()) / 2 + stopCoordinates1.getZ(), null);

			float[] rotationAxis = { Math.abs(stopMiddle.getX() - startingMiddle.getX()), Math.abs(stopMiddle.getY() - startingMiddle.getY()), Math.abs(stopMiddle.getZ() - startingMiddle.getZ()) };
			System.out.println(firstFacingFoldingLine);
			System.out.println("(" + rotationAxis[0] + "," + rotationAxis[1] + "," + rotationAxis[2] + ")");
			System.out.println();
		}
		/*
			1) TODO find borders
			2) fold around border axis with the actual axis in the example being at height 0.5 at the beginning
			 not sure about the part
			3) consider axis shift: result = matrix * (original - offset) + offset
			offset means: matrix requires rotation around the x-Axis, shift border to be equal to x-Axis, then rotate,then shift back.
		 */
		return foldedCoordinateList;
	}

	private List<CubeBorder> findBorders() {
		final List<CubeBorder> cubeBorderList = new ArrayList<>();
		for (Map.Entry<Integer, List<Coordinates>> facing : facingElements.entrySet()) {
			final int minX, minY, maxX, maxY;
			final List<Coordinates> facingElements = facing.getValue();
			final Coordinates firstField = facingElements.get(0);
			minX = firstField.getX();
			minY = firstField.getY();
			final Coordinates lastField = facingElements.get(facingElements.size() - 1);
			maxX = lastField.getX();
			maxY = lastField.getY();
			final List<FoldedCoordinates> leftFacing = facingElements.stream()
					.filter(coordinates -> coordinates.getX() == minX)
					.map(coordinates -> new FoldedCoordinates(coordinates.getX(), coordinates.getY(), 1, coordinates))
					.collect(Collectors.toList());
			cubeBorderList.add(new CubeBorder(facing.getKey(), Direction.LEFT, leftFacing));
			final List<FoldedCoordinates> rightFacing = facingElements.stream()
					.filter(coordinates -> coordinates.getX() == maxX)
					.map(coordinates -> new FoldedCoordinates(coordinates.getX(), coordinates.getY(), 1, coordinates))
					.collect(Collectors.toList());
			cubeBorderList.add(new CubeBorder(facing.getKey(), Direction.RIGHT, rightFacing));
			final List<FoldedCoordinates> upFacing = facingElements.stream()
					.filter(coordinates -> coordinates.getY() == minY)
					.map(coordinates -> new FoldedCoordinates(coordinates.getX(), coordinates.getY(), 1, coordinates))
					.collect(Collectors.toList());
			cubeBorderList.add(new CubeBorder(facing.getKey(), Direction.UP, upFacing));
			final List<FoldedCoordinates> downFacing = facingElements.stream()
					.filter(coordinates -> coordinates.getY() == maxY)
					.map(coordinates -> new FoldedCoordinates(coordinates.getX(), coordinates.getY(), 1, coordinates))
					.collect(Collectors.toList());
			cubeBorderList.add(new CubeBorder(facing.getKey(), Direction.DOWN, downFacing));

			System.out.println("min (" + minX + "," + minY + ") and max (" + maxX + "," + maxY + ")");
		}

		return cubeBorderList;
	}

	private List<FoldingCubeCorner> findFoldingLines(List<CubeBorder> borders, List<Coordinates> boardElements) {

		final List<FoldingCubeCorner> foldingCubeCorners = borders.stream().filter(border -> {
			// 1 leads to a unique new position, get(0) leads to a corner, which is part of multiple borders
			final Coordinates coordinates = stepOutsideFacing(border);
			//if step to leave is still on board => corner that requires folding
			return boardElements.contains(coordinates);
		}).map(border -> {
			final CubeBorder partnerBorder = borders.stream().filter(otherBorder -> {
				final Coordinates coordinates = stepOutsideFacing(border);
				return otherBorder.getCornerCoordinates().contains(new FoldedCoordinates(coordinates.getX(), coordinates.getY(), 1, coordinates));
			}).findFirst().get();
			return new FoldingCubeCorner(border, partnerBorder);
		}).collect(Collectors.toList());

		foldingCubeCorners.forEach(foldingCubeCorner -> System.out
				.println(foldingCubeCorner.getCubeBorder1().getFacingValue() + " " + foldingCubeCorner.getCubeBorder1().getDirectionToLeave() + "->" + foldingCubeCorner.getCubeBorder2().getFacingValue() + " " + foldingCubeCorner.getCubeBorder2()
						.getDirectionToLeave()));
		return foldingCubeCorners;
	}

	private Coordinates stepOutsideFacing(CubeBorder border) {
		final FoldedCoordinates startingCoordinates = border.getCornerCoordinates().get(1);
		final Coordinates coordinates = new Coordinates(((int) startingCoordinates.getX()), (int) startingCoordinates.getY());
		border.getDirectionToLeave().getMove().accept(coordinates);
		return coordinates;
	}

	@Override protected boolean checkAndHandleWrappingAround(MapAndInstructions mapAndInstructions, MyPosition myPosition) {
		return false;
	}
}
