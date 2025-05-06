package src.stategraph;

/**
 * Clase que añade funcionalidad de registro a un grafo de estados.
 * Guarda información sobre la ejecución en un archivo de texto.
 *
 * @param <T> Tipo de dato que maneja el grafo de estados
 */
public class StateGraphLogger<T> extends StateGraph<T> {
    /** Grafo original que estamos decorando con funcionalidad de logging */
    private StateGraph<T> stategraph;
    
    /** Nombre del archivo donde se guardarán los registros */
    private String fileName;

    /**
     * Crea un nuevo logger para un grafo de estados existente.
     * 
     * @param stategraph Grafo que queremos monitorizar
     * @param fileName   Ruta del archivo donde guardar los registros
     */
    public StateGraphLogger(StateGraph<T> stategraph, String fileName) {
        super(stategraph.name, stategraph.description);
        this.stategraph = stategraph;
        this.fileName = fileName;
        this.nodes.putAll(stategraph.nodes);
        this.edges.putAll(stategraph.edges);
        this.conditionalEdges.putAll(stategraph.conditionalEdges);
        this.workflowNodes.putAll(stategraph.workflowNodes);
        this.finalNodes.addAll(stategraph.finalNodes);
        this.initialNode = stategraph.initialNode;
    }

    /**
     * Ejecuta el grafo registrando información importante en el archivo.
     * 
     * @param input  Estado inicial para la ejecución
     * @param debug  Si mostrar información de depuración por consola
     * @return       Resultado de la ejecución del grafo
     */
    @Override
    public T run(T input, boolean debug) {
        log("Starting workflow: " + name);
        log("Initial state: " + input);
        
        T result = super.run(input, debug);
        
        log("Workflow completed");
        log("Final state: " + result);
        
        return result;
    }

    /**
     * Escribe un mensaje en el archivo de registro con marca de tiempo.
     * 
     * @param message Mensaje a registrar
     */
    private void log(String message) {
        String timestamp = java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));
        
        try (java.io.PrintWriter out = new java.io.PrintWriter(
             new java.io.FileWriter(fileName, true))) {
            out.println("[" + timestamp + "] " + message);
        } catch (java.io.IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    /**
     * Descripción del grafo indicando que tiene logging activado.
     * 
     * @return Representación textual del grafo con indicador de logging
     */
    @Override
    public String toString() {
        return super.toString() + " [logged]";
    }
}