package de.barryallenofearth.adventofcode2022.riddle.day2.rockpaperscissors.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum MatchResultType {

	LOSS(0, Map.of(RockPaperScissorOption.ROCK, RockPaperScissorOption.SCISSOR,
			RockPaperScissorOption.PAPER, RockPaperScissorOption.ROCK,
			RockPaperScissorOption.SCISSOR, RockPaperScissorOption.PAPER), "X"),
	TIE(3, Map.of(RockPaperScissorOption.ROCK, RockPaperScissorOption.ROCK,
			RockPaperScissorOption.PAPER, RockPaperScissorOption.PAPER,
			RockPaperScissorOption.SCISSOR, RockPaperScissorOption.SCISSOR), "Y"),
	WIN(6, Map.of(RockPaperScissorOption.ROCK, RockPaperScissorOption.PAPER,
			RockPaperScissorOption.PAPER, RockPaperScissorOption.SCISSOR,
			RockPaperScissorOption.SCISSOR, RockPaperScissorOption.ROCK), "Z");

	private final int numberOfPoints;

	private final Map<RockPaperScissorOption, RockPaperScissorOption> relations;

	private final String encryptionKey;

	public static MatchResultType getMatchTypeResult(RockPaperScissorOption player1, RockPaperScissorOption player2) {
		for (MatchResultType value : MatchResultType.values()) {
			if (value.getRelations().get(player1) == player2) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid MatchResultType for option '" + player1 + "' and option '" + player2 + "'");
	}

	public static MatchResultType getByEncryptionKey(String key) {
		for (MatchResultType value : MatchResultType.values()) {
			if (value.getEncryptionKey().equals(key)) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid encryption key: " + key);
	}

}
