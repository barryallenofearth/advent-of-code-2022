package de.barryallenofearth.adventofcode2022.riddle.day20.decryption.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntryWithId {

	public EntryWithId(int id, int value) {
		this.id = id;
		this.value = value;
		this.originalValue = value;
	}

	//original index
	private final int id;

	private int value;

	private int originalValue;

}
