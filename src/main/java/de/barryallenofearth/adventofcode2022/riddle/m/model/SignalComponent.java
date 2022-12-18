package de.barryallenofearth.adventofcode2022.riddle.m.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class SignalComponent {

	public SignalComponent() {
		uuid = UUID.randomUUID().toString();
	}

	public SignalComponent(Type type) {
		this();
		this.type = type;
	}

	private String originalString;

	private List<SignalComponent> components = new ArrayList<>();

	private Integer integer;

	private Type type = Type.LIST;

	private final String uuid;

	public enum Type {
		INTEGER, LIST
	}
}
