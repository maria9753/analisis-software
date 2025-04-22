package src.ejecutables;

/**
 * La clase StringDataMain contiene el método main que sirve para probar y
 * ejecutar el flujo de trabajo.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class StringDataMain {

    /**
     * Programa principal para probar el flujo de trabajo. Lo crea, ejecuta y
     * muestra los resultados.
     */
    public static void main(String[] args) {
        StateGraph<StringData> sg = buildWorkflow();
        System.out.println(sg);
        StringData input = new StringData("hola");
        System.out.println("input = " + input);
        StringData output = sg.run(input, true);
        System.out.println("result = " + output);
    }

    /**
     * Crea y configura el flujo de trabajo.
     *
     * @return StateGraph configurado.
     */
    private static StateGraph<StringData> buildWorkflow() {
        StateGraph<StringData> sg = new StateGraph<>("textFlow", "Reverse and uppercase");
        sg.addNode("reverse", d -> d.put("result", new StringBuilder(d.get("text")).reverse().toString()))
                .addNode("uppercase", d -> d.put("result", d.get("result").toUpperCase()));

        sg.addEdge("reverse", "uppercase");
        sg.setInitial("reverse");
        sg.setFinal("uppercase");
        return sg;
    }
}
