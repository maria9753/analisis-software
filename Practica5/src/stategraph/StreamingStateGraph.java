package src.stategraph;

import java.util.*;
import java.util.function.Function;

/**
 * La clase StreamingStateGraph representa la clase genérica con memoria de
 * flujo de trabajo.
 * 
 * @param <T> El tipo de los datos, el estado.
 * @author Carmen Gómez, María Pozo.
 */
public class StreamingStateGraph<T> extends StateGraph<T> {
	/** Historial del grafo*/
    private final List<T> history = new ArrayList<>();
    /** Processor del grafo*/
    private Function<List<T>, T> processor;

    /**
     * Constructor de la clase StreamingStateGraph
     * @param name 			Nombre del grafo.
     * @param description	Descripción del grafo.
     */
    public StreamingStateGraph(String name, String description) {
        super(name, description);
    }

    /**
     * Método que establece la función que se aplicará para generar el nuevo estado.
     * 
     * @param processor función que recibe el histórico y devuelve el nuevo estado.
     */
    public void setProcessor(Function<List<T>, T> processor) {
        this.processor = processor;
    }

    /**
     * Método que ejecuta el flujo de trabajo desde el nodo inicial, en cada paso se
     * ejecuta la acción del nodo correspondiente.
     * 
     * @param input El estado inicial.
     * @param debug Bandera de debug.
     * @return El estado final.
     */
    @Override
    public T run(T input, boolean debug) {

        history.add(input);

        if (processor != null) {
            T processed = processor.apply(new ArrayList<>(history));
            history.set(history.size() - 1, processed);

            super.run(processed, debug);

            return processed;
        }

        return input;
    }

    /**
     * Método que devuelve el historial del flujo de trabajo.
     * 
     * @return El historial.
     */
    public List<T> history() {
        return history;
    }

    /**
     * Método que el processor.
     * 
     * @return La función que representa el procesor.
     */
    public Function<List<T>, T> getProcessor() {
        return processor;
    }
}
