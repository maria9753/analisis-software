package src.ejecutables;

import java.util.*;
import src.stategraph.*;
import src.tiposDatos.*;

/**
 * La clase StreamingMain contiene el método main que sirve para probar y
 * ejecutar la memoria en el flujo de trabajo.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class StreamingMain {

    /**
     * Programa principal para probar el flujo de trabajo. Lo crea, ejecuta y
     * muestra los resultados.
     */
    public static void main(String[] args) {
        StreamingStateGraph<DoubleData> sg = buildWorkflow();

        System.out.println(sg);

        List.of(1, 5, 2, 4).forEach(d -> {
            DoubleData wfInput = new DoubleData(d, 0);
            System.out.println("Workflow input = " + wfInput);
            sg.run(wfInput, true);
        });

        System.out.println("History=" + sg.history());
    }

    /**
     * Crea y configura el flujo de trabajo.
     *
     * @return StateGraph configurado.
     */
    public static StreamingStateGraph<DoubleData> buildWorkflow() {
        StreamingStateGraph<DoubleData> sg = new StreamingStateGraph<>("average",
                "Calculates the average of incoming data");

        sg.addNode("average", d -> {
            List<DoubleData> history = sg.history(); // accede al historial
            double sum = 0;
            for (DoubleData data : history) {
                sum += data.getValue();
            }
            double avg = sum / history.size();
            history.get(history.size() - 1).setAverage(avg); // actualiza la media del último dato
        });
        sg.setInitial("average");
        sg.setProcessor(history -> {
            DoubleData last = history.get(history.size() - 1);
            return new DoubleData(last.getValue(), 0.0); // Media inicial = 0
        });

        return sg;
    }

}