package src.stategraph;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import src.stategraph.StateGraph.ConditionalEdge;
import src.stategraph.StateGraph.WorkflowNode;

/**
 * Clase que ayuda a medir el tiempo de ejecución de un grafo de estados.
 * Guarda información sobre cuánto tarda en ejecutarse cada vez que se usa.
 *
 * @param <T> Tipo de dato para la clase.
 */
public class StateGraphProfiler<T> extends StateGraph<T> {
	/** Grafo que se decora */
    private StateGraph<T> stategraph;
    /** Historial de decoración */
    private List<String> history = new ArrayList<>();

    /**
     * Constructor de la clase StateGraphProfiler.
     * 
     * @param stategraph Grafo que se decora.
     */
    public StateGraphProfiler(StateGraph<T> stategraph) {
        super(stategraph.name, stategraph.description);

        this.stategraph = stategraph;

        this.tag = " [profiled]";
    }

    @Override
    public T run(T input, boolean debug) {
        return stategraph.run(input, debug);  
    }

    /**
     * Método que junta la acción del nodo y la de decoración.
     * 
     * @param nodeName Nombre del nodo.
     * @param action   Acción original del nodo.
     * 
     * @return La acción fusionada.
     */
    protected Consumer<T> profilerAction(String nodeName, Consumer<T> action) {
        return state -> {
            long startTime = System.nanoTime();
            long duration = System.nanoTime() - startTime;
            String entry = String.format("%s with: %s %.4f ms",
                    nodeName, state, duration / 1_000_000.0);
            action.accept(state);
            history.add(entry);
        };
    }

    /**
     * Getter del atributo historial de decoración.
     * 
     * @return El historial de decoración.
     */
    public List<String> history() {
        return new ArrayList<>(history);
    }

    @Override
    public StateGraph<T> addNode(String name, Consumer<T> action) {
        Consumer<T> profiler = profilerAction(name, action);
        stategraph.addNode(name, profiler); 
        return this;
    }
    
    @Override
    public StateGraph<T> setInitial(String nodeName) {
        return stategraph.setInitial(nodeName);
    }
    
    @Override
    public StateGraph<T> setFinal(String nodeName) {
        return stategraph.setFinal(nodeName);
    }
    
    @Override
    public <S> WorkflowNode<T, S> addWfNode(String nodeName, StateGraph<S> workflow) {
        WorkflowNode<T, S> node = new WorkflowNode<>(nodeName, workflow);
        stategraph.addWfNode(nodeName, workflow);
        return node;
    }
    
    @Override
    public StateGraph<T> addConditionalEdge(String from, String to, Predicate<T> condition) {
        stategraph.addConditionalEdge(from, to, condition);
    	return this;
    }
    
    @Override
    public StateGraph<T> addEdge(String from, String to) {
    	stategraph.addEdge(from, to);
    	return this;
    }

    @Override
    public String toString() {
    	stategraph.setTag(tag);
        return stategraph.toString();
    }
}
