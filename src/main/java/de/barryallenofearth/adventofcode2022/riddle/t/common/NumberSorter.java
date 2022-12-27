package de.barryallenofearth.adventofcode2022.riddle.t.common;

import de.barryallenofearth.adventofcode2022.riddle.t.common.model.EntryWithId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NumberSorter {

	public static List<Integer> sortNumbers(List<EntryWithId> sequence) {
		int numberOfEntries = sequence.size();

		for (int index = 0; index < numberOfEntries; index++) {
			printSequence(sequence);
			final EntryWithId entry = getById(sequence, index);
			System.out.println(entry);

			if (entry.getValue() >= 0) {
				shiftPositiveValue(sequence, numberOfEntries, sequence.indexOf(entry), entry);
			} else {
				shiftNegativeValue(sequence, numberOfEntries, sequence.indexOf(entry), entry);
			}
		}
		printSequence(sequence);
		return sequence.stream().map(EntryWithId::getValue).collect(Collectors.toList());
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
		if (Math.abs(entry.getValue()) >= index) {
			int value = Math.abs(entry.getValue()) - (index - 1);
			sequence.add(numberOfEntries - value, entry);
		} else {
			sequence.add(index + entry.getValue(), entry);
		}
	}

	static void shiftPositiveValue(List<EntryWithId> sequence, int numberOfEntries, int index, EntryWithId entry) {
		sequence.remove(index);
		if (index + entry.getValue() >= numberOfEntries - 1) {
			sequence.add((index + entry.getValue()) % (numberOfEntries - 1), entry);
		} else {
			sequence.add(index + entry.getValue(), entry);
		}
	}
}
