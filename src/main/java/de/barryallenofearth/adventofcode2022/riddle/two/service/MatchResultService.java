package de.barryallenofearth.adventofcode2022.riddle.two.service;

import de.barryallenofearth.adventofcode2022.riddle.two.model.MatchModel;
import de.barryallenofearth.adventofcode2022.riddle.two.model.RockPaperScissorOption;

public class MatchResultService {

	public static final int LOSS_SCORE = 0;

	public static final int TIE_SCORE = 3;

	public static final int WIN_SCORE = 6;

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
		if (optionPlayer1 == optionPlayer2) {
			scorePlayer1 += TIE_SCORE;
			scorePlayer2 += TIE_SCORE;
		}
		//player 1 has won this round
		else if ((optionPlayer1 == RockPaperScissorOption.ROCK && optionPlayer2 == RockPaperScissorOption.SCISSOR)
				|| (optionPlayer1 == RockPaperScissorOption.SCISSOR && optionPlayer2 == RockPaperScissorOption.PAPER)
				|| (optionPlayer1 == RockPaperScissorOption.PAPER && optionPlayer2 == RockPaperScissorOption.ROCK)) {
			scorePlayer1 += WIN_SCORE;
			scorePlayer2 += LOSS_SCORE;
		}
		//in any other scenario player 2 wins.
		else {
			scorePlayer1 += LOSS_SCORE;
			scorePlayer2 += WIN_SCORE;
		}
		matchModel.setScoreOpponent(scorePlayer1);
		matchModel.setScoreOwn(scorePlayer2);
	}

}
