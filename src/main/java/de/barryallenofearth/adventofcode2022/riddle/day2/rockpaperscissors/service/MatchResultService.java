package de.barryallenofearth.adventofcode2022.riddle.day2.rockpaperscissors.service;

import de.barryallenofearth.adventofcode2022.riddle.day2.rockpaperscissors.model.MatchModel;
import de.barryallenofearth.adventofcode2022.riddle.day2.rockpaperscissors.model.MatchResultType;
import de.barryallenofearth.adventofcode2022.riddle.day2.rockpaperscissors.model.RockPaperScissorOption;

public class MatchResultService {

	/**
	 * fill match model with scores for each player from the current match
	 * @param matchModel
	 */
	public static void calculatePlayerScore(MatchModel matchModel) {
		final RockPaperScissorOption optionPlayer1 = matchModel.getInputOpponent();
		final RockPaperScissorOption optionPlayer2 = matchModel.getInputOwn();

		int scorePlayer1 = optionPlayer1.getBaseScore();
		int scorePlayer2 = optionPlayer2.getBaseScore();

		//tied result
		MatchResultType resultPlayer1 = MatchResultType.getMatchTypeResult(optionPlayer2, optionPlayer1);
		MatchResultType resultPlayer2 = MatchResultType.getMatchTypeResult(optionPlayer1, optionPlayer2);

		scorePlayer1 += resultPlayer1.getNumberOfPoints();
		scorePlayer2 += resultPlayer2.getNumberOfPoints();

		matchModel.setScoreOpponent(scorePlayer1);
		matchModel.setScoreOwn(scorePlayer2);
	}

}
