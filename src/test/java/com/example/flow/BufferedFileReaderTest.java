package com.example.flow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BufferedFileReaderTest {
    @Test
    void testParseEvents() {
        System.out.println("Testing reading events using buffered file reader...\n");
        String filepath = SharedTestInfra.DATA_DIR + "flow_1.log";
        SharedTestInfra.createFlowLog(filepath, SharedTestInfra.FLOW_LOG_CONTENT_1);
        BufferedFileReader bfr = new BufferedFileReader();
        bfr.parseEvents(filepath);
        assertEquals(3, bfr.getTagCount().size());
        assertEquals(2, bfr.getTagCount().get("sv_P2"));
        assertTrue(bfr.getTagCount().containsKey("sv_P1"));
        assertFalse(bfr.getTagCount().containsKey("sv_P5"));
        assertEquals(5, bfr.getPortProtoCount().size());
        assertEquals(1, bfr.getPortProtoCount().get("443_tcp"));
        SharedTestInfra.deleteFlowLog(filepath);
        System.out.println("Testing reading events using buffered file reader...SUCCESS\n");
    }

    @Test
    void testParseEventsUntagged() {
        System.out.println("Testing reading events using buffered file reader untagged...\n");
        String filepath = SharedTestInfra.DATA_DIR + "flow_1.log";
        SharedTestInfra.createFlowLog(filepath, SharedTestInfra.FLOW_LOG_CONTENT_2);
        BufferedFileReader bfr = new BufferedFileReader();
        bfr.parseEvents(filepath);
        assertEquals(3, bfr.getTagCount().size());
        assertEquals(1, bfr.getTagCount().get("sv_P2"));
        assertTrue(bfr.getTagCount().containsKey("sv_P1"));
        assertEquals(2, bfr.getTagCount().get("Untagged"));
        assertFalse(bfr.getTagCount().containsKey("sv_P5"));
        assertEquals(5, bfr.getPortProtoCount().size());
        assertEquals(1, bfr.getPortProtoCount().get("443_tcp"));
        SharedTestInfra.deleteFlowLog(filepath);
        System.out.println("Testing reading events using buffered file reader untagged...SUCCESS\n");
    }

}
