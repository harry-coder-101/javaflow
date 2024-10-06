package com.example;

import com.example.flow.GenerateFlowData;
import com.example.flow.MultiThreadedProcessEvents;
import com.example.flow.SingleThreadedProcessEvents;

import java.io.IOException;

public class Flow {

    SingleThreadedProcessEvents singleThreadedProcessEvents;
    MultiThreadedProcessEvents multiThreadedProcessEvents;
    String dataDir;

    public static void main(String[] args) {
        // Generate random set of flow logs in the "data" folder
//        GenerateFlowData flowData = new GenerateFlowData();
//        flowData.RandomEventFiles("data", 1000, 10000);
//        flowData.FixedSetRandomEventFiles("data", 10, 10000);

        Flow flow = new Flow("data");
        try {
            flow.readEventsAndPrintSummary();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Flow(String dataDir) {
        this.dataDir = dataDir;
    }

    public void readEventsAndPrintSummary() throws IOException {

        singleThreadedProcessEvents = new SingleThreadedProcessEvents(dataDir);
        singleThreadedProcessEvents.readEvents();
        singleThreadedProcessEvents.printTagSummary();
        singleThreadedProcessEvents.printPortProtoSummary();

        multiThreadedProcessEvents = new MultiThreadedProcessEvents(dataDir, 8);
        multiThreadedProcessEvents.readEvents();
        multiThreadedProcessEvents.printTagSummary();
        multiThreadedProcessEvents.printPortProtoSummary();

    }


}