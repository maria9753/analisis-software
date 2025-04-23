package src.ejecutables;

import src.stategraph.StateGraph;
import src.tiposDatos.StringData;
import src.tiposDatos.NumericData;

/**
 * Clase para probar los flujos anidados.
 * 
 * @author María Pozo, Carmen Gómez.
 */
public class WorkflowMain {
    /**
     * Constructor de la clase WorkflowMain.
     */
    public WorkflowMain() {
        /** Constructor vacío */
    }

    /**
     * Programa principal para probar el flujo de trabajo. Lo crea, ejecuta y
     * muestra los resultados.
     */
    public static void main(String[] args) {
        StateGraph<NumericData> wfNumeric = new StateGraph<>("math1", "Add two numbers, and then square if even");
        wfNumeric.addNode("sum", (NumericData mo) -> mo.put("result", mo.get("op1") + mo.get("op2")));
        wfNumeric.addNode("square", (NumericData mo) -> mo.put("result", mo.get("result") * mo.get("result")));
        wfNumeric.addConditionalEdge("sum", "square", mo -> mo.get("result") % 2 == 0);
        wfNumeric.setInitial("sum");
        wfNumeric.setFinal("square");

        StateGraph<StringData> sg = buildWorflow(wfNumeric);

        System.out.println(sg);

        StringData input = new StringData("jamon", 2);

        System.out.println("input = " + input);

        StringData output = sg.run(input, true);

        System.out.println("result = " + output);
    }

    /**
     * Crea y configura el flujo de trabajo.
     *
     * @param wfNumeric Flujo de trabajo anidado.
     * @return StateGraph configurado.
     */
    public static StateGraph<StringData> buildWorflow(StateGraph<NumericData> wfNumeric) {
        StateGraph<StringData> sg = new StateGraph<>("replicate", "Replicates a given word");

        sg.addWfNode("calculate", wfNumeric)
                .withInjector((StringData sd) -> sd.toNumericData())
                .withExtractor((NumericData nd, StringData sd) -> sd.setTimes(nd.get("result")));

        sg.addNode("replicate", sd -> sd.replicate());
        sg.addEdge("calculate", "replicate")
        	.addConditionalEdge("replicate", "replicate", sd -> sd.times() > 0);

        sg.setInitial("calculate");

        return sg;
    }
}
