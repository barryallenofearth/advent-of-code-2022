package de.barryallenofearth.adventofcode2022.riddle.day22.pathword.part1;

import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.Coordinates;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.MapAndInstructions;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.model.MyPosition;
import de.barryallenofearth.adventofcode2022.riddle.day22.pathword.common.util.AbstractPathwordFinder;

public class PathwordFinder extends AbstractPathwordFinder {

	@Override
	protected boolean checkAndHandleWrappingAround(MapAndInstructions mapAndInstructions, MyPosition myPosition) {
		myPosition.getDirection().getReverse().accept(myPosition.getCoordinates());
		Coordinates startingCoordinates = new Coordinates(myPosition.getCoordinates().getX(), myPosition.getCoordinates().getY());
		do {
			myPosition.getDirection().getReverse().accept(myPosition.getCoordinates());
		} while (mapAndInstructions.getBoardCoordinates().contains(myPosition.getCoordinates()));
		myPosition.getDirection().getMove().accept(myPosition.getCoordinates());
		if (mapAndInstructions.getBlockedCoordinates().contains(myPosition.getCoordinates())) {
			myPosition.setCoordinates(startingCoordinates);

			return true;
		}
		return false;
	}

}
