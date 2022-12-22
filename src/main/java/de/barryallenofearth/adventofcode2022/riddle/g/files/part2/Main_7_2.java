package de.barryallenofearth.adventofcode2022.riddle.g.files.part2;

import de.barryallenofearth.adventofcode2022.riddle.g.files.common.CalculateSizeInDirectory;
import de.barryallenofearth.adventofcode2022.riddle.g.files.common.ReadDirectoryTree;
import de.barryallenofearth.adventofcode2022.riddle.g.files.model.Directory;
import de.barryallenofearth.adventofcode2022.riddle.g.files.part2.common.FindSmallestDirectoryWithSufficientSize;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main_7_2 {
    public static void main(String[] args) {
        final List<Directory> directoryList = ReadDirectoryTree.readDirectoryTree();
        final Directory directory = FindSmallestDirectoryWithSufficientSize.findDirectory(directoryList);

        System.out.println("A total size of " + CalculateSizeInDirectory.calculateSize(directory) + " can be deleted.");
    }
}
