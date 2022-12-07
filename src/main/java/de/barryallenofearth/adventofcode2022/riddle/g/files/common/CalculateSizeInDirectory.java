package de.barryallenofearth.adventofcode2022.riddle.g.files.common;

import de.barryallenofearth.adventofcode2022.riddle.g.files.model.Directory;
import de.barryallenofearth.adventofcode2022.riddle.g.files.model.File;

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
