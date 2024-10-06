package com.example.flow;

import java.util.HashMap;

public class ProcessEvents {
    public void setTagCount(HashMap<String, Integer> tagCount) {
        this.tagCount = tagCount;
    }

    public HashMap<String, Integer> getTagCount() {
        return tagCount;
    }

    private HashMap<String, Integer> tagCount = new HashMap<>();

    public void setPortProtoCount(HashMap<String, Integer> portProtoCount) {
        this.portProtoCount = portProtoCount;
    }

    public HashMap<String, Integer> getPortProtoCount() {
        return portProtoCount;
    }

    private HashMap<String, Integer> portProtoCount = new HashMap<>();

    String dataDir;
    ProcessEvents(String dataDir) {
        this.dataDir = dataDir;
    }

    public void readEvents() {}

    public void printTagSummary() {
        System.out.println("\nTag Counts:");
        System.out.printf("%-10s %-10s\n", "Tag", "Count");
        tagCount.forEach((key, value) -> System.out.printf("%-10s %-10s\n", key, value));
    }
    public void printPortProtoSummary() {
        System.out.println("\nPort/Protocol Combination Counts:");
        System.out.printf("%-6s %-10s %-10s\n","Port", "Protocol", "Count");
        portProtoCount.forEach((key, value) -> {
            String[] tokens = key.split("_");
            System.out.printf("%-6s %-10s %-10s\n", tokens[0], tokens[1], value);
        });
    }
}
