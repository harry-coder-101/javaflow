package com.example.flow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProcessEventsTest {
    @Test
    void testSingleThreadedFlowLogs() {
        System.out.println("Testing processing events using single thread...\n");
        String filepath1 = SharedTestInfra.DATA_DIR + "flow_1.log";
        SharedTestInfra.createFlowLog(filepath1, SharedTestInfra.FLOW_LOG_CONTENT_1);
        String filepath2 = SharedTestInfra.DATA_DIR + "flow_2.log";
        SharedTestInfra.createFlowLog(filepath2, SharedTestInfra.FLOW_LOG_CONTENT_2);

        SingleThreadedProcessEvents stProcessEvents = new SingleThreadedProcessEvents(SharedTestInfra.DATA_DIR);
        stProcessEvents.readEvents();
        assertEquals(4, stProcessEvents.getTagCount().size());
        assertEquals(4, stProcessEvents.getTagCount().get("sv_P1"));
        assertTrue(stProcessEvents.getTagCount().containsKey("sv_P2"));
        assertFalse(stProcessEvents.getTagCount().containsKey("sv_P5"));
        assertEquals(2, stProcessEvents.getTagCount().get("Untagged"));
        assertEquals(5, stProcessEvents.getPortProtoCount().size());
        assertEquals(2, stProcessEvents.getPortProtoCount().get("443_tcp"));
        SharedTestInfra.deleteFlowLog(filepath1);
        SharedTestInfra.deleteFlowLog(filepath2);
        System.out.println("Testing processing events using single thread...SUCCESS\n");
    }

    @Test
    void testMultiThreadedFlowLogs() {
        System.out.println("Testing processing events using multi thread...\n");
        String filepath1 = SharedTestInfra.DATA_DIR + "flow_1.log";
        SharedTestInfra.createFlowLog(filepath1, SharedTestInfra.FLOW_LOG_CONTENT_1);
        String filepath2 = SharedTestInfra.DATA_DIR + "flow_2.log";
        SharedTestInfra.createFlowLog(filepath2, SharedTestInfra.FLOW_LOG_CONTENT_2);

        MultiThreadedProcessEvents mtProcessEvents = new MultiThreadedProcessEvents(SharedTestInfra.DATA_DIR, 2);
        mtProcessEvents.readEvents();
        assertEquals(4, mtProcessEvents.getTagCount().size());
        assertEquals(4, mtProcessEvents.getTagCount().get("sv_P1"));
        assertTrue(mtProcessEvents.getTagCount().containsKey("sv_P2"));
        assertFalse(mtProcessEvents.getTagCount().containsKey("sv_P5"));
        assertEquals(2, mtProcessEvents.getTagCount().get("Untagged"));
        assertEquals(5, mtProcessEvents.getPortProtoCount().size());
        assertEquals(2, mtProcessEvents.getPortProtoCount().get("443_tcp"));
        SharedTestInfra.deleteFlowLog(filepath1);
        SharedTestInfra.deleteFlowLog(filepath2);
        System.out.println("Testing processing events using multi thread...SUCCESS\n");
    }

    @Test
    void validateSingleAndMultiThreadedFlowLogs() {
        System.out.println("Testing processing events using single and multi thread...\n");
        String filepath1 = SharedTestInfra.DATA_DIR + "flow_1.log";
        SharedTestInfra.createFlowLog(filepath1, SharedTestInfra.FLOW_LOG_CONTENT_1);
        String filepath2 = SharedTestInfra.DATA_DIR + "flow_2.log";
        SharedTestInfra.createFlowLog(filepath2, SharedTestInfra.FLOW_LOG_CONTENT_2);

        SingleThreadedProcessEvents stProcessEvents = new SingleThreadedProcessEvents(SharedTestInfra.DATA_DIR);
        stProcessEvents.readEvents();
        assertEquals(4, stProcessEvents.getTagCount().size());
        assertEquals(4, stProcessEvents.getTagCount().get("sv_P1"));
        assertTrue(stProcessEvents.getTagCount().containsKey("sv_P2"));
        assertFalse(stProcessEvents.getTagCount().containsKey("sv_P5"));
        assertEquals(2, stProcessEvents.getTagCount().get("Untagged"));
        assertEquals(5, stProcessEvents.getPortProtoCount().size());
        assertEquals(2, stProcessEvents.getPortProtoCount().get("443_tcp"));

        MultiThreadedProcessEvents mtProcessEvents = new MultiThreadedProcessEvents(SharedTestInfra.DATA_DIR, 2);
        mtProcessEvents.readEvents();
        assertEquals(4, mtProcessEvents.getTagCount().size());
        assertEquals(4, mtProcessEvents.getTagCount().get("sv_P1"));
        assertTrue(mtProcessEvents.getTagCount().containsKey("sv_P2"));
        assertFalse(mtProcessEvents.getTagCount().containsKey("sv_P5"));
        assertEquals(2, mtProcessEvents.getTagCount().get("Untagged"));
        assertEquals(5, mtProcessEvents.getPortProtoCount().size());
        assertEquals(2, mtProcessEvents.getPortProtoCount().get("443_tcp"));

        assertEquals(stProcessEvents.getTagCount().size(), mtProcessEvents.getTagCount().size());
        assertEquals(stProcessEvents.getTagCount().get("sv_P1"), mtProcessEvents.getTagCount().get("sv_P1"));
        assertEquals(stProcessEvents.getTagCount().get("Untagged"), mtProcessEvents.getTagCount().get("Untagged"));
        assertEquals(stProcessEvents.getPortProtoCount().size(), mtProcessEvents.getPortProtoCount().size());
        assertEquals(stProcessEvents.getPortProtoCount().get("443_tcp"), mtProcessEvents.getPortProtoCount().get("443_tcp"));

        SharedTestInfra.deleteFlowLog(filepath1);
        SharedTestInfra.deleteFlowLog(filepath2);
        System.out.println("Testing processing events using single and multi thread...SUCCESS\n");
    }
}
