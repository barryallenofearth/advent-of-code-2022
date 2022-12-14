package de.barryallenofearth.adventofcode2022.riddle.day2.rockpaperscissors.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MatchModel {

	private final RockPaperScissorOption inputOpponent;

	private final RockPaperScissorOption inputOwn;

	private int scoreOpponent;

	private int scoreOwn;
}
