package src.stategraph;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import src.tiposDatos.DoubleData;

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
    /** Mapa que representa los flujos de trabajo en forma de nodos */
    private final Map<String, WorkflowNode<T, ?>> workflowNodes = new HashMap<>();
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
    public StateGraph<T> addEdge(String from, String to) {
        edges.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        return this;
    }

    /**
     * Método que establece una conexión condicional entre dos nodos.
     * 
     * @param from      Nodo de origen.
     * @param to        Nodo de destino.
     * @param condition Condición.
     */
    public StateGraph<T> addConditionalEdge(String from, String to, Predicate<T> condition) {
        conditionalEdges.computeIfAbsent(from, k -> new ArrayList<>()).add(new ConditionalEdge<>(to, condition));
        return this;
    }

    /**
     * Método para añadir un nodo que en realidad es un flujo de trabajo.
     * 
     * @param <S>      Tipo del estado de flujo.
     * @param nodeName Nombre del nodo.
     * @param workflow Flujo de trabajo.
     * @return El nodo creado.
     */
    public <S> WorkflowNode<T, S> addWfNode(String nodeName, StateGraph<S> workflow) {
        WorkflowNode<T, S> node = new WorkflowNode<>(nodeName, workflow);
        workflowNodes.put(nodeName, node);
        return node;
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
        String string;
        if (initialNode == null)
            return input;

        String currentNode = initialNode;
        int step = 1;

        if (debug) {
            string = "Step " + (step++) + " (" + name + ") - input: ";
            if (this instanceof StreamingStateGraph<?>) {
                string += ((StreamingStateGraph<?>) this).history();
            } else {
                string += input;
            }
            System.out.println(string);
        }

        while (currentNode != null) {
            // Ejecutar nodo de workflow anidado primero
            if (workflowNodes.containsKey(currentNode)) {
                WorkflowNode<T, ?> node = workflowNodes.get(currentNode);
                try {
                    node.execute(input, debug);
                    if (debug) {
                        string = "Step " + (step++) + " (" + name + ") - " + currentNode
                                + " executed : ";
                        if (this instanceof StreamingStateGraph<?>) {
                            string += ((StreamingStateGraph<?>) this).history();
                        } else {
                            string += input;
                        }
                        System.out.println(string);
                    }
                } catch (IllegalStateException e) {
                    System.err.println("Error executing nested workflow: " + e.getMessage());
                }
            }
            // Luego nodos normales
            else if (nodes.containsKey(currentNode)) {
                Consumer<T> action = nodes.get(currentNode);
                if (action != null) {
                    action.accept(input);
                    if (debug) {
                        string = "Step " + (step++) + " (" + name + ") - " + currentNode
                                + " executed : ";
                        if (this instanceof StreamingStateGraph<?>) {
                            string += ((StreamingStateGraph<?>) this).history();
                        } else {
                            string += input;
                        }
                        System.out.println(string);
                    }
                }
            }

            // Verificar si es nodo final
            if (finalNodes.contains(currentNode)) {
                break;
            }

            // Obtener siguiente nodo
            currentNode = getNextNode(currentNode, input);
        }

        return input;
    }

    private String getNextNode(String currentNode, T state) {
        // Primero verificar conexiones condicionales
        List<ConditionalEdge<T>> conditionals = conditionalEdges.getOrDefault(currentNode, new ArrayList<>());
        for (ConditionalEdge<T> edge : conditionals) {
            if (edge.condition.test(state)) {
                return edge.toNode;
            }
        }

        // Si no hay condiciones, verificar conexiones normales
        List<String> normalEdges = edges.getOrDefault(currentNode, new ArrayList<>());
        if (!normalEdges.isEmpty()) {
            return normalEdges.get(0); // Tomar el primer nodo de la lista
        }

        return null; // No hay más nodos
    }

    /**
     * Método to string para imprimir el grafo del flujo de trabajo.
     * 
     * @return Cadena que representa el grafo.
     */
    @Override
    public String toString() {
        String result = "Workflow '" + name + "' (" + description + "):\n";
        result += "- Nodes: {";

        boolean first = true;

        // Mostrar primero los workflowNodes
        for (String nodeName : workflowNodes.keySet()) {
            if (!first) {
                result += ", ";
            }
            int outputs = edges.getOrDefault(nodeName, new ArrayList<>()).size() +
                    conditionalEdges.getOrDefault(nodeName, new ArrayList<>()).size();
            result += nodeName + "=Node " + nodeName + " (" + outputs + " output nodes)";
            first = false;
        }

        // Luego los nodos normales
        for (String nodeName : nodes.keySet()) {
            if (!first) {
                result += ", ";
            }
            int outputs = edges.getOrDefault(nodeName, new ArrayList<>()).size() +
                    conditionalEdges.getOrDefault(nodeName, new ArrayList<>()).size();
            result += nodeName + "=Node " + nodeName + " (" + outputs + " output nodes)";
            first = false;
        }

        result += "}\n";
        result += "- Initial: " + initialNode + "\n";
        result += "- Final: " + (finalNodes.isEmpty() ? "None" : String.join(", ", finalNodes));

        return result;
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

    /**
     * Clase interna para representar los flujos de trabajo en forma de nodo.
     * 
     * @param <T>
     * @param <S> Tipo del estado de flujo.
     */
    public static class WorkflowNode<T, S> {
        private final String name;
        private final StateGraph<S> workflow;
        private Function<T, S> injector;
        private BiConsumer<S, T> extractor;

        public WorkflowNode(String name, StateGraph<S> workflow) {
            this.name = name;
            this.workflow = workflow;
        }

        public WorkflowNode<T, S> withInjector(Function<T, S> injector) {
            this.injector = injector;
            return this;
        }

        public WorkflowNode<T, S> withExtractor(BiConsumer<S, T> extractor) {
            this.extractor = extractor;
            return this;
        }

        public void execute(T parentState, boolean debug) throws IllegalStateException {
            if (injector == null || extractor == null) {
                throw new IllegalStateException("El flujo de trabajo debe tener inyector y extractor especificado.");
            }

            S state = injector.apply(parentState);
            if (debug) {
                System.out.println("Step 1 (" + workflow.name + ") - input: " + state);
            }

            S output = workflow.run(state, debug);
            extractor.accept(output, parentState);
        }
    }
}
