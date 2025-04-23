package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.stategraph.StreamingStateGraph;
import java.util.List;
import java.util.function.Function;

class StreamingStateGraphTest {
    
    private StreamingStateGraph<Integer> graph;
    private StreamingStateGraph<String> stringGraph;
    
    @BeforeEach
    void setUp() {
        graph = new StreamingStateGraph<>("TestGraph", "Test Description");
        stringGraph = new StreamingStateGraph<>("StringGraph", "String processing graph");
    }
    
    @Test
    void testInitialState() {
        assertNotNull(graph);
        assertTrue(graph.history().isEmpty());
        assertNull(graph.getProcessor());
    }
    
    @Test
    void testRunWithoutProcessor() {
        Integer result = graph.run(5, false);
        assertEquals(5, result);
        
        List<Integer> history = graph.history();
        assertEquals(1, history.size());
        assertEquals(5, history.get(0));
    }
    
    @Test
    void testRunWithProcessor() {
        // Definimos un procesador que suma todos los elementos del historial
        Function<List<Integer>, Integer> sumProcessor = history -> 
            history.stream().mapToInt(Integer::intValue).sum();
        
        graph.setProcessor(sumProcessor);
        
        // Primera ejecución
        Integer result1 = graph.run(5, false);
        assertEquals(5, result1);
        
        // Segunda ejecución
        Integer result2 = graph.run(3, false);
        assertEquals(8, result2); // 5 + 3
        
        // Tercera ejecución
        Integer result3 = graph.run(2, false);
        assertEquals(10, result3); // 5 + 3 + 2
        
        // Verificamos el historial
        List<Integer> history = graph.history();
        assertEquals(3, history.size());
        assertEquals(5, history.get(0));
        assertEquals(8, history.get(1));
        assertEquals(10, history.get(2));
    }
    
    @Test
    void testStringProcessing() {
        // Procesador que concatena todos los strings del historial
        Function<List<String>, String> concatProcessor = history -> 
            String.join(" ", history);
        
        stringGraph.setProcessor(concatProcessor);
        
        String result1 = stringGraph.run("Hello", false);
        assertEquals("Hello", result1);
        
        String result2 = stringGraph.run("world", false);
        assertEquals("Hello world", result2);
        
        String result3 = stringGraph.run("!", false);
        assertEquals("Hello world !", result3);
        
        List<String> history = stringGraph.history();
        assertEquals(3, history.size());
        assertEquals("Hello", history.get(0));
        assertEquals("Hello world", history.get(1));
        assertEquals("Hello world !", history.get(2));
    }
    
    @Test
    void testMultipleRunsWithoutSettingProcessor() {
        // Ejecutamos varias veces sin procesador
        graph.run(1, false);
        graph.run(2, false);
        graph.run(3, false);
        
        List<Integer> history = graph.history();
        assertEquals(3, history.size());
        assertEquals(1, history.get(0));
        assertEquals(2, history.get(1));
        assertEquals(3, history.get(2));
    }
    
    @Test
    void testSetProcessorAfterRuns() {
        // Primero ejecutamos sin procesador
        graph.run(10, false);
        graph.run(20, false);
        
        // Luego establecemos el procesador
        Function<List<Integer>, Integer> avgProcessor = history -> 
            (int) history.stream().mapToInt(Integer::intValue).average().orElse(0);
        
        graph.setProcessor(avgProcessor);
        
        // Ejecutamos con procesador
        Integer result = graph.run(30, false);
        assertEquals(20, result); // (10 + 20 + 30) / 3 = 20
        
        List<Integer> history = graph.history();
        assertEquals(3, history.size());
        assertEquals(10, history.get(0));
        assertEquals(20, history.get(1));
        assertEquals(20, history.get(2)); // El último es el procesado
    }
    
    @Test
    void testDebugMode() {
        // Aunque el debug no hace mucho en esta clase, verificamos que no afecte el comportamiento
        Function<List<Integer>, Integer> squareProcessor = history -> 
            history.get(history.size() - 1) * history.get(history.size() - 1);
        
        graph.setProcessor(squareProcessor);
        
        Integer result1 = graph.run(5, true);
        assertEquals(25, result1);
        
        Integer result2 = graph.run(3, false);
        assertEquals(9, result2);
        
        List<Integer> history = graph.history();
        assertEquals(2, history.size());
        assertEquals(25, history.get(0));
        assertEquals(9, history.get(1));
    }
}