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
    private final List<T> history = new ArrayList<>();
    private Function<List<T>, T> processor;

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
     */
    public List<T> history() {
        return history;
    }

    public Function<List<T>, T> getProcessor() {
        return processor;
    }
}
