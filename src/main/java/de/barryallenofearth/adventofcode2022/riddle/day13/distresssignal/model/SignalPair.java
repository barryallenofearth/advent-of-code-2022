package de.barryallenofearth.adventofcode2022.riddle.day13.distresssignal.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SignalPair {

	private final int index;

	private SignalComponent first;

	private SignalComponent second;

	private String originalFirst;

	private String originalSecond;
}
