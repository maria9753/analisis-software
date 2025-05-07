package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.stategraph.StateGraph;
import src.stategraph.StateGraphLogger;
import src.stategraph.StateGraphProfiler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class StateGraphDecoratorsTest {
    private StateGraph<String> baseGraph;
    private final String LOG_FILE = "test_log.txt";

    @BeforeEach
    void setUp() {
        baseGraph = new StateGraph<>("TestGraph", "Test Description");
        // Limpiar archivo de log antes de cada test
        try {
            Files.deleteIfExists(Paths.get(LOG_FILE));
        } catch (IOException e) {
            System.err.println("Error cleaning log file: " + e.getMessage());
        }
    }

    @Test
    void testLoggerDecoratorAddsTag() {
        StateGraph<String> loggedGraph = new StateGraphLogger<>(baseGraph, LOG_FILE);
        assertTrue(loggedGraph.toString().contains("[logged]"));
    }

    @Test
    void testProfilerDecoratorAddsTag() {
        StateGraph<String> profiledGraph = new StateGraphProfiler<>(baseGraph);
        assertTrue(profiledGraph.toString().contains("[profiled]"));
    }

    @Test
    void testCombinedDecoratorsAddBothTags() {
        StateGraph<String> loggedGraph = new StateGraphLogger<>(baseGraph, LOG_FILE);
        StateGraph<String> decoratedGraph = new StateGraphProfiler<>(loggedGraph);
        String graphStr = decoratedGraph.toString();
        assertTrue(graphStr.contains("[logged]"));
        assertTrue(graphStr.contains("[profiled]"));
    }

    @Test
    void testLoggerWritesToFile() {
        StateGraph<String> loggedGraph = new StateGraphLogger<>(baseGraph, LOG_FILE);
        
        loggedGraph.addNode("testNode", s -> s += " processed")
                 .setInitial("testNode")
                 .setFinal("testNode");
        
        loggedGraph.run("input", false);
        
        try {
            List<String> lines = Files.readAllLines(Paths.get(LOG_FILE));
            assertFalse(lines.isEmpty());
            assertTrue(lines.get(0).contains("testNode executed"));
        } catch (IOException e) {
            fail("Error reading log file: " + e.getMessage());
        }
    }

    @Test
    void testProfilerRecordsExecutionHistory() {
        StateGraphProfiler<String> profiledGraph = new StateGraphProfiler<>(baseGraph);
        
        profiledGraph.addNode("node1", s -> {})
                     .addNode("node2", s -> {})
                     .addEdge("node1", "node2")
                     .setInitial("node1")
                     .setFinal("node2");
        
        profiledGraph.run("test", false);
        
        List<String> history = profiledGraph.history();
        assertEquals(0, history.size());
    }

    @Test
    void testDecoratorChainMaintainsStructure() {
        StateGraph<String> loggedGraph = new StateGraphLogger<>(baseGraph, LOG_FILE);
        StateGraph<String> decoratedGraph = new StateGraphProfiler<>(loggedGraph);
        
        decoratedGraph.addNode("start", s -> {})
                      .addNode("end", s -> {})
                      .addEdge("start", "end")
                      .setInitial("start")
                      .setFinal("end");
        
        assertEquals(2, baseGraph.getNodes().size());
        assertEquals("start", decoratedGraph.getInitialNode());
    }
}