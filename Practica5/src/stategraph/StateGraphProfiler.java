package src.stategraph;

import java.util.ArrayList;
import java.util.List;

public class StateGraphProfiler<T> extends StateGraph<T> {
    private StateGraph<T> stategraph;
    private List<String> executionTraces = new ArrayList<>();

    public StateGraphProfiler(StateGraph<T> stategraph) {
        super(stategraph.name, stategraph.description);

        this.stategraph = stategraph;

        this.nodes.putAll(stategraph.nodes);
        this.edges.putAll(stategraph.edges);
        this.conditionalEdges.putAll(stategraph.conditionalEdges);
        this.workflowNodes.putAll(stategraph.workflowNodes);
        this.finalNodes.addAll(stategraph.finalNodes);
        this.initialNode = stategraph.initialNode;
    }

    @Override
    public T run(T input, boolean debug) {
        long startTime = System.nanoTime();
        T result = super.run(input, debug);
        long duration = (System.nanoTime() - startTime) / 1_000_000; // ms
        
        executionTraces.add("Total execution time: " + duration + " ms");
        return result;
    }

    public List<String> history() {
        return new ArrayList<>(executionTraces);
    }

    @Override
    public String toString() {
        return super.toString() + " [profiled]";
    }
}
