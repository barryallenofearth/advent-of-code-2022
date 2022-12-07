package de.barryallenofearth.adventofcode2022.riddle.g.files.common;

import de.barryallenofearth.adventofcode2022.riddle.g.files.model.Directory;
import de.barryallenofearth.adventofcode2022.riddle.g.files.model.File;
import de.barryallenofearth.adventofcode2022.riddle.util.RiddleFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadDirectoryTree {

    public static final Pattern CD_PATTERN = Pattern.compile("^\\$ cd (.+)$");
    public static final Pattern DIR_PATTERN = Pattern.compile("^dir (.+)$");
    public static final Pattern FILE_PATTERN = Pattern.compile("^(\\d+) (.+)$");

    public static List<Directory> readDirectoryTree() throws IOException, URISyntaxException {
        final List<String> strings = RiddleFileReader.readAllLines("riddle-7.txt");
        List<Directory> directoryList = new ArrayList<>();

        final Stack<Directory> currentTree = new Stack<>();
        final Directory root = new Directory("/");
        directoryList.add(root);
        currentTree.push(root);

        for (String string : strings) {
            if (string.equals("$ cd /")) {
                continue;
            }

            final Matcher cdMatcher = CD_PATTERN.matcher(string);
            if (cdMatcher.matches()) {
                handleCDInstruction(currentTree, cdMatcher);
            }
            final Matcher dirMatcher = DIR_PATTERN.matcher(string);
            if (dirMatcher.matches()) {
                currentTree.peek().getSubDirectories().add(new Directory(dirMatcher.group(1)));
            }

            final Matcher fileMatcher = FILE_PATTERN.matcher(string);
            if (fileMatcher.matches()) {
                final int fileSize = Integer.parseInt(fileMatcher.group(1));
                final String fileName = fileMatcher.group(2);
                currentTree.peek().getFiles().add(new File(fileName, fileSize));
            }

        }
        return directoryList;
    }

    private static void handleCDInstruction(Stack<Directory> currentTree, Matcher cdMatcher) {
        final String cdInstruction = cdMatcher.group(1);
        if (cdInstruction.equals("..")) {
            currentTree.pop();
        } else {
            final Directory directoryFromSubdirectories = getDirectoryFromSubdirectories(currentTree.peek(), cdInstruction);
            currentTree.push(directoryFromSubdirectories);
        }
    }

    private static Directory getDirectoryFromSubdirectories(Directory currentDirectory, String nameOfSubdirectory) {
        for (Directory subDirectory : currentDirectory.getSubDirectories()) {
            if (subDirectory.getName().equals(nameOfSubdirectory)) {
                return subDirectory;
            }
        }
        throw new IllegalArgumentException("Subdirectory " + nameOfSubdirectory + " not found in current directory: " + currentDirectory);
    }
}
