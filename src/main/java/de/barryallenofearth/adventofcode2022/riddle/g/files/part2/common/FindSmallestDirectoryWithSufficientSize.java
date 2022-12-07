package de.barryallenofearth.adventofcode2022.riddle.g.files.part2.common;

import de.barryallenofearth.adventofcode2022.riddle.g.files.common.CalculateSizeInDirectory;
import de.barryallenofearth.adventofcode2022.riddle.g.files.model.Directory;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FindSmallestDirectoryWithSufficientSize {

    public static final int TOTAL_SPACE = 70_000_000;
    public static final int REQUIRED_SPACE = 30_000_000;

    public static Directory findDirectory(List<Directory> allDirectories) {
        int totalOccupiedSpace = 0;
        for (Directory directory : allDirectories) {
            totalOccupiedSpace += CalculateSizeInDirectory.calculateSize(directory);
        }

        final int availableSpace = TOTAL_SPACE - totalOccupiedSpace;
        final Set<Directory> allDirsAsList = flatMapDirs(new HashSet<>(), allDirectories);

        final int fileSizeNeededToBeDeleted = REQUIRED_SPACE - availableSpace;

        final List<Directory> directoriesWithSufficientSize = allDirsAsList.stream()
                .filter(directory -> fileSizeNeededToBeDeleted <= CalculateSizeInDirectory.calculateSize(directory))
                .sorted(Comparator.comparing(CalculateSizeInDirectory::calculateSize))
                .collect(Collectors.toList());

        return directoriesWithSufficientSize.get(0);
    }

    private static Set<Directory> flatMapDirs(Set<Directory> flatDirectories, List<Directory> newSubdirectories) {

        for (Directory directory : newSubdirectories) {
            flatDirectories.add(directory);
            for (Directory subDirectory : directory.getSubDirectories()) {
                flatDirectories.add(subDirectory);
                flatMapDirs(flatDirectories, subDirectory.getSubDirectories());
            }
        }
        return flatDirectories;
    }

}
