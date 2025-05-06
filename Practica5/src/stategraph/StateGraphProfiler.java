package src.stategraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que ayuda a medir el tiempo de ejecución de un grafo de estados.
 * Guarda información sobre cuánto tarda en ejecutarse cada vez que se usa.
 * 
 * @param <T> Tipo de dato para la clase.
 */
public class StateGraphProfiler<T> extends StateGraph<T> {
	/** Stategraph */
    private StateGraph<T> stategraph;
    /** Trazas de ejecución */
    private List<String> executionTraces = new ArrayList<>();

    /**
     * Crea un nuevo medidor para un grafo de estados.
     * @param stategraph El grafo que queremos medir
     */
    public StateGraphProfiler(StateGraph<T> stategraph) {
        super(stategraph.name, stategraph.description);

        this.stategraph = stategraph;

        this.nodes.putAll(stategraph.nodes); 
        this.edges.putAll(stategraph.edges); 
        this.conditionalEdges.putAll(stategraph.conditionalEdges); 
        this.workflowNodes.putAll(stategraph.workflowNodes); 
        this.finalNodes.addAll(stategraph.finalNodes); 
        this.initialNode = stategraph.initialNode; 
    }

    /**
     * Ejecuta el grafo y mide cuánto tiempo tarda.
     * 
     * @param input Los datos de entrada
     * @param debug Si mostrar información de depuración
     * @return El resultado después de ejecutar el grafo
     */
    @Override
    public T run(T input, boolean debug) {
        long startTime = System.nanoTime();
      
        T result = super.run(input, debug);
        
        long duration = (System.nanoTime() - startTime) / 1_000_000;
        
        executionTraces.add("Total execution time: " + duration + " ms");
        
        return result;
    }

    /**
     * Nos da el historial de tiempos de ejecución guardados.
     * @return Lista con todos los tiempos medidos
     */
    public List<String> history() {
        return new ArrayList<>(executionTraces);
    }

    /**
     * Descripción del grafo con información de que está siendo medido.
     * @return Texto que identifica este grafo como "perfilado"
     */
    @Override
    public String toString() {
        return super.toString() + " [profiled]";
    }
}