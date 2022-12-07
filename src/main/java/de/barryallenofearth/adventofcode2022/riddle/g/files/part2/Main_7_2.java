package de.barryallenofearth.adventofcode2022.riddle.g.files.part2;

import de.barryallenofearth.adventofcode2022.riddle.g.files.common.CalculateSizeInDirectory;
import de.barryallenofearth.adventofcode2022.riddle.g.files.common.ReadDirectoryTree;
import de.barryallenofearth.adventofcode2022.riddle.g.files.model.Directory;
import de.barryallenofearth.adventofcode2022.riddle.g.files.part2.common.GetAllDirectoriesBelowSizeLimit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Main_7_2 {
    public static void main(String[] args) throws IOException, URISyntaxException {
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
