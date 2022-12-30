package de.barryallenofearth.adventofcode2022.riddle.day7.files.part1;

import de.barryallenofearth.adventofcode2022.riddle.day7.files.common.CalculateSizeInDirectory;
import de.barryallenofearth.adventofcode2022.riddle.day7.files.common.ReadDirectoryTree;
import de.barryallenofearth.adventofcode2022.riddle.day7.files.model.Directory;
import de.barryallenofearth.adventofcode2022.riddle.day7.files.part1.common.GetAllDirectoriesBelowSizeLimit;

import java.util.ArrayList;
import java.util.List;

public class Main_7_1 {
    public static void main(String[] args) {
        final List<Directory> directoryList = ReadDirectoryTree.readDirectoryTree();
        final List<Directory> mostOuterDirBelowSizeLimit = GetAllDirectoriesBelowSizeLimit.getMostOuterDirBelowSizeLimit(100_000, directoryList, new ArrayList<>());


        int totalSize = 0;
        for (Directory directory : mostOuterDirBelowSizeLimit) {
            final int size = CalculateSizeInDirectory.calculateSize(directory);
            totalSize += size;
        }

        System.out.println("A total size of " + totalSize + " can be deleted.");
    }
}
