package de.barryallenofearth.adventofcode2022.riddle.day2.rockpaperscissors.service;

import de.barryallenofearth.adventofcode2022.riddle.day2.rockpaperscissors.model.MatchModel;
import de.barryallenofearth.adventofcode2022.riddle.day2.rockpaperscissors.model.MatchResultType;
import de.barryallenofearth.adventofcode2022.riddle.day2.rockpaperscissors.model.RockPaperScissorOption;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.util.ArrayList;
import java.util.List;

public class StrategyReaderService {

	/**
	 * Read in file contains the options chosen by the opponent (first column) and you (second column)
	 * @param fileName path to actual file relative to classpath
	 * @param isPartOne
	 * @return MatchModels containing the chosen options by your opponent and you
	 */
	public static List<MatchModel> readMatchesFromStrategyPaper(String fileName, boolean isPartOne) {
		final List<String> allStrategyEntries = RiddleFileReader.readAllLines(fileName);

		List<MatchModel> matchModels = new ArrayList<>();

		for (String strategyEntry : allStrategyEntries) {
			if (!strategyEntry.matches("\\w\\s+\\w")) {
				continue;
			}
			final String[] options = strategyEntry.split("\\s+");
			final RockPaperScissorOption opponent = RockPaperScissorOption.getByCode(options[0]);
			final RockPaperScissorOption own;
			if (isPartOne) {
				own = RockPaperScissorOption.getByCode(options[1]);
			} else {
				MatchResultType byEncryptionKey = MatchResultType.getByEncryptionKey(options[1]);
				own = byEncryptionKey.getRelations().get(opponent);
			}
			matchModels.add(new MatchModel(opponent, own));
		}

		return matchModels;
	}
}
