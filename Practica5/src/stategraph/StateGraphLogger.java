package src.stategraph;

public class StateGraphLogger<T> extends StateGraph<T> {
    private StateGraph<T> stategraph;
    private String fileName;

    /**
     * Contructor de la clase StateGraphLogger<T>.
     * 
     * @param stategraph Grafo que se decora.
     * @param fileName   Nombre del fichero
     */
    public StateGraphLogger (StateGraph<T> stategraph, String fileName) {
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

    @Override
    public T run(T input, boolean debug) {
        log("Starting workflow: " + name);
        log("Initial state: " + input);
        
        T result = super.run(input, debug);
        
        log("Workflow completed");
        log("Final state: " + result);
        
        return result;
    }

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

    @Override
    public String toString() {
        return super.toString() + " [logged]";
    }
}
