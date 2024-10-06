package com.example.flow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SharedTestInfra {
    public static final String FLOW_LOG_CONTENT_1 = "dstport,protocol,tag\n" +
            "25,tcp,sv_P1\n" +
            "68,udp,sv_P2\n" +
            "23,tcp,sv_P1\n" +
            "31,udp,SV_P3\n" +
            "443,tcp,sv_P2";

    public static final String FLOW_LOG_CONTENT_2 = "dstport,protocol,tag\n" +
            "25,tcp,sv_P1\n" +
            "68,udp\n" +
            "23,tcp,sv_P1\n" +
            "31,udp,\n" +
            "443,tcp,sv_P2";

    public static final String DATA_DIR = "src/test/data/";


    public static void createFlowLog(String filename, String flowLogContent) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(filename);
            out.println(flowLogContent);
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteFlowLog(String filename) {
        File file = new File(filename);
        file.delete();
    }
}
