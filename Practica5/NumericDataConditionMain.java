import java.util.*;

/**
 * Clase para probar el flujo con datos numéricos y condiciones, y con un flujo
 * de decremento aleatorio.
 */
public class NumericDataConditionMain {
    /**
     * Programa principal para probar el flujo de trabajo. Lo crea, ejecuta y
     * muestra los resultados.
     */
    public static void main(String[] args) {
        StateGraph<NumericData> sg1 = buildMathWorkflow();

        System.out.println(sg1);

        NumericData input1 = new NumericData(2, 3);
        System.out.println("input = " + input1);
        NumericData output1 = sg1.run(input1, true);
        System.out.println("result = " + output1);

        NumericData input2 = new NumericData(2, 2);
        System.out.println("input = " + input2);
        NumericData output2 = sg1.run(input2, true);
        System.out.println("result = " + output2);

        System.out.println("\n");
        StateGraph<NumericData> sg2 = buildDecrementWorkflow();
        System.out.println(sg2);

        NumericData input3 = new NumericData(0, 0);
        NumericData output3 = sg2.run(input3, true);
        System.out.println("Final result = " + output3);
    }

    /**
     * Crea y configura el flujo de trabajo para sumar dos números y luego hacer
     * cuadrado si es par.
     *
     * @return StateGraph configurado.
     */
    private static StateGraph<NumericData> buildMathWorkflow() {
        StateGraph<NumericData> sg = new StateGraph<>("math1", "Add two numbers, and then square if even");

        sg.addNode("sum", mo -> mo.put("result", mo.get("op1") + mo.get("op2")));
        sg.addNode("square", mo -> mo.put("result", mo.get("result") * mo.get("result")));
        sg.addConditionalEdge("sum", "square", mo -> mo.get("result") % 2 == 0);

        sg.setInitial("sum");
        sg.setFinal("square");

        return sg;
    }

    /**
     * Crea y configura el flujo de trabajo para decrementar un número hasta cero.
     *
     * @return StateGraph configurado.
     */
    private static StateGraph<NumericData> buildDecrementWorkflow() {
        StateGraph<NumericData> sg = new StateGraph<>("decrementFlow", "Generate random number and decrement to zero");

        sg.addNode("generateRandomNumber", mo -> {
            Random rand = new Random();
            int randomValue = rand.nextInt(100) + 1;
            mo.put("op1", randomValue);
            mo.put("op2", 0);
            mo.put("result", randomValue);
        });

        sg.addNode("decrement", mo -> {
            int op1 = mo.get("op1");
            int result = op1;

            while (result > 0) {
                result--;
                mo.put("result", result);
            }
        });
        sg.addEdge("generateRandomNumber", "decrement");
        sg.setInitial("generateRandomNumber");
        sg.setFinal("decrement");

        return sg;
    }

}
