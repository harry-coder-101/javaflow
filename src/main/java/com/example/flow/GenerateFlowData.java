package com.example.flow;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateFlowData {
    public void RandomEventFiles(String dataDir, int numFiles, int events) {
        String[] protocols = {"tcp", "udp", "icmp"};
        char[] tagChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        int tagSize = 5;
        Random rndPort = new Random();
        Random rndProto = new Random();
        Random rndTag = new Random();
        Random rndNoTag = new Random();
        String header = "dstport,protocol,tag\n";

        for (int idx = 1; idx <= numFiles; idx++) {
            String filename = dataDir + "/flowlog_" + idx + ".log";
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
                bw.write(header);
                for (int evt = 0; evt < events; evt++) {
                    int port = rndPort.nextInt(65535);
                    String proto = protocols[rndProto.nextInt(protocols.length)];
                    String tag = "";
                    if (rndNoTag.nextInt(10) != 1) {
                        char[] tagCh = new char[tagSize];
                        for (int i = 0; i < tagSize; i++) {
                            tagCh[i] = i == tagSize / 2 ? '_' : tagChars[rndTag.nextInt(tagChars.length)];
                        }
                        tag = String.valueOf(tagCh);
                    }
                    String eventStr = port + "," + proto + "," + tag + "\n";
                    bw.write(eventStr);
                }
                bw.flush();
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void FixedSetRandomEventFiles(String dataDir, int numFiles, int events) {
        String[] protocols = {"tcp", "udp"};
        String[] tags = {"sv_P1", "sv_P2", "sv_P3", "sv_P4", "SV_P1", "SV_P2", "SV_P3", "SV_P4"};
        String[] ports = {"22", "23", "25", "31", "68", "80", "443"};

        Random rndPort = new Random();
        Random rndProto = new Random();
        Random rndTag = new Random();
        Random rndNoTag = new Random();
        String header = "dstport,protocol,tag\n";

        for (int idx = 1; idx <= numFiles; idx++) {
            String filename = dataDir + "/flowlog_" + idx + ".log";
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
                bw.write(header);
                for (int evt = 0; evt < events; evt++) {
                    String port = ports[rndPort.nextInt(ports.length)];
                    String proto = protocols[rndProto.nextInt(protocols.length)];
                    String tag = rndNoTag.nextInt(10) == 1 ? "" : tags[rndTag.nextInt(tags.length)];
                    String eventStr = port + "," + proto + "," + tag + "\n";
                    bw.write(eventStr);
                }
                bw.flush();
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
