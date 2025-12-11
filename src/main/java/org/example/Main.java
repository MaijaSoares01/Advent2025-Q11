package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static Map<String, List<String>> graph = new HashMap<>();

    // Memo for part 1 (simple count)
    static Map<String, Long> memoPart1 = new HashMap<>();

    // Memo for part 2 (with dac/fft visited flags)
    static Map<String, Long> memoPart2 = new HashMap<>();

    public static void main(String[] args) throws IOException {
        String inputFile = "src/main/resources/Devices&Outputs.txt";

        // Read input file and build graph
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(":", 2);
                String device = parts[0].trim();
                List<String> outs = new ArrayList<>();
                if (parts.length > 1 && !parts[1].trim().isEmpty()) {
                    outs = Arrays.asList(parts[1].trim().split("\\s+"));
                }
                graph.put(device, outs);
            }
        }

        // Part 1: Count paths from "you" to "out"
        if (!graph.containsKey("you")) {
            System.out.println("No 'you' device found.");
        } else {
            long part1Count = countPathsPart1("you");
            System.out.println("Part 1: Number of paths from 'you' to 'out' = " + part1Count);
        }

        // Part 2: Count paths from "svr" to "out" that include both dac and fft
        if (!graph.containsKey("svr")) {
            System.out.println("No 'svr' device found.");
        } else {
            long part2Count = countPathsPart2("svr", false, false);
            System.out.println("Part 2: Number of paths from 'svr' to 'out' including both 'dac' and 'fft' = " + part2Count);
        }
    }

    // Part 1: Simple memoized DFS count from 'you' to 'out'
    static long countPathsPart1(String node) {
        if (node.equals("out")) {
            return 1;
        }
        if (memoPart1.containsKey(node)) {
            return memoPart1.get(node);
        }
        long total = 0;
        for (String next : graph.getOrDefault(node, Collections.emptyList())) {
            total += countPathsPart1(next);
        }
        memoPart1.put(node, total);
        return total;
    }

    // Part 2: Memoized DFS count from 'svr' to 'out', tracking dac/fft visited flags
    static long countPathsPart2(String node, boolean dacVisited, boolean fftVisited) {
        if (node.equals("out")) {
            return (dacVisited && fftVisited) ? 1 : 0;
        }
        boolean newDacVisited = dacVisited || node.equals("dac");
        boolean newFftVisited = fftVisited || node.equals("fft");

        String key = node + "|" + newDacVisited + "|" + newFftVisited;
        if (memoPart2.containsKey(key)) {
            return memoPart2.get(key);
        }

        long total = 0;
        for (String next : graph.getOrDefault(node, Collections.emptyList())) {
            total += countPathsPart2(next, newDacVisited, newFftVisited);
        }
        memoPart2.put(key, total);
        return total;
    }
}
