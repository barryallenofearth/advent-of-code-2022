package de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.part2;

import de.barryallenofearth.adventofcode2022.riddle.day21.monkeys.common.model.Monkey;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EvaluatedAndOpenMonkey {
	private final Monkey evaluated;

	private final Monkey open;

}
