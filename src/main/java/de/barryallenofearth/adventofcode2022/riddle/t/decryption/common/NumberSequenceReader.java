package de.barryallenofearth.adventofcode2022.riddle.t.decryption.common;

import de.barryallenofearth.adventofcode2022.riddle.t.decryption.common.model.EntryWithId;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.util.ArrayList;
import java.util.List;

public class NumberSequenceReader {

	public static List<EntryWithId> read() {
		final List<String> strings = RiddleFileReader.readAllLines("riddle-20.txt");
		final List<EntryWithId> numbers = new ArrayList<>();
		for (int index = 0, stringsSize = strings.size(); index < stringsSize; index++) {
			String string = strings.get(index);
			numbers.add(new EntryWithId(index, Integer.parseInt(string)));
		}

		return numbers;
	}
}
