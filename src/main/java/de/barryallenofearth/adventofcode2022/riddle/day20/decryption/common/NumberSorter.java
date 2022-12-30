package de.barryallenofearth.adventofcode2022.riddle.day20.decryption.common;

import de.barryallenofearth.adventofcode2022.riddle.day20.decryption.common.model.EntryWithId;

import java.util.List;
import java.util.stream.Collectors;

public class NumberSorter {

	public static List<Integer> sortNumbers(List<EntryWithId> sequence) {
		int numberOfEntries = sequence.size();

		for (int index = 0; index < numberOfEntries; index++) {
			final EntryWithId entry = getById(sequence, index);

			if (entry.getValue() >= 0) {
				shiftPositiveValue(sequence, numberOfEntries, sequence.indexOf(entry), entry);
			} else {
				shiftNegativeValue(sequence, numberOfEntries, sequence.indexOf(entry), entry);
			}
		}
		printSequence(sequence);
		return sequence.stream().map(EntryWithId::getOriginalValue).collect(Collectors.toList());
	}

	static void printSequence(List<EntryWithId> sequence) {
		System.out.println(sequence.stream().map(entryWithId -> String.valueOf(entryWithId.getValue())).collect(Collectors.joining(", ")));
	}

	private static EntryWithId getById(List<EntryWithId> entryWithIds, int id) {
		for (EntryWithId entryWithId : entryWithIds) {
			if (entryWithId.getId() == id) {
				return entryWithId;
			}
		}
		throw new IllegalArgumentException("Entry with id=" + id + " could not be found in sequence");
	}

	static void shiftNegativeValue(List<EntryWithId> sequence, int numberOfEntries, int index, EntryWithId entry) {
		//negative number is shifted below 0
		sequence.remove(index);
		final int entryValue = entry.getValue() % (numberOfEntries - 1);
		if (Math.abs(entryValue) >= index) {
			int value = Math.abs(entryValue) - (index - 1);
			sequence.add(numberOfEntries - value, entry);
		} else {
			sequence.add(index + entryValue, entry);
		}
	}

	static void shiftPositiveValue(List<EntryWithId> sequence, int numberOfEntries, int index, EntryWithId entry) {
		sequence.remove(index);
		final int value = entry.getValue() % (numberOfEntries - 1);
		if (index + value >= numberOfEntries - 1) {
			sequence.add((index + value) % (numberOfEntries - 1), entry);
		} else {
			sequence.add(index + value, entry);
		}
	}
}
