package de.barryallenofearth.adventofcode2022.riddle.g.files.part1.common;

import de.barryallenofearth.adventofcode2022.riddle.g.files.common.CalculateSizeInDirectory;
import de.barryallenofearth.adventofcode2022.riddle.g.files.model.Directory;

import java.util.List;

public class GetAllDirectoriesBelowSizeLimit {

    public static List<Directory> getMostOuterDirBelowSizeLimit(int sizeLimit, List<Directory> allDirectories, List<Directory> deletableDirectories) {

        for (Directory directory : allDirectories) {
            int size = CalculateSizeInDirectory.calculateSize(directory);
            if (size < sizeLimit) {
                deletableDirectories.add(directory);
                getMostOuterDirBelowSizeLimit(sizeLimit, directory.getSubDirectories(), deletableDirectories);
            } else {
                getMostOuterDirBelowSizeLimit(sizeLimit, directory.getSubDirectories(), deletableDirectories);
            }
        }
        return deletableDirectories;

    }

}
