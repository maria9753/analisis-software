package src.stategraph;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * La clase StateGraph representa la clase genérica de flujo de trabajo.
 * 
 * @param <T> El tipo de los datos, el estado.
 * @author Carmen Gómez, María Pozo.
 */
public class StateGraph<T> {
    /** Nombre del grafo de flujo de trabajo */
    protected final String name;
    /** Descripción del grafo de flujo de trabajo */
    protected final String description;
    /** Mapa de nodos que contiene sus nombres y las acciones asociadas */
    protected final Map<String, Consumer<T>> nodes = new LinkedHashMap<>();
    /** Mapa que representa las conexiones entre nodos */
    protected final Map<String, List<String>> edges = new HashMap<>();
    /** Mapa que representa las conexiones entre nodos con condiciones */
    protected final Map<String, List<ConditionalEdge<T>>> conditionalEdges = new HashMap<>();
    /** Mapa que representa los flujos de trabajo en forma de nodos */
    protected final Map<String, WorkflowNode<T, ?>> workflowNodes = new HashMap<>();
    /** Nodos finales del grafo */
    protected final Set<String> finalNodes = new HashSet<>();
    /** Nodo inicialdel grafo */
    protected String initialNode;
    /** Tag del grafo */
    protected String tag;

    /**
     * Constructor de la clase StateGraph.
     * 
     * @param name        Nombre del grafo de flujo de trabajo.
     * @param description Descripción del grafo de flujo de trabajo.
     */
    public StateGraph(String name, String description) {
        this.name = name;
        this.description = description;
        this.tag = "";
    }
    
    /**
     * Modifica el atributo tag del grafo.
     * 
     * @param tag Tag del grafo.
     */
    public void setTag(String tag) {
    	this.tag += tag;
    }
    
    /**
     * Obtiene el nombre identificativo del grafo de estados.
     * 
     * @return Nombre asignado al grafo durante su creación
     */
    public String getName() {
        return name;
    }

    /**
     * Proporciona la descripción del propósito del grafo.
     * 
     * @return Texto descriptivo sobre la función del grafo
     */
    public String getDescription() {
        return description;
    }

    /**
     * Indica el punto de entrada del flujo de trabajo.
     * 
     * @return Nombre del nodo donde comienza la ejecución
     */
    public String getInitialNode() {
        return initialNode;
    }

    /**
     * Muestra los nodos que marcan el final del proceso.
     * 
     * @return Conjunto de nodos considerados como finales (solo lectura)
     */
    public Set<String> getFinalNodes() {
        return Collections.unmodifiableSet(finalNodes);
    }

    /**
     * Devuelve todos los nodos básicos del grafo.
     * 
     * @return Mapa con los nodos y sus acciones asociadas (no modificable)
     */
    public Map<String, Consumer<T>> getNodes() {
        return Collections.unmodifiableMap(nodes);
    }

    /**
     * Presenta las conexiones directas entre nodos.
     * 
     * @return Relación de nodos origen con sus destinos directos
     */
    public Map<String, List<String>> getEdges() {
        return Collections.unmodifiableMap(edges);
    }

    /**
     * Muestra las conexiones que requieren cumplir una condición.
     * 
     * @return Relación de nodos con sus conexiones condicionales
     */
    public Map<String, List<ConditionalEdge<T>>> getConditionalEdges() {
        return Collections.unmodifiableMap(conditionalEdges);
    }

    /**
     * Método para añadir un nodo con su acción asociada al grafo.
     * 
     * @param nodeName El nombre del nodo.
     * @param action   La acción que se ejecutará en el nodo.
     * 
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
     * 
     * @return El grafo con el edge añadido.
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
     * 
     * @return El grafo con el edge añadido.
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
     * 
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
     * 
     * @return EL grafo.
     */
    public StateGraph<T> setInitial(String nodeName) {
        this.initialNode = nodeName;
        return this;
    }

    /**
     * Método que establece un nodo final del grafo.
     * 
     * @param nodeName El nodo final.
     * 
     * @return El grafo.
     */
    public StateGraph<T> setFinal(String nodeName) {
        finalNodes.add(nodeName);
        return this;
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
        
        result += tag + "}\n";
        result += "- Initial: " + initialNode + "\n";
        result += "- Final: " + (finalNodes.isEmpty() ? "None" : String.join(", ", finalNodes));

        return result;
    }

    /**
     * Clase interna para representar las condiciones.
     * 
     * @param <T> Tipo de dato que maneja el grafo de estados.
     */
    public static class ConditionalEdge<T> {
    	/** Nodo del nodo condicional*/
        public String toNode;
        /** Condición del nodo*/
        Predicate<T> condition;
        
        /**
         * Constructor de ConditionalEdge.
         * @param toNode	Nodo.
         * @param condition Condición.
         */
        ConditionalEdge(String toNode, Predicate<T> condition) {
            this.toNode = toNode;
            this.condition = condition;
        }
    }
    /**
     * Clase interna que representa un nodo especializado que contiene un flujo de trabajo completo.
     *
     * @param <T> Tipo de dato del estado del grafo padre
     * @param <S> Tipo de dato del estado del flujo de trabajo anidado
     */
    public static class WorkflowNode<T, S> {
        /** Nombre identificativo del nodo de flujo de trabajo */
        private final String name;
        
        /** Grafo de estados que representa el flujo de trabajo anidado */
        private final StateGraph<S> workflow;
        
        /** Función que transforma el estado padre al estado del subflujo */
        private Function<T, S> injector;
        
        /** Función que transfiere resultados del subflujo al estado padre */
        private BiConsumer<S, T> extractor;

        /**
         * Crea un nuevo nodo de flujo de trabajo.
         * 
         * @param name 			Nombre identificativo del nodo.
         * @param workflow 		Grafo de estados que representa el flujo.
         */
        public WorkflowNode(String name, StateGraph<S> workflow) {
            this.name = name;
            this.workflow = workflow;
        }

        /**
         * Establece la función de inyección para transformar el estado padre.
         * 
         * @param injector Función que convierte T a S.
         * 
         * @return La misma instancia para permitir encadenamiento.
         */
        public WorkflowNode<T, S> withInjector(Function<T, S> injector) {
            this.injector = injector;
            return this;
        }

        /**
         * Establece la función de extracción para transmitir los resultados.
         * 
         * @param extractor Función que aplica los resultados del subflujo al estado padre.
         * 
         * @return La misma instancia para permitir encadenamiento.
         */
        public WorkflowNode<T, S> withExtractor(BiConsumer<S, T> extractor) {
            this.extractor = extractor;
            return this;
        }

        /**
         * Ejecuta el flujo de trabajo anidado.
         * 
         * @param parentState Estado actual del grafo padre.
         * @param debug Modo de depuración para mostrar pasos de ejecución.
         * 
         * @throws IllegalStateException Si no se han configurado inyector o extractor.
         */
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
