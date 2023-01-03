package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.util;

import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.*;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPathwordFinder {

	public MyPosition findFinalPosition(MapAndInstructions mapAndInstructions) {
		MyPosition myPosition = new MyPosition();
		final Coordinates initialCoordinates = mapAndInstructions.getBoardCoordinates().get(0);
		myPosition.setCoordinates(new Coordinates(initialCoordinates.getX(), initialCoordinates.getY()));
		Map<Coordinates, String> touchedFieldsVsOrienatation = new HashMap<>();
		putMyPosition(myPosition, touchedFieldsVsOrienatation);

		for (Instruction instruction : mapAndInstructions.getInstructions()) {
			if (instruction.getInstructionType() == InstructionType.ROTATE) {
				rotate(myPosition, touchedFieldsVsOrienatation, instruction);
			} else if (instruction.getInstructionType() == InstructionType.MOVE_FORWARD) {
				move(mapAndInstructions, myPosition, touchedFieldsVsOrienatation, instruction);
			}
		}
		printPath(mapAndInstructions, touchedFieldsVsOrienatation);

		return myPosition;
	}

	private void printPath(MapAndInstructions mapAndInstructions, Map<Coordinates, String> touchedFieldsVsOrienatation) {
		int maxX = 0;
		int maxY = 0;
		for (Coordinates boardCoordinate : mapAndInstructions.getBoardCoordinates()) {
			if (boardCoordinate.getX() > maxX) {
				maxX = boardCoordinate.getX();
			}
			if (boardCoordinate.getY() > maxY) {
				maxY = boardCoordinate.getY();
			}
		}

		for (int y = 1; y <= maxY; y++) {
			for (int x = 1; x <= maxX; x++) {
				final Coordinates coordinates = new Coordinates(x, y);
				final String orientation = touchedFieldsVsOrienatation.get(coordinates);
				if (orientation != null) {
					System.out.print(orientation);
				} else if (mapAndInstructions.getBlockedCoordinates().contains(coordinates)) {
					System.out.print("#");
				} else if (mapAndInstructions.getBoardCoordinates().contains(coordinates)) {
					System.out.print(".");
				} else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	private void rotate(MyPosition myPosition, Map<Coordinates, String> touchedFieldsVsOrienatation, Instruction instruction) {
		final Direction directionAfterRotation = instruction.getRotation().getRotate().apply(myPosition.getDirection());
		myPosition.setDirection(directionAfterRotation);
		putMyPosition(myPosition, touchedFieldsVsOrienatation);
	}

	private void move(MapAndInstructions mapAndInstructions, MyPosition myPosition, Map<Coordinates, String> touchedFieldsVsOrienatation, Instruction instruction) {
		for (int step = 0; step < instruction.getNumberOfSteps(); step++) {
			myPosition.getDirection().getMove().accept(myPosition.getCoordinates());
			if (mapAndInstructions.getBlockedCoordinates().contains(myPosition.getCoordinates())) {
				myPosition.getDirection().getReverse().accept(myPosition.getCoordinates());
				break;
			} else if (!mapAndInstructions.getBoardCoordinates().contains(myPosition.getCoordinates())) {
				if (checkAndHandleWrappingAround(mapAndInstructions, myPosition)) {
					break;
				}
			}
			putMyPosition(myPosition, touchedFieldsVsOrienatation);
		}
	}

	protected abstract boolean checkAndHandleWrappingAround(MapAndInstructions mapAndInstructions, MyPosition myPosition);

	private void putMyPosition(MyPosition myPosition, Map<Coordinates, String> map) {
		map.put(new Coordinates(myPosition.getCoordinates().getX(), myPosition.getCoordinates().getY()), myPosition.getDirection().getSymbol());
	}
}
