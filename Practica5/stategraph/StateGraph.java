import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * La clase StateGraph representa la clase genérica de flujo de trabajo.
 * 
 * @param <T> El tipo de los datos, el estado.
 * @author Carmen Gómez, María Pozo.
 */
public class StateGraph<T> {
    /** Nombre del grafo de flujo de trabajo */
    private final String name;
    /** Descripción del grafo de flujo de trabajo */
    private final String description;
    /** Mapa de nodos que contiene sus nombres y las acciones asociadas */
    private final Map<String, Consumer<T>> nodes = new LinkedHashMap<>();
    /** Mapa que representa las conexiones entre nodos */
    private final Map<String, List<String>> edges = new HashMap<>();
    /** Mapa que representa las conexiones entre nodos con condiciones */
    private final Map<String, List<ConditionalEdge<T>>> conditionalEdges = new HashMap<>();
    /** Nodos finales del grafo */
    private final Set<String> finalNodes = new HashSet<>();
    /** Nodo inicialdel grafo */
    private String initialNode;

    /**
     * Constructor de la clase StateGraph.
     * 
     * @param name        Nombre del grafo de flujo de trabajo.
     * @param description Descripción del grafo de flujo de trabajo.
     */
    public StateGraph(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Método para añadir un nodo con su acción asociada al grafo.
     * 
     * @param nodeName El nombre del nodo.
     * @param action   La acción que se ejecutará en el nodo.
     * @return StateGraph actual.
     */
    public StateGraph<T> addNode(String nodeName, Consumer<T> action) {
        nodes.put(nodeName, action);
        return this;
    }

    /**
     * Método que establece una conexión entre dos nodos.
     * 
     * @param from Nodo de origen.
     * @param to   Nodo de destino.
     */
    public void addEdge(String from, String to) {
        edges.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
    }

    /**
     * Método que establece una conexión condicional entre dos nodos.
     * 
     * @param from      Nodo de origen.
     * @param to        Nodo de destino.
     * @param condition Condición.
     */
    public void addConditionalEdge(String from, String to, Predicate<T> condition) {
        conditionalEdges.computeIfAbsent(from, k -> new ArrayList<>()).add(new ConditionalEdge<>(to, condition));
    }

    /**
     * Método que establece el nodo inicial del grafo.
     * 
     * @param nodeName El nodo inicial.
     */
    public void setInitial(String nodeName) {
        this.initialNode = nodeName;
    }

    /**
     * Método que establece un nodo final del grafo.
     * 
     * @param nodeName El nodo final.
     */
    public void setFinal(String nodeName) {
        finalNodes.add(nodeName);
    }

    /**
     * Método que ejecuta el flujo de trabajo desde el nodo inicial, en cada paso se
     * ejecuta la acción del nodo correspondiente.
     * 
     * @param input El estado inicial.
     * @param debug Bandera de debug.
     * @return El estado final.
     */
    public T run(T input, boolean debug) {
        Set<String> visited = new HashSet<>();
        Deque<String> stack = new ArrayDeque<>();
        stack.push(initialNode);
        int step = 1;
        if (debug) {
            System.out.println("Step " + (step++) + " (" + name + ") - input: " + input);
        }
        /** Recorrido DFS del grafo */
        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (visited.contains(current))
                continue;
            visited.add(current);

            Consumer<T> action = nodes.get(current);
            if (action != null) {
                action.accept(input);
                if (debug) {
                    System.out.println("Step " + (step++) + " (" + name + ") - " + current
                            + " executed : " + input);
                }
            }

            List<String> next = edges.getOrDefault(current, new ArrayList<>());
            for (int i = next.size() - 1; i >= 0; i--) {
                stack.push(next.get(i));
            }

            List<ConditionalEdge<T>> conditionals = conditionalEdges.getOrDefault(current, new ArrayList<>());
            for (int i = conditionals.size() - 1; i >= 0; i--) {
                ConditionalEdge<T> edge = conditionals.get(i);
                if (edge.condition.test(input)) {
                    stack.push(edge.toNode);
                }
            }
        }
        return input;
    }

    /**
     * Método to string para imprimir el grafo del flujo de trabajo.
     * 
     * @return Cadena que representa el grafo.
     */
    @Override
    public String toString() {
        String string = "Workflow '" + name + "' (" + description + "):\n- Nodes: {";
        boolean first = true;
        for (String nodeName : nodes.keySet()) {
            if (!first)
                string += ", ";

            int normalEdges = edges.getOrDefault(nodeName, new ArrayList<>()).size();
            int conditionalEdgesCount = conditionalEdges.getOrDefault(nodeName, new ArrayList<>()).size();
            int totalOutputs = normalEdges + conditionalEdgesCount;
            string += nodeName + "=Node " + nodeName + " (" + totalOutputs + " output nodes)";
            first = false;
        }
        string += "}\n- Initial: " + initialNode + "\n- Final: " + String.join(", ", finalNodes);
        return string;
    }

    /**
     * Clase interna para representar las condiciones.
     */
    private static class ConditionalEdge<T> {
        String toNode;
        Predicate<T> condition;

        ConditionalEdge(String toNode, Predicate<T> condition) {
            this.toNode = toNode;
            this.condition = condition;
        }
    }
}
