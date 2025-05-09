package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.stategraph.StateGraph;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.BiConsumer;

class StateGraphTest {

    private StateGraph<String> graph;
    private StateGraph<Integer> intGraph;

    @BeforeEach
    void setUp() {
        graph = new StateGraph<>("TestGraph", "Test Description");
        intGraph = new StateGraph<>("IntGraph", "Integer Processing Graph");
    }

    @Test
    void testInitialState() {
        assertEquals("TestGraph", graph.getName());
        assertEquals("Test Description", graph.getDescription());
        assertNull(graph.getInitialNode());
        assertTrue(graph.getFinalNodes().isEmpty());
    }

    @Test
    void testAddNodeAndEdge() {
        graph.addNode("node1", s -> s += "1")
                .addNode("node2", s -> s += "2")
                .addEdge("node1", "node2");

        assertEquals(2, graph.getNodes().size());
        assertEquals(1, graph.getEdges().get("node1").size());
        assertEquals("node2", graph.getEdges().get("node1").get(0));
    }

    @Test
    void testAddConditionalEdge() {
        Predicate<String> condition = s -> s.length() > 3;
        graph.addNode("start", s -> {
        })
                .addNode("long", s -> s += "_long")
                .addConditionalEdge("start", "long", condition);

        assertEquals(1, graph.getConditionalEdges().get("start").size());
        assertEquals("long", graph.getConditionalEdges().get("start").get(0).toNode);
    }

    @Test
    void testRunSimpleWorkflow() {
        StringBuilder result = new StringBuilder();

        graph.addNode("a", s -> result.append("a"));
        graph.addNode("b", s -> result.append("b"));
        graph.addEdge("a", "b");
        graph.setInitial("a");
        graph.setFinal("b");

        graph.run("", false);
        assertEquals("ab", result.toString());
    }

    @Test
    void testConditionalPath() {
        StringBuilder result = new StringBuilder();
        Predicate<String> condition = s -> s.contains("x");

        graph.addNode("start", s -> {
        });
        graph.addNode("xPath", s -> result.append("x"));
        graph.addNode("default", s -> result.append("d"));
        graph.addEdge("start", "default");
        graph.addConditionalEdge("start", "xPath", condition);
        graph.setInitial("start");
        graph.setFinal("xPath");
        graph.setFinal("default");

        graph.run("x", false);
        assertEquals("x", result.toString());

        result.setLength(0);
        graph.run("y", false);
        assertEquals("d", result.toString());
    }

    @Test
    void testWorkflowNode() {
        class IntWrapper {
            int value;

            IntWrapper(int value) {
                this.value = value;
            }
        }

        StateGraph<IntWrapper> nestedGraph = new StateGraph<>("Nested", "Nested workflow");

        nestedGraph.addNode("inc", wrapper -> wrapper.value += 1);
        nestedGraph.setInitial("inc");
        nestedGraph.setFinal("inc");

        StringBuilder result = new StringBuilder();

        graph.addWfNode("nested", nestedGraph)
                .withInjector(s -> new IntWrapper(s.length()))
                .withExtractor((wrapper, s) -> result.append("l:").append(wrapper.value));

        graph.setInitial("nested");

        graph.run("test", false);
        assertEquals("l:5", result.toString());
    }

    @Test
    void testDebugOutput() {
        Consumer<String> action = s -> s += " processed";
        graph.addNode("process", action);
        graph.setInitial("process");
        graph.setFinal("process");

        assertDoesNotThrow(() -> graph.run("input", true));
    }

    @Test
    void testToString() {
        graph.addNode("start", s -> {
        });
        graph.addNode("end", s -> {
        });
        graph.addEdge("start", "end");
        graph.setInitial("start");
        graph.setFinal("end");

        String str = graph.toString();
        assertTrue(str.contains("Workflow 'TestGraph'"));
        assertTrue(str.contains("Nodes: {start=Node start (1 output nodes)"));
        assertTrue(str.contains("Initial: start"));
        assertTrue(str.contains("Final: end"));
    }

    @Test
    void testFinalNodeStopsExecution() {
        StringBuilder result = new StringBuilder();

        graph.addNode("a", s -> result.append("a"));
        graph.addNode("b", s -> result.append("b"));
        graph.addNode("c", s -> result.append("c"));
        graph.addEdge("a", "b");
        graph.addEdge("b", "c");
        graph.setInitial("a");
        graph.setFinal("b");

        graph.run("", false);
        assertEquals("ab", result.toString());
    }

    @Test
    void testMultipleFinalNodes() {
        StringBuilder result = new StringBuilder();

        graph.addNode("a", s -> result.append("a"));
        graph.addNode("b", s -> result.append("b"));
        graph.addNode("c", s -> result.append("c"));
        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.setInitial("a");
        graph.setFinal("b");
        graph.setFinal("c");

        graph.run("", false);
        assertTrue(result.toString().equals("ab") || result.toString().equals("ac"));
    }
}