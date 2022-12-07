package de.barryallenofearth.adventofcode2022.riddle.g.files.part2.common;

import de.barryallenofearth.adventofcode2022.riddle.g.files.common.CalculateSizeInDirectory;
import de.barryallenofearth.adventofcode2022.riddle.g.files.model.Directory;

import java.util.List;

public class GetAllDirectoriesBelowSizeLimit {

    public static List<Directory> getMostOuterDirBelowSizeLimit(int sizeLimit, List<Directory> allDirectories, List<Directory> deletableDirectories) {

        for (Directory directory : allDirectories) {
            int size = CalculateSizeInDirectory.calculateSize(directory);
            if (size < sizeLimit && !isParentAlreadyDeleted(deletableDirectories, directory)) {
                deletableDirectories.add(directory);
            } else {
                getMostOuterDirBelowSizeLimit(sizeLimit, directory.getSubDirectories(), deletableDirectories);
            }
        }
        return deletableDirectories;

    }

    public static boolean isParentAlreadyDeleted(List<Directory> deletableDirectories, Directory directory) {
        return deletableDirectories.stream()
                .flatMap(currentDirectory -> currentDirectory.getSubDirectories().stream())
                .anyMatch(currentDirectory -> currentDirectory.equals(directory));

    }
}
