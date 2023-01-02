package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.part2;

import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.MapAndInstructions;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.MyPosition;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.util.AbstrctPathwordFinder;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

public class CubePathWordFinder extends AbstrctPathwordFinder {

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

		int currentFacing = 0;
		for (int yCube = 1; yCube <= maxY; yCube += 50) {
			for (int xCube = 1; xCube <= maxX; xCube += 50) {
				final Coordinates coordinates = new Coordinates(xCube, yCube);
				if (boardElements.contains(coordinates)) {
					currentFacing++;
					final List<Coordinates> currentList = new ArrayList<>();
					facingElements.put(currentFacing, currentList);
					for (int y = yCube; y < yCube + 50; y++) {
						for (int x = xCube; x < xCube + 50; x++) {
							currentList.add(new Coordinates(x, y));
						}
					}
				}
			}
		}

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

	@Override protected boolean checkAndHandleWrappingAround(MapAndInstructions mapAndInstructions, MyPosition myPosition) {
		return false;
	}
}
