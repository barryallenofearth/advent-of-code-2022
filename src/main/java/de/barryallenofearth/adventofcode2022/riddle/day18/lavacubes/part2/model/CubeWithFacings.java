package de.barryallenofearth.adventofcode2022.riddle.day18.lavacubes.part2.model;

import de.barryallenofearth.adventofcode2022.riddle.day18.lavacubes.common.model.Cube;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@RequiredArgsConstructor
public class CubeWithFacings {
	private final Cube cube;

	private final List<Facing> facings = new ArrayList<>() {{
		this.addAll(Arrays.asList(Facing.values()));
	}};
}
