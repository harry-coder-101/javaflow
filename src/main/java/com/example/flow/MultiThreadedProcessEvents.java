package com.example.flow;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadedProcessEvents extends ProcessEvents {
    int numThreads;

    public MultiThreadedProcessEvents(String dataDir, int numThreads) {
        super(dataDir);
        this.numThreads = numThreads;
    }
    public void readEvents() {
        try {
            File dir = new File(dataDir);
            File[] files = dir.listFiles();
            if (files == null) {
                throw new FileNotFoundException("No files found!");
            }

            numThreads = Math.min(numThreads, files.length);
            int batchSize = files.length/numThreads;
            int rem = files.length % numThreads;
            Thread[] pool = new Thread[numThreads];
            Instant start = Instant.now();
            int fileIdx = 0;
            for (int i = 0; i < numThreads; i++) {
                List<String> filePaths = new ArrayList<>();
                for (int j = 0; j < batchSize; j++) {
                    filePaths.add(files[fileIdx++].getPath());
                }
                // distribute remaining files to the first few threads
                if (rem != 0) {
                    filePaths.add(files[fileIdx++].getPath());
                    rem--;
                }
                pool[i] = new BatchProcess(filePaths);
                pool[i].start();
            }

            BatchProcess batch;
            for (int i = 0; i < numThreads; i++) {
                pool[i].join();

                // merge the tags and port counts from all threads
                batch = (BatchProcess) pool[i];
                batch.bufferedFileReader.getTagCount().forEach((k, v) -> super.getTagCount().merge(k, v, Integer::sum));
                batch.bufferedFileReader.getPortProtoCount().forEach((k, v) -> super.getPortProtoCount().merge(k, v, Integer::sum));
            }
            Instant end = Instant.now();
            System.out.println("\nMulti-threaded time taken:" + Duration.between(start, end).toMillis() + " milliseconds");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}

class BatchProcess extends Thread {
    BufferedFileReader bufferedFileReader = new BufferedFileReader();
    List<String> filePaths;
    BatchProcess(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    @Override
    public void run() {
        for (String filepath : filePaths) {
            bufferedFileReader.parseEvents(filepath);
        }
    }

}
