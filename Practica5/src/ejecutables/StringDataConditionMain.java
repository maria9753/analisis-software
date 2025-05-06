package src.ejecutables;

import src.stategraph.*;
import src.tiposDatos.*;

/**
 * Clase para probar el flujo con datos de texto y condiciones.
 */
public class StringDataConditionMain {
	
	/**
	 * Constructor.
	 */
	public StringDataConditionMain() {
		/** Constructor vacío*/
	}
    /**
     * Programa principal para probar el flujo de trabajo. Lo crea, ejecuta y
     * muestra los resultados.
     * 
     * @param args Argumentos.
     */
    public static void main(String[] args) {
        StateGraph<StringData> sg = buildWorkflow();

        System.out.println(sg);

        StringData input1 = new StringData("hello");
        System.out.println("input = " + input1);
        StringData output1 = sg.run(input1, true);
        System.out.println("result = " + output1);

        StringData input2 = new StringData("world");
        System.out.println("input = " + input2);
        StringData output2 = sg.run(input2, true);
        System.out.println("result = " + output2);
    }

    /**
     * Crea y configura el flujo de trabajo.
     *
     * @return StateGraph configurado.
     */
    private static StateGraph<StringData> buildWorkflow() {
        StateGraph<StringData> sg = new StateGraph<>("textFlow", "Reverse and uppercase if starts with 'h'");

        sg.addNode("reverse", d -> d.put("result", new StringBuilder(d.get("text")).reverse().toString()))
                .addNode("uppercase", d -> d.put("result", d.get("result").toUpperCase()));
        sg.addConditionalEdge("reverse", "uppercase", d -> d.get("text").startsWith("h"));

        sg.setInitial("reverse");
        sg.setFinal("uppercase");

        return sg;
    }
}