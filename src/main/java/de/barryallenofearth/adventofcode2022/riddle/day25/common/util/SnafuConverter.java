package de.barryallenofearth.adventofcode2022.riddle.day25.common.util;

import de.barryallenofearth.adventofcode2022.riddle.day25.common.model.SnafuSymbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SnafuConverter {

	public static final long BASE_VALUE = 5;

	public static final int MAX_SNAFU_DIGIT_VALUE = Arrays.stream(SnafuSymbol.values())
			.mapToInt(SnafuSymbol::getValue)
			.max()
			.getAsInt();

	public static final int MIN_SNAFU_DIGIT_VALUE = Arrays.stream(SnafuSymbol.values())
			.mapToInt(SnafuSymbol::getValue)
			.min()
			.getAsInt();

	public List<Long> convertToDecimal(List<String> snafuNumbers) {
		final List<Long> decimal = new ArrayList<>();

		for (String snafuNumber : snafuNumbers) {
			decimal.add(convertToDecimal(snafuNumber));
		}
		return decimal;
	}

	public long convertToDecimal(String snafuNumber) {
		long decimal = 0;

		final int maxIndex = snafuNumber.length() - 1;
		for (int index = maxIndex; index >= 0; index--) {
			final SnafuSymbol snafuSymbol = SnafuSymbol.getBySymbol(snafuNumber.charAt(index));
			decimal += Math.pow(BASE_VALUE, maxIndex - index) * snafuSymbol.getValue();
		}
		return decimal;
	}

	public String convertToSnafuNumber(long decimal) {
		String snafuNumber = "";
		int maximumPotency = determineMaximumPotency(decimal);

		final String lowestSnafuNumber = buildLowestSnafuNumber(maximumPotency);
		System.out.println(lowestSnafuNumber);

		snafuNumber = lowestSnafuNumber;
		for (int index = 0; index < lowestSnafuNumber.length(); index++) {
			for (SnafuSymbol snafuSymbol : SnafuSymbol.values()) {
				final StringBuilder testSnafuNumber = new StringBuilder(snafuNumber);
				testSnafuNumber.setCharAt(index, snafuSymbol.getSymbol());
				snafuNumber = testSnafuNumber.toString();
				if (convertToDecimal(snafuNumber) <= decimal) {
					break;
				}
			}
			if (convertToDecimal(snafuNumber) == decimal) {
				break;
			}
		}

		return snafuNumber;
	}

	private String buildLowestSnafuNumber(int maximumPotency) {
		final StringBuilder lowestSnafuNumberBuilder = new StringBuilder();
		final SnafuSymbol snafuSymbol = SnafuSymbol.getByValue(MIN_SNAFU_DIGIT_VALUE);
		for (int count = 0; count < maximumPotency; count++) {
			lowestSnafuNumberBuilder.append(snafuSymbol.getSymbol());
		}
		final String lowestSnafuNumber = lowestSnafuNumberBuilder.toString();
		return lowestSnafuNumber;
	}

	private int determineMaximumPotency(long decimal) {
		StringBuilder snafuNumber = new StringBuilder();
		int maximumPotency = 0;
		do {
			snafuNumber.append(MAX_SNAFU_DIGIT_VALUE);
			maximumPotency++;
		} while (decimal / convertToDecimal(snafuNumber.toString()) > 1);
		return maximumPotency;
	}
}
