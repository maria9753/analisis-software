package src.ejecutables;

import src.stategraph.StateGraph;
import src.stategraph.StateGraphLogger;
import src.stategraph.StateGraphProfiler;
import src.tiposDatos.NumericData;

/**
 * Clase para probar el patrón Decorator.
 * 
 * @author María Pozo, Carmen Gómez.
 */
public class DecoratorMain {
    /**
     * Contructor de la clase DecoratorMain.
     */
    public DecoratorMain() {
        /** Constructor vacío */
    }

    /**
     * Programa principal para probar el flujo de trabajo. Lo crea, ejecuta y
     * muestra los resultados.
     * 
     * @param args Argumentos.
     */
    public static void main(String[] args) {
        StateGraph<NumericData> g = new StateGraph<>("loop-down", "Get a number, and decrease if positive");
        StateGraphLogger<NumericData> lg = new StateGraphLogger<>(g, "traces.txt");
        StateGraphProfiler<NumericData> sg = new StateGraphProfiler<>(lg);

        sg.addNode("decrease", (NumericData mo) -> mo.put("op1", mo.get("op1")-1))
          .addConditionalEdge("decrease", "decrease", (NumericData mo) -> mo.get("op1") > 0)
          .setInitial("decrease");
        
          NumericData input = new NumericData(3, 0);
          System.out.println(sg+"\\input = " + input);
          NumericData output = sg.run(input, true);
          System.out.println("result = " + output);
          System.out.println("history = "+ sg.history());
    }
}
