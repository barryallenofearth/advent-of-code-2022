package de.barryallenofearth.adventofcode2022.riddle.day2.rockpaperscissors;

import de.barryallenofearth.adventofcode2022.riddle.day2.rockpaperscissors.model.MatchModel;
import de.barryallenofearth.adventofcode2022.riddle.day2.rockpaperscissors.service.MatchResultService;
import de.barryallenofearth.adventofcode2022.riddle.day2.rockpaperscissors.service.StrategyReaderService;

import java.util.List;

/**
 * Answer to riddle:
 * https://adventofcode.com/2022/day/2
 *
 */
public class Main {

	public static void main(String[] args) {

		boolean isPartOne = false;
		final List<MatchModel> matchModels = StrategyReaderService.readMatchesFromStrategyPaper("riddle-2.txt", isPartOne);

		int totalPoints = 0;
		for (MatchModel matchModel : matchModels) {
			MatchResultService.calculatePlayerScore(matchModel);
			totalPoints += matchModel.getScoreOwn();
		}
		System.out.println("You scored " + totalPoints + " points in " + matchModels.size() + " games.");

	}
}
