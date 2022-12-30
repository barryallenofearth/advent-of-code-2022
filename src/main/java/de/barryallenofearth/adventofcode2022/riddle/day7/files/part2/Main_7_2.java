package de.barryallenofearth.adventofcode2022.riddle.day7.files.part2;

import de.barryallenofearth.adventofcode2022.riddle.day7.files.common.CalculateSizeInDirectory;
import de.barryallenofearth.adventofcode2022.riddle.day7.files.common.ReadDirectoryTree;
import de.barryallenofearth.adventofcode2022.riddle.day7.files.model.Directory;
import de.barryallenofearth.adventofcode2022.riddle.day7.files.part2.common.FindSmallestDirectoryWithSufficientSize;

import java.util.List;

public class Main_7_2 {
    public static void main(String[] args) {
        final List<Directory> directoryList = ReadDirectoryTree.readDirectoryTree();
        final Directory directory = FindSmallestDirectoryWithSufficientSize.findDirectory(directoryList);

        System.out.println("A total size of " + CalculateSizeInDirectory.calculateSize(directory) + " can be deleted.");
    }
}
