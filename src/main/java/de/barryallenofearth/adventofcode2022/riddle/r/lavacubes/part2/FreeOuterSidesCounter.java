package de.barryallenofearth.adventofcode2022.riddle.r.lavacubes.part2;

import de.barryallenofearth.adventofcode2022.riddle.r.lavacubes.common.model.Cube;
import de.barryallenofearth.adventofcode2022.riddle.r.lavacubes.part2.model.BubbleModel;
import de.barryallenofearth.adventofcode2022.riddle.r.lavacubes.part2.model.CubeWithFacings;
import de.barryallenofearth.adventofcode2022.riddle.r.lavacubes.part2.model.Facing;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FreeOuterSidesCounter {
    private static int xMin = 5000, xMax = 0;
    private static int yMin = 5000, yMax = 0;
    private static int zMin = 5000, zMax = 0;

    public static long countFreeOuterSide(List<CubeWithFacings> cubes) {
        final List<Cube> allCubes = cubes.stream().map(CubeWithFacings::getCube).collect(Collectors.toList());
        determineExtrema(allCubes);
        final List<Cube> bubbleCubes = determineBubbleCubes(allCubes);
        System.out.println(bubbleCubes.size() + " cubes found in a bubble.");
        System.out.println(bubbleCubes);
        for (CubeWithFacings currentCube : cubes) {
            Stream.concat(cubes.stream().map(CubeWithFacings::getCube), bubbleCubes.stream())
                    .forEach(cubeWithFacing -> {
                        for (int index = currentCube.getFacings().size() - 1; index >= 0; index--) {
                            final Facing facing = currentCube.getFacings().get(index);
                            if (facing.getIsInContact().test(currentCube.getCube(), cubeWithFacing)) {
                                currentCube.getFacings().remove(facing);
                            }
                        }
                    });
        }

        printCube(cubes, bubbleCubes);
        return cubes.stream().flatMap(cubeWithFacings -> cubeWithFacings.getFacings().stream()).filter(Objects::nonNull).count();
    }

    private static List<Cube> determineBubbleCubes(List<Cube> allCubes) {
        //only direct neighbors of cubes need to be starting points

        final List<Cube> allBubbleCubes = new ArrayList<>();
        final List<Cube> allNonBubbleCubes = new ArrayList<>();
        final List<Cube> closedCubes = new ArrayList<>();

        for (Cube cube : allCubes) {

            List<Cube> emptyCubes = new ArrayList<>();
            try {
                //all cubes are relevant here so not actual non bubble list but empty list
                emptyCubes = getAllEmptyNeighbors(cube, allCubes, closedCubes, List.of());
            } catch (CubeIsNonBubble e) {
                e.printStackTrace();
            }
            for (Cube emptyStartingCube : emptyCubes) {
                final BubbleModel bubbleModel = findBubble(allCubes, closedCubes, allNonBubbleCubes, emptyStartingCube);
                if (bubbleModel.isBubble()) {
                    addToList(bubbleModel, allBubbleCubes);
                } else {
                    addToList(bubbleModel, allNonBubbleCubes);
                }
            }

        }
        return allBubbleCubes;
    }

    private static void addToList(BubbleModel bubbleModel, List<Cube> allNonBubbleCubes) {
        for (Cube bubbleCube : bubbleModel.getBubbleCubes()) {
            if (!allNonBubbleCubes.contains(bubbleCube)) {
                allNonBubbleCubes.add(bubbleCube);
            }
        }
    }

    private static BubbleModel findBubble(List<Cube> allCubes, List<Cube> closedCubes, List<Cube> nonBubbleNodes, Cube startingCube) {
        Queue<Cube> startingQueue = new LinkedList<>();
        startingQueue.add(startingCube);
        final Queue<Queue<Cube>> openNodes = new LinkedList<>();
        openNodes.add(startingQueue);
        final BubbleModel bubbleModel = new BubbleModel();
        while (!openNodes.isEmpty()) {
            final Queue<Cube> currentQueue = openNodes.poll();
            final Queue<Cube> soonQueue = new LinkedList<>();

            for (int index = 0; index < currentQueue.size(); index++) {
                final Cube openCube = currentQueue.poll();
                bubbleModel.getBubbleCubes().add(openCube);
                closedCubes.add(openCube);
                if (openCube.getX() < xMin || openCube.getX() > xMax
                        || openCube.getY() < yMin || openCube.getY() > yMax
                        || openCube.getZ() < zMin || openCube.getZ() > zMax || nonBubbleNodes.contains(openCube)) {
                    bubbleModel.setBubble(false);
                    return bubbleModel;
                }
                try {
                    soonQueue.addAll(getAllEmptyNeighbors(openCube, allCubes, closedCubes, nonBubbleNodes));
                } catch (CubeIsNonBubble e) {
                    bubbleModel.setBubble(false);
                    return bubbleModel;
                }
            }
            if (!soonQueue.isEmpty()) {
                openNodes.add(soonQueue);
            }
        }
        return bubbleModel;
    }

    private static void determineExtrema(List<Cube> allCubes) {
        for (Cube cube : allCubes) {
            final int x = cube.getX();
            final int y = cube.getY();
            final int z = cube.getZ();
            xMin = Math.min(xMin, x);
            xMax = Math.max(xMax, x);
            yMin = Math.min(yMin, y);
            yMax = Math.max(yMax, y);
            zMin = Math.min(zMin, z);
            zMax = Math.max(zMax, z);
        }
    }

    private static List<Cube> getAllEmptyNeighbors(Cube cube, List<Cube> allCubes, List<Cube> closedCubes, List<Cube> allNonBubbleCubes) throws CubeIsNonBubble {
        final List<Cube> neighbors = new ArrayList<>();
        addConditional(allNonBubbleCubes, allCubes, closedCubes, neighbors, new Cube(cube.getX() - 1, cube.getY(), cube.getZ()));
        addConditional(allNonBubbleCubes, allCubes, closedCubes, neighbors, new Cube(cube.getX() + 1, cube.getY(), cube.getZ()));
        addConditional(allNonBubbleCubes, allCubes, closedCubes, neighbors, new Cube(cube.getX(), cube.getY() - 1, cube.getZ()));
        addConditional(allNonBubbleCubes, allCubes, closedCubes, neighbors, new Cube(cube.getX(), cube.getY() + 1, cube.getZ()));
        addConditional(allNonBubbleCubes, allCubes, closedCubes, neighbors, new Cube(cube.getX(), cube.getY(), cube.getZ() - 1));
        addConditional(allNonBubbleCubes, allCubes, closedCubes, neighbors, new Cube(cube.getX(), cube.getY(), cube.getZ() + 1));
        return neighbors;
    }

    private static void addConditional(List<Cube> allNonBubbleCubes, List<Cube> closedCubes, List<Cube> allCubes, List<Cube> neighbors, Cube neighbor) throws CubeIsNonBubble {
        if (allNonBubbleCubes.contains(neighbor)) {
            throw new CubeIsNonBubble(neighbor);
        }
        if (!allCubes.contains(neighbor) && !closedCubes.contains(neighbor)) {
            neighbors.add(neighbor);
        }
    }

    public static void printCube(List<CubeWithFacings> cubes, List<Cube> bubbleCube) {

        final List<Cube> collect = cubes.stream().map(CubeWithFacings::getCube).collect(Collectors.toList());
        for (int z = zMin; z <= zMax; z++) {
            for (int y = yMin - 1; y <= yMax; y++) {
                for (int x = xMin; x <= xMax; x++) {
                    if (y == yMin - 1) {
                        if (x < 10) {
                            System.out.print(" ");
                        }
                        System.out.print(x);
                        continue;
                    }
                    if (collect.contains(new Cube(x, y, z))) {
                        System.out.print(" #");
                    } else if (bubbleCube.contains(new Cube(x, y, z))) {
                        System.out.print(" @");
                    } else {
                        System.out.print(" .");
                    }
                }
                if (y != yMin - 1) {
                    System.out.println(y);
                } else {
                    System.out.println();
                }
            }
            System.out.println(z);
            System.out.println();
        }
    }
}