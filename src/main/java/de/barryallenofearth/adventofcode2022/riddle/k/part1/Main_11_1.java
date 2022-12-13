package de.barryallenofearth.adventofcode2022.riddle.k.part1;

import de.barryallenofearth.adventofcode2022.riddle.k.common.ReadInitialMonkeyList;
import de.barryallenofearth.adventofcode2022.riddle.k.model.Monkey;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main_11_1 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        final List<Monkey> monkeys = ReadInitialMonkeyList.readMonkeys();
        for (Monkey monkey : monkeys) {
            System.out.println(monkey);
        }
    }
}
