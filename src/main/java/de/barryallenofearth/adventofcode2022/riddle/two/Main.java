package de.barryallenofearth.adventofcode2022.riddle.two;

import de.barryallenofearth.adventofcode2022.riddle.two.model.MatchModel;
import de.barryallenofearth.adventofcode2022.riddle.two.service.MatchResultService;
import de.barryallenofearth.adventofcode2022.riddle.two.service.StrategyReaderService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Answer to riddle:
 * https://adventofcode.com/2022/day/2
 *
 */
public class Main {

	public static void main(String[] args) throws IOException, URISyntaxException {

		boolean isPartOne = false;
		final List<MatchModel> matchModels = StrategyReaderService.readMatchesFromStrategyPaper("/riddle-input/riddle-2.txt", isPartOne);

		int totalPoints = 0;
		for (MatchModel matchModel : matchModels) {
			MatchResultService.calculatePlayerScore(matchModel);
			totalPoints += matchModel.getScoreOwn();
		}
		System.out.println("You scored " + totalPoints + " points in " + matchModels.size() + " games.");

	}
}
