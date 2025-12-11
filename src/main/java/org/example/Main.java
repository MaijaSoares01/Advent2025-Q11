package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static Map<String, List<String>> graph = new HashMap<>();
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Devices&Outputs.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                String device = parts[0].trim();
                String[] outputs = parts[1].trim().split(" ");
                graph.put(device, Arrays.asList(outputs));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long count = dfs("you");
        System.out.println("How many different paths lead from you to out?: " + count);
    }

    private static long dfs(String node) {
        if (node.equals("out")) {
            return 1; // reached destination
        }
        long paths = 0;
        for (String next : graph.getOrDefault(node, List.of())) {
            paths += dfs(next);
        }
        return paths;
    }
}
