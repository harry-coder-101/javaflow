package com.example.flow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class BufferedFileReader implements EventFileReader {
    public HashMap<String, Integer> getTagCount() {
        return tagCount;
    }
    private final HashMap<String, Integer> tagCount = new HashMap<>();

    public HashMap<String, Integer> getPortProtoCount() {
        return portProtoCount;
    }

    private final HashMap<String, Integer> portProtoCount = new HashMap<>();
    private final HashMap<String, Integer> headerMap = new HashMap<>();
    @Override
    public void parseEvents(final String filepath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line = br.readLine();
            if (line == null)
                throw new IOException("No data");

            // validate header?
            String[] header = line.split(",");
            for (int col = 0; col < header.length; col++) {
                headerMap.put(header[col], col);
            }

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // log and continue invalid data
//                if (values.length < header.length)
//                    continue;

                // port + protocol
                String portProto = values[headerMap.get("dstport")].toString() + "_" + values[headerMap.get("protocol")];

                int cnt = portProtoCount.containsKey(portProto) ? portProtoCount.get(portProto) + 1 : 1;
                portProtoCount.put(portProto, cnt);

                int tagIdx = headerMap.get("tag");
                String tag = tagIdx >= values.length ? "Untagged" : values[tagIdx] == "" ? "Untagged" : values[tagIdx];
                cnt = tagCount.containsKey(tag) ? tagCount.get(tag) + 1 : 1;
                tagCount.put(tag, cnt);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
