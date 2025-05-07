package src.stategraph;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.function.Predicate;

import src.stategraph.StateGraph.WorkflowNode;

/**
 * Clase que añade funcionalidad de registro a un grafo de estados.
 * Guarda información sobre la ejecución en un archivo de texto.
 *
 * @param <T> Tipo de dato que maneja el grafo de estados
 */
public class StateGraphLogger<T> extends StateGraph<T> {
	/** Grafo que se decora */
    private StateGraph<T> stategraph;
    /** Nombre del archivo */
    private String fileName;

    /**
     * Constructor de la clase StateGraphLogger.
     * 
     * @param stategraph Grafo que se decora.
     * @param fileName	 Nombre del fichero.
     */
    public StateGraphLogger(StateGraph<T> stategraph, String fileName) {
        super(stategraph.name, stategraph.description);

        this.stategraph = stategraph;
        this.fileName = fileName;
        
        try (BufferedWriter file = new BufferedWriter(new FileWriter(fileName))) {
            file.write("");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }

        this.tag = " [logged]";
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
    protected Consumer<T> loggerAction(String nodeName, Consumer<T> action) {
        return state -> {
            action.accept(state);
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));
            String entry = "[" + time + "] node " + nodeName + " executed, with output: " + state + "\n";

            try (BufferedWriter file = new BufferedWriter(new FileWriter(fileName, true))) {
                file.write(entry);
            } catch (IOException e) {
                System.err.println("Error writing to log file: " + e.getMessage());
            }
        };
    }

    @Override
    public StateGraph<T> addNode(String name, Consumer<T> action) {
        Consumer<T> logger = loggerAction(name, action);
        stategraph.addNode(name, logger);  
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
    public StateGraph<T> addEdge(String from, String to) {
    	stategraph.addEdge(from, to);
    	return this;
    }
    
    @Override
    public StateGraph<T> addConditionalEdge(String from, String to, Predicate<T> condition) {
        stategraph.addConditionalEdge(from, to, condition);
    	return this;
    }
    
    @Override
    public <S> WorkflowNode<T, S> addWfNode(String nodeName, StateGraph<S> workflow) {
        WorkflowNode<T, S> node = new WorkflowNode<>(nodeName, workflow);
        stategraph.addWfNode(nodeName, workflow);
        return node;
    }

    @Override
    public String toString() {
    	stategraph.setTag(tag);
        return stategraph.toString();
    }
}
