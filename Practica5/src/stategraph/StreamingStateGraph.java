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
        // Guardamos el input recibido
        history.add(input);

        if (debug) {
            System.out.println("Step 1 (average) - input: " + history);
        }

        // Se calcula el nuevo estado a partir del histórico
        if (processor != null) {
            T processed = processor.apply(new ArrayList<>(history));
            // Reemplazamos el último elemento con el procesado
            history.set(history.size() - 1, processed);

            // Ejecutamos el flujo normal sobre el dato procesado
            super.run(processed, debug);

            if (debug) {
                System.out.println("Step 2 (average) - average executed: " + history);
            }
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

    /**
     * Método to string para imprimir el grafo del flujo de trabajo con memoria.
     * 
     * @return Cadena que representa el grafo.
     */
    @Override
    public String toString() {
        return "Workflow 'average' (Calculates the average of incoming data):\n" +
                "- Nodes: {average=Node average (0 output nodes)}\n" +
                "- Initial: average\n";
    }
}
