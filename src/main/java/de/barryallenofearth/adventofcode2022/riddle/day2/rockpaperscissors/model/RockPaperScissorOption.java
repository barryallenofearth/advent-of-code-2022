package de.barryallenofearth.adventofcode2022.riddle.day2.rockpaperscissors.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum RockPaperScissorOption {
	ROCK(1, List.of("A", "X")),
	PAPER(2, List.of("B", "Y")),
	SCISSOR(3, List.of("C", "Z"));

	private final int baseScore;

	private final List<String> strategyCode;

	public static RockPaperScissorOption getByCode(String code) {
		for (RockPaperScissorOption value : RockPaperScissorOption.values()) {
			if (value.getStrategyCode().contains(code)) {
				return value;
			}
		}
		throw new IllegalArgumentException("No valid code found for code: " + code);
	}

}
