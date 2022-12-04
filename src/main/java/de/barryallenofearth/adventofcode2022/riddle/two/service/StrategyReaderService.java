package de.barryallenofearth.adventofcode2022.riddle.two.service;

import de.barryallenofearth.adventofcode2022.riddle.two.model.MatchModel;
import de.barryallenofearth.adventofcode2022.riddle.two.model.RockPaperScissorOption;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StrategyReaderService {

	/**
	 * Read in file contains the options chosen by the opponent (first column) and you (second column)
	 * @param fileName path to actual file relative to classpath
	 * @return MatchModels containing the chosen options by your opponent and you
	 */
	public static List<MatchModel> readMatchesFromStrategyPaper(String fileName) throws URISyntaxException, IOException {
		final List<String> allStrategyEntries = FileUtils.readLines(new File(StrategyReaderService.class.getResource(fileName).toURI()), StandardCharsets.UTF_8);

		List<MatchModel> matchModels = new ArrayList<>();

		for (String strategyEntry : allStrategyEntries) {
			if (!strategyEntry.matches("\\w\\s+\\w")) {
				continue;
			}
			final String[] options = strategyEntry.split("\\s+");
			final RockPaperScissorOption opponent = RockPaperScissorOption.getByCode(options[0]);
			final RockPaperScissorOption own = RockPaperScissorOption.getByCode(options[1]);
			matchModels.add(new MatchModel(opponent, own));
		}

		return matchModels;
	}
}