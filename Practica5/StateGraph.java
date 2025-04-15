import java.util.*;
import java.util.function.Consumer;

public class StateGraph<T> {
    private final String name;
    private final String description;
    private final Map<String, Consumer<T>> nodes = new LinkedHashMap<>();
    private final Map<String, List<String>> edges = new HashMap<>();
    private final Set<String> finalNodes = new HashSet<>();
    private String initialNode;

    public StateGraph(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public StateGraph<T> addNode(String nodeName, Consumer<T> action) {
        nodes.put(nodeName, action);
        return this;
    }

    public void addEdge(String from, String to) {
        edges.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
    }

    public void setInitial(String nodeName) {
        this.initialNode = nodeName;
    }

    public void setFinal(String nodeName) {
        finalNodes.add(nodeName);
    }

    public T run(T input, boolean debug) {
        Set<String> visited = new HashSet<>();
        Deque<String> stack = new ArrayDeque<>();
        stack.push(initialNode);
        int step = 1;

        if (debug) {
            System.out.println("- Initial: " + initialNode);
            System.out.println("- Final: " + finalNodes);
            System.out.println("input = " + input);
        }

        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (visited.contains(current)) continue;
            visited.add(current);

            Consumer<T> action = nodes.get(current);
            if (action != null) {
                if (debug) {
                    System.out.println("Step " + (step++) + " (" + name + ") - " + current + " executed: " + input);
                }
                action.accept(input);
            }

            List<String> next = edges.getOrDefault(current, new ArrayList<>());
            for (int i = next.size() - 1; i >= 0; i--) {
                stack.push(next.get(i));
            }
        }

        return input;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Workflow '").append(name).append("' (").append(description).append("):\n");
        sb.append("- Nodes: ").append(nodes.keySet()).append("\n");
        return sb.toString();
    }
}