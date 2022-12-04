package de.barryallenofearth.adventofcode2022.riddle.two.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static de.barryallenofearth.adventofcode2022.riddle.two.model.RockPaperScissorOption.*;

@Getter
@RequiredArgsConstructor
public enum MatchResultType {

	LOSS(0, Map.of(ROCK, SCISSOR,
			PAPER, ROCK,
			SCISSOR, PAPER), "X"),
	TIE(3, Map.of(ROCK, ROCK,
			PAPER, PAPER,
			SCISSOR, SCISSOR), "Y"),
	WIN(6, Map.of(ROCK, PAPER,
			PAPER, SCISSOR,
			SCISSOR, ROCK), "Z");

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
