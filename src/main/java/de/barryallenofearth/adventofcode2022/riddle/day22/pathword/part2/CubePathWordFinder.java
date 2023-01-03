package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.part2;

import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.Direction;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.MapAndInstructions;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.MyPosition;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.util.AbstractPathwordFinder;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.part2.model.*;

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
			final RotationProperties rotationProperties = getRotationProperties(firstFacingFoldingLine);
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
					.map(this::getFoldedCoordinates)
					.collect(Collectors.toList());
			cubeBorderList.add(new CubeBorder(facing.getKey(), Direction.LEFT, leftFacing));
			final List<FoldedCoordinates> rightFacing = facingElements.stream()
					.filter(coordinates -> coordinates.getX() == maxX)
					.map(this::getFoldedCoordinates)
					.collect(Collectors.toList());
			cubeBorderList.add(new CubeBorder(facing.getKey(), Direction.RIGHT, rightFacing));
			final List<FoldedCoordinates> upFacing = facingElements.stream()
					.filter(coordinates -> coordinates.getY() == minY)
					.map(this::getFoldedCoordinates)
					.collect(Collectors.toList());
			cubeBorderList.add(new CubeBorder(facing.getKey(), Direction.UP, upFacing));
			final List<FoldedCoordinates> downFacing = facingElements.stream()
					.filter(coordinates -> coordinates.getY() == maxY)
					.map(this::getFoldedCoordinates)
					.collect(Collectors.toList());
			cubeBorderList.add(new CubeBorder(facing.getKey(), Direction.DOWN, downFacing));

			System.out.println("min (" + minX + "," + minY + ") and max (" + maxX + "," + maxY + ")");
		}

		return cubeBorderList;
	}

	private FoldedCoordinates getFoldedCoordinates(Coordinates coordinates) {
		return new FoldedCoordinates(new Coordinates3D(coordinates.getX(), coordinates.getY(), 1), coordinates);
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
				return otherBorder.getCornerCoordinates().contains(getFoldedCoordinates(coordinates));
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
		final Coordinates coordinates = new Coordinates(((int) startingCoordinates.getFoldedCoordinates().getX()), (int) startingCoordinates.getFoldedCoordinates().getY());
		border.getDirectionToLeave().getMove().accept(coordinates);
		return coordinates;
	}

	private RotationProperties getRotationProperties(FoldingCubeCorner foldingCubeCorner) {
		final FoldedCoordinates startingCoordinates1 = foldingCubeCorner.getCubeBorder1().getCornerCoordinates().get(0);
		final FoldedCoordinates startingCoordinates2 = foldingCubeCorner.getCubeBorder2().getCornerCoordinates().get(0);
		final FoldedCoordinates stopCoordinates1 = foldingCubeCorner.getCubeBorder1().getCornerCoordinates().get(foldingCubeCorner.getCubeBorder1().getCornerCoordinates().size() - 1);
		final FoldedCoordinates stopCoordinates2 = foldingCubeCorner.getCubeBorder2().getCornerCoordinates().get(foldingCubeCorner.getCubeBorder2().getCornerCoordinates().size() - 1);

		final Coordinates3D startingMiddle = new Coordinates3D((startingCoordinates2.getFoldedCoordinates().getX() - startingCoordinates1.getFoldedCoordinates().getX()) / 2 + startingCoordinates1.getFoldedCoordinates().getX(),
				(startingCoordinates2.getFoldedCoordinates().getY() - startingCoordinates1.getFoldedCoordinates().getY()) / 2 + startingCoordinates1.getFoldedCoordinates().getY(),
				(startingCoordinates2.getFoldedCoordinates().getZ() - startingCoordinates1.getFoldedCoordinates().getZ()) / 2 + startingCoordinates1.getFoldedCoordinates().getZ());
		final Coordinates3D stopMiddle = new Coordinates3D((stopCoordinates2.getFoldedCoordinates().getX() - stopCoordinates1.getFoldedCoordinates().getX()) / 2 + stopCoordinates1.getFoldedCoordinates().getX(),
				(stopCoordinates2.getFoldedCoordinates().getY() - stopCoordinates1.getFoldedCoordinates().getY()) / 2 + stopCoordinates1.getFoldedCoordinates().getY(),
				(stopCoordinates2.getFoldedCoordinates().getZ() - stopCoordinates1.getFoldedCoordinates().getZ()) / 2 + stopCoordinates1.getFoldedCoordinates().getZ());

		float[] rotationAxis = { Math.abs(stopMiddle.getX() - startingMiddle.getX()), Math.abs(stopMiddle.getY() - startingMiddle.getY()), Math.abs(stopMiddle.getZ() - startingMiddle.getZ()) };
		int[][] rotationMatrix = null;
		Coordinates3D shiftToAxisVector = null;
		if (rotationAxis[0] > 0) {
			if (foldingCubeCorner.getCubeBorder1().getDirectionToLeave() == Direction.UP) {
				rotationMatrix = X_AXIS_CLOCKWISE;
			} else if (foldingCubeCorner.getCubeBorder1().getDirectionToLeave() == Direction.DOWN) {
				rotationMatrix = X_AXIS_COUNTER_CLOCKWISE;
			} else {
				System.out.println();
			}
			shiftToAxisVector = new Coordinates3D(0, -startingMiddle.getY(), -startingMiddle.getZ());
		} else if (rotationAxis[1] > 0) {
			if (foldingCubeCorner.getCubeBorder1().getDirectionToLeave() == Direction.LEFT) {
				rotationMatrix = Y_AXIS_CLOCKWISE;
			} else if (foldingCubeCorner.getCubeBorder1().getDirectionToLeave() == Direction.RIGHT) {
				rotationMatrix = Y_AXIS_COUNTER_CLOCKWISE;
			} else {
				System.out.println();
			}
			shiftToAxisVector = new Coordinates3D(-startingMiddle.getX(), 0, -startingMiddle.getZ());
		}
		if (shiftToAxisVector == null) {
			throw new IllegalStateException("A shift to axis vector needs to be defined");
		}
		if (rotationMatrix == null) {
			throw new IllegalStateException("A rotation matrix needs to be defined");
		}
		return new RotationProperties(shiftToAxisVector, rotationMatrix);
	}

	public void rotateCoordinates(List<FoldedCoordinates> foldedCoordinates, RotationProperties rotationProperties) {

		for (FoldedCoordinates foldedCoordinate : foldedCoordinates) {
			final float xOriginal = foldedCoordinate.getFoldedCoordinates().getX();
			final float yOriginal = foldedCoordinate.getFoldedCoordinates().getY();
			final float zOriginal = foldedCoordinate.getFoldedCoordinates().getZ();

			final float xShifted = foldedCoordinate.getFoldedCoordinates().getX() + rotationProperties.getShiftToAxisVector().getX();
			final float yShifted = foldedCoordinate.getFoldedCoordinates().getY() + rotationProperties.getShiftToAxisVector().getY();
			final float zShifted = foldedCoordinate.getFoldedCoordinates().getZ() + rotationProperties.getShiftToAxisVector().getZ();

			final int[] firstRotationRow = rotationProperties.getRotationMatrix()[0];
			final int[] secondRotationRow = rotationProperties.getRotationMatrix()[1];
			final int[] thirdRotationRow = rotationProperties.getRotationMatrix()[2];

			foldedCoordinate.getFoldedCoordinates().setX(firstRotationRow[0] * xShifted + firstRotationRow[1] * yShifted + firstRotationRow[2] * zShifted - rotationProperties.getShiftToAxisVector().getX());
			foldedCoordinate.getFoldedCoordinates().setY(secondRotationRow[0] * xShifted + secondRotationRow[1] * yShifted + secondRotationRow[2] * zShifted - rotationProperties.getShiftToAxisVector().getY());
			foldedCoordinate.getFoldedCoordinates().setZ(thirdRotationRow[0] * xShifted + thirdRotationRow[1] * yShifted + thirdRotationRow[2] * zShifted - rotationProperties.getShiftToAxisVector().getZ());
		}
	}

	@Override protected boolean checkAndHandleWrappingAround(MapAndInstructions mapAndInstructions, MyPosition myPosition) {
		return false;
	}
}
