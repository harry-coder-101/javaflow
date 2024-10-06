package com.example.flow;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

public class SingleThreadedProcessEvents extends ProcessEvents {

    public SingleThreadedProcessEvents(String dataDir) {
        super(dataDir);
    }

    @Override
    public void readEvents() {
        File dir = new File(dataDir);
        File[] files = dir.listFiles();
        if (files != null) {
            BufferedFileReader eventFileParser = new BufferedFileReader();
            Instant singleStart = Instant.now();
            for (File file : files) {
                eventFileParser.parseEvents(file.getPath());
            }
            Instant singleEnd = Instant.now();
            super.setTagCount(eventFileParser.getTagCount());
            super.setPortProtoCount(eventFileParser.getPortProtoCount());
            System.out.println("\nSingle-threaded time taken:" + Duration.between(singleStart, singleEnd).toMillis() + " milliseconds");
        }
    }

}
