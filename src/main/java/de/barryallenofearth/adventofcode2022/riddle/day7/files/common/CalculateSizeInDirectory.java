package de.barryallenofearth.adventofcode2022.riddle.day7.files.common;

import de.barryallenofearth.adventofcode2022.riddle.day7.files.model.Directory;
import de.barryallenofearth.adventofcode2022.riddle.day7.files.model.File;

public class CalculateSizeInDirectory {

    public static int calculateSize(Directory directory) {
        int directorySize = 0;
        for (File file : directory.getFiles()) {
            directorySize += file.getSize();
        }
        for (Directory subDirectory : directory.getSubDirectories()) {
            directorySize += calculateSize(subDirectory);
        }

        return directorySize;
    }

}
