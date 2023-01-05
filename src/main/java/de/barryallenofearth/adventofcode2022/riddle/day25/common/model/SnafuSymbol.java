package de.barryallenofearth.adventofcode2022.riddle.day25.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SnafuSymbol {
	TWO(2, '2'),
	ONE(1, '1'),
	ZERO(0, '0'),
	MINUS_ONE(-1, '-'),
	MINUS_TWO(-2, '=');

	private final int value;

	private final char symbol;

	public static SnafuSymbol getBySymbol(char symbol) {
		for (SnafuSymbol snafuSymbol : SnafuSymbol.values()) {
			if (snafuSymbol.getSymbol() == symbol) {
				return snafuSymbol;
			}
		}
		throw new IllegalArgumentException("No Snafu symbol found for symbol=" + symbol);
	}

	public static SnafuSymbol getByValue(int value) {
		for (SnafuSymbol snafuSymbol : SnafuSymbol.values()) {
			if (snafuSymbol.getValue() == value) {
				return snafuSymbol;
			}
		}
		throw new IllegalArgumentException("No Snafu symbol found for value=" + value);
	}

}
