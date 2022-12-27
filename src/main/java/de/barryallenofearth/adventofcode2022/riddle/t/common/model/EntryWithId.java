package de.barryallenofearth.adventofcode2022.riddle.t.common.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EntryWithId {
	//original index
	private final int id;

	private final int value;

}
