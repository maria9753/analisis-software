import java.util.*;
import java.util.function.Function;

public class StreamingStateGraph<T> {
    private final String name;
    private final String description;
    private final List<T> history = new ArrayList<>();
    private Function<List<T>, T> processor;

    public StreamingStateGraph(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setProcessor(Function<List<T>, T> processor) {
        this.processor = processor;
    }

    public void run(T input, boolean debug) {
        history.add(input);

        if (debug) {
            System.out.println("Step 1 (average) - input: " + history);
        }

        if (processor != null) {
            T result = processor.apply(new ArrayList<>(history));
            history.set(history.size() - 1, result);

            if (debug) {
                System.out.println("Step 2 (average) - average executed: " + history);
            }
        }
    }

    public List<T> history() {
        return history;
    }

    @Override
    public String toString() {
        return "Workflow 'average' (Calculates the average of incoming data):\n" +
                "- Nodes: {average=Node average (0 output nodes)}\n" +
                "- Initial: average\n";
    }
}