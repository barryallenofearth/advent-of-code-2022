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

	private static final int[][] Z_AXIS_COUNTER_CLOCKWISE = { { 0, 1, 0 }, { -1, 0, 0 }, { 0, 0, 1 } };

	private static final int[][] Z_AXIS_CLOCKWISE = { { 0, -1, 0 }, { 1, 0, 0 }, { 0, 0, 1 } };

	private final Map<Integer, List<FoldedCoordinates>> facingElements = new HashMap<>();

	private final Map<FacingAndDirection, FacingAndDirection> whereToNext = new HashMap<>();

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
		printOriginalFacings(maxX, maxY);
		final List<CubeBorder> cubeBorderList = foldCubeAndReturnBorders(boardElements);

		findAdjacentFields(cubeBorderList);
	}

	private void findAdjacentFields(List<CubeBorder> cubeBorderList) {
		Map<CubeBorder, CubeBorder> closestNeighbors = determineClosestNeighborBorders(cubeBorderList);

		for (Map.Entry<CubeBorder, CubeBorder> neighborBorders : closestNeighbors.entrySet()) {
			final CubeBorder border1 = neighborBorders.getKey();
			final CubeBorder border2 = neighborBorders.getValue();
			for (FoldedCoordinates current : border1.getCornerCoordinates()) {
				float minDistance = Float.MAX_VALUE;
				for (FoldedCoordinates comparison : border2.getCornerCoordinates()) {
					if (current.equals(comparison)) {
						continue;
					}
					final Coordinates3D currentCoordinates3D = current.getFoldedCoordinates();
					final Coordinates3D comparisonCoordinates3D = comparison.getFoldedCoordinates();
					float distance = Math.abs(currentCoordinates3D.getX() - comparisonCoordinates3D.getX()) + Math.abs(currentCoordinates3D.getY() - comparisonCoordinates3D.getY()) + Math
							.abs(currentCoordinates3D.getZ() - comparisonCoordinates3D.getZ());
					if (distance < minDistance) {
						whereToNext.put(new FacingAndDirection(border1.getFacingID(), border1.getDirectionToLeave(), current.getOriginalCoordinates()),
								new FacingAndDirection(border2.getFacingID(), border2.getDirectionToLeave(), comparison.getOriginalCoordinates()));

						minDistance = distance;
					}
				}
			}
		}
	}

	private Map<CubeBorder, CubeBorder> determineClosestNeighborBorders(List<CubeBorder> cubeBorderList) {
		Map<CubeBorder, CubeBorder> closestNeighbors = new HashMap<>();
		int middleIndex = cubeBorderList.get(0).getCornerCoordinates().size() / 2;
		for (CubeBorder cubeBorder : cubeBorderList) {
			float minDistance = Float.MAX_VALUE;
			for (CubeBorder comparisonBorder : cubeBorderList) {
				if (comparisonBorder.getFacingID() == cubeBorder.getFacingID()) {
					continue;
				}
				final Coordinates3D current = cubeBorder.getCornerCoordinates().get(middleIndex).getFoldedCoordinates();
				final Coordinates3D comparison = comparisonBorder.getCornerCoordinates().get(middleIndex).getFoldedCoordinates();
				float distance = Math.abs(current.getX() - comparison.getX()) + Math.abs(current.getY() - comparison.getY()) + Math.abs(current.getZ() - comparison.getZ());
				if (distance < minDistance) {
					closestNeighbors.put(cubeBorder, comparisonBorder);
					minDistance = distance;
				}
			}
		}
		return closestNeighbors;
	}

	private void printOriginalFacings(int maxX, int maxY) {
		for (int y = 1; y <= maxY; y++) {
			for (int x = 1; x <= maxX; x++) {
				final Coordinates coordinates = new Coordinates(x, y);
				final Optional<Map.Entry<Integer, List<FoldedCoordinates>>> foundEntry = facingElements.entrySet().stream()
						.filter(entry -> entry.getValue().stream().anyMatch(foldedCoordinates -> foldedCoordinates.getOriginalCoordinates().equals(coordinates))).findFirst();
				if (foundEntry.isEmpty()) {
					System.out.print(" ");
				} else {
					final Map.Entry<Integer, List<FoldedCoordinates>> entry = foundEntry.get();
					System.out.print(entry.getKey());
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
					final List<FoldedCoordinates> currentList = new ArrayList<>();
					facingElements.put(currentFacing, currentList);
					for (int y = yCube; y < yCube + cubeLength; y++) {
						for (int x = xCube; x < xCube + cubeLength; x++) {
							final Coordinates coordinates1 = new Coordinates(x, y);
							currentList.add(getFoldedCoordinates(coordinates1));
						}
					}
				}
			}
		}
	}

	private List<CubeBorder> foldCubeAndReturnBorders(List<Coordinates> boardElements) {

		final List<CubeBorder> borders = findBorders();
		final List<FoldingLine> foldingLines = findFoldingLines(borders, boardElements);

		final Map<Integer, List<FoldingLine>> facingIDWithFoldingLines = foldingLines.stream().collect(Collectors.groupingBy(foldingLine -> foldingLine.getCubeBorder1().getFacingID()));
		foldAllLinesAndSublinesOfFacing(foldingLines, facingIDWithFoldingLines, 1);
		return borders;
	}

	private void foldAllLinesAndSublinesOfFacing(List<FoldingLine> foldingLines, Map<Integer, List<FoldingLine>> facingIDWithFoldingLines, int facingId) {
		final List<FoldingLine> startingSide = facingIDWithFoldingLines.get(facingId);
		for (FoldingLine foldingLine : startingSide) {
			//tracking of all folding lines happens in the complete list for simplicity
			if (!foldingLines.contains(foldingLine)) {
				continue;
			}

			//rotate
			final RotationProperties rotationProperties = getRotationProperties(foldingLine);
			rotateCoordinates(getConnectedFacings(foldingLine, foldingLines), rotationProperties);

			removeUsedFoldingLines(foldingLines, foldingLine);

			foldAllLinesAndSublinesOfFacing(foldingLines, facingIDWithFoldingLines, foldingLine.getCubeBorder2().getFacingID());
		}
	}

	private void removeUsedFoldingLines(List<FoldingLine> foldingLines, FoldingLine foldingLine) {
		//remove already rotated lines and their opposites
		foldingLines.remove(foldingLine);
		removeOppsosite(foldingLines, foldingLine);
	}

	private List<CubeBorder> findBorders() {
		final List<CubeBorder> cubeBorderList = new ArrayList<>();
		for (Map.Entry<Integer, List<FoldedCoordinates>> facing : facingElements.entrySet()) {
			final int minX, minY, maxX, maxY;
			final List<FoldedCoordinates> facingElements = facing.getValue();
			final FoldedCoordinates firstField = facingElements.get(0);
			minX = firstField.getOriginalCoordinates().getX();
			minY = firstField.getOriginalCoordinates().getY();
			final FoldedCoordinates lastField = facingElements.get(facingElements.size() - 1);
			maxX = lastField.getOriginalCoordinates().getX();
			maxY = lastField.getOriginalCoordinates().getY();
			final List<FoldedCoordinates> leftFacing = facingElements.stream()
					.filter(coordinates -> coordinates.getOriginalCoordinates().getX() == minX)
					.collect(Collectors.toList());
			Collections.reverse(leftFacing);
			cubeBorderList.add(new CubeBorder(facing.getKey(), Direction.LEFT, leftFacing));

			final List<FoldedCoordinates> rightFacing = facingElements.stream()
					.filter(coordinates -> coordinates.getOriginalCoordinates().getX() == maxX)
					.collect(Collectors.toList());
			cubeBorderList.add(new CubeBorder(facing.getKey(), Direction.RIGHT, rightFacing));

			final List<FoldedCoordinates> upFacing = facingElements.stream()
					.filter(coordinates -> coordinates.getOriginalCoordinates().getY() == minY)
					.collect(Collectors.toList());
			cubeBorderList.add(new CubeBorder(facing.getKey(), Direction.UP, upFacing));

			final List<FoldedCoordinates> downFacing = facingElements.stream()
					.filter(coordinates -> coordinates.getOriginalCoordinates().getY() == maxY)
					.collect(Collectors.toList());
			Collections.reverse(downFacing);
			cubeBorderList.add(new CubeBorder(facing.getKey(), Direction.DOWN, downFacing));

		}

		return cubeBorderList;
	}

	private FoldedCoordinates getFoldedCoordinates(Coordinates coordinates) {
		return new FoldedCoordinates(new Coordinates3D(coordinates.getX(), coordinates.getY(), 1), coordinates);
	}

	private List<FoldingLine> findFoldingLines(List<CubeBorder> borders, List<Coordinates> boardElements) {

		final List<FoldingLine> foldingLines = borders.stream().filter(border -> {
			// 1 leads to a unique new position, get(0) leads to a corner, which is part of multiple borders
			final Coordinates coordinates = stepOutsideFacing(border);
			//if step to leave is still on board => corner that requires folding
			return boardElements.contains(coordinates);
		}).map(border -> {
			final CubeBorder partnerBorder = borders.stream().filter(otherBorder -> {
				final Coordinates coordinates = stepOutsideFacing(border);
				return otherBorder.getCornerCoordinates().contains(getFoldedCoordinates(coordinates));
			}).findFirst().get();
			return new FoldingLine(border, partnerBorder);
		}).collect(Collectors.toList());

		return foldingLines;
	}

	private Coordinates stepOutsideFacing(CubeBorder border) {
		final FoldedCoordinates startingCoordinates = border.getCornerCoordinates().get(1);
		final Coordinates coordinates = new Coordinates(((int) startingCoordinates.getFoldedCoordinates().getX()), (int) startingCoordinates.getFoldedCoordinates().getY());
		border.getDirectionToLeave().getMove().accept(coordinates);
		return coordinates;
	}

	private RotationProperties getRotationProperties(FoldingLine foldingLine) {
		final FoldedCoordinates startingCoordinates1 = foldingLine.getCubeBorder1().getCornerCoordinates().get(0);
		final FoldedCoordinates startingCoordinates2 = foldingLine.getCubeBorder2().getCornerCoordinates().get(foldingLine.getCubeBorder2().getCornerCoordinates().size() - 1);
		final FoldedCoordinates stopCoordinates1 = foldingLine.getCubeBorder1().getCornerCoordinates().get(foldingLine.getCubeBorder1().getCornerCoordinates().size() - 1);
		final FoldedCoordinates stopCoordinates2 = foldingLine.getCubeBorder2().getCornerCoordinates().get(0);

		final Coordinates3D startingMiddle = new Coordinates3D((startingCoordinates2.getFoldedCoordinates().getX() - startingCoordinates1.getFoldedCoordinates().getX()) / 2 + startingCoordinates1.getFoldedCoordinates().getX(),
				(startingCoordinates2.getFoldedCoordinates().getY() - startingCoordinates1.getFoldedCoordinates().getY()) / 2 + startingCoordinates1.getFoldedCoordinates().getY(),
				(startingCoordinates2.getFoldedCoordinates().getZ() - startingCoordinates1.getFoldedCoordinates().getZ()) / 2 + startingCoordinates1.getFoldedCoordinates().getZ());
		final Coordinates3D stopMiddle = new Coordinates3D((stopCoordinates2.getFoldedCoordinates().getX() - stopCoordinates1.getFoldedCoordinates().getX()) / 2 + stopCoordinates1.getFoldedCoordinates().getX(),
				(stopCoordinates2.getFoldedCoordinates().getY() - stopCoordinates1.getFoldedCoordinates().getY()) / 2 + stopCoordinates1.getFoldedCoordinates().getY(),
				(stopCoordinates2.getFoldedCoordinates().getZ() - stopCoordinates1.getFoldedCoordinates().getZ()) / 2 + stopCoordinates1.getFoldedCoordinates().getZ());

		float[] rotationAxis = { stopMiddle.getX() - startingMiddle.getX(), stopMiddle.getY() - startingMiddle.getY(), stopMiddle.getZ() - startingMiddle.getZ() };
		int[][] rotationMatrix = null;
		Coordinates3D shiftToAxisVector = null;
		if (Math.abs(rotationAxis[0]) > 0) {
			if (rotationAxis[0] > 0) {
				rotationMatrix = X_AXIS_CLOCKWISE;
			} else {
				rotationMatrix = X_AXIS_COUNTER_CLOCKWISE;
			}
			shiftToAxisVector = new Coordinates3D(0, -startingMiddle.getY(), -startingMiddle.getZ());
		} else if (Math.abs(rotationAxis[1]) > 0) {
			if (rotationAxis[1] > 0) {
				rotationMatrix = Y_AXIS_CLOCKWISE;
			} else {
				rotationMatrix = Y_AXIS_COUNTER_CLOCKWISE;
			}
			shiftToAxisVector = new Coordinates3D(-startingMiddle.getX(), 0, -startingMiddle.getZ());
		} else if (Math.abs(rotationAxis[2]) > 0) {
			if (rotationAxis[2] > 0) {
				rotationMatrix = Z_AXIS_COUNTER_CLOCKWISE;
			} else {
				rotationMatrix = Z_AXIS_CLOCKWISE;
			}
			shiftToAxisVector = new Coordinates3D(-startingMiddle.getX(), -startingMiddle.getY(), 0);
		}
		if (shiftToAxisVector == null) {
			throw new IllegalStateException("A shift to axis vector needs to be defined. rotation axis is: x=" + rotationAxis[0] + " y=" + rotationAxis[1] + " z=" + rotationAxis[2]);
		}
		if (rotationMatrix == null) {
			throw new IllegalStateException("A rotation matrix needs to be defined");
		}
		return new RotationProperties(shiftToAxisVector, rotationMatrix);
	}

	private void rotateCoordinates(List<FoldedCoordinates> foldedCoordinates, RotationProperties rotationProperties) {

		for (FoldedCoordinates foldedCoordinate : foldedCoordinates) {

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

	private List<FoldedCoordinates> getConnectedFacings(FoldingLine foldingLine, List<FoldingLine> allRemainingFoldingLines) {
		removeOppsosite(allRemainingFoldingLines, foldingLine);

		List<FoldedCoordinates> allCoordinates = new ArrayList<>(facingElements.get(foldingLine.getCubeBorder2().getFacingID()));

		final List<FoldingLine> foldingLinesConnectedToCurrentFacing = allRemainingFoldingLines.stream()
				.filter(line -> line.getCubeBorder1().getFacingID() == foldingLine.getCubeBorder2().getFacingID())
				.collect(Collectors.toList());

		for (FoldingLine line : foldingLinesConnectedToCurrentFacing) {
			allCoordinates.addAll(getConnectedFacings(line, allRemainingFoldingLines));
		}

		return allCoordinates;
	}

	private void removeOppsosite(List<FoldingLine> allRemainingFoldingLines, FoldingLine nextLine) {
		final Optional<FoldingLine> oppositeLine = allRemainingFoldingLines.stream()
				.filter(matchFoldingLine -> matchFoldingLine.getCubeBorder2().equals(nextLine.getCubeBorder1()) && matchFoldingLine.getCubeBorder1().equals(nextLine.getCubeBorder2()))
				.findFirst();
		oppositeLine.ifPresent(allRemainingFoldingLines::remove);
	}

	@Override protected boolean checkAndHandleWrappingAround(MapAndInstructions mapAndInstructions, MyPosition myPosition) {
		myPosition.getDirection().getReverse().accept(myPosition.getCoordinates());
		Optional<Integer> facingID = facingElements.entrySet().stream()
				.filter(entry -> entry.getValue().stream()
						.anyMatch(foldedCoordinates -> foldedCoordinates.getOriginalCoordinates().equals(myPosition.getCoordinates())))
				.map(Map.Entry::getKey)
				.findFirst();

		if (facingID.isEmpty()) {
			return true;
		}

		final FacingAndDirection next = whereToNext.get(new FacingAndDirection(facingID.get(), myPosition.getDirection(), myPosition.getCoordinates()));
		if (next != null) {
			if (mapAndInstructions.getBlockedCoordinates().contains(next.getCoordinates())) {
				return true;
			}
			myPosition.getCoordinates().setX(next.getCoordinates().getX());
			myPosition.getCoordinates().setY(next.getCoordinates().getY());
			if (next.getDirection() == Direction.UP) {
				myPosition.setDirection(Direction.DOWN);
			} else if (next.getDirection() == Direction.DOWN) {
				myPosition.setDirection(Direction.UP);
			} else if (next.getDirection() == Direction.LEFT) {
				myPosition.setDirection(Direction.RIGHT);
			} else if (next.getDirection() == Direction.RIGHT) {
				myPosition.setDirection(Direction.LEFT);
			}
			return false;
		}

		return true;
	}
}
