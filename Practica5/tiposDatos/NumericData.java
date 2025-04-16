import java.util.LinkedHashMap;

/**
 * La clase NumericData es un LinkedHashMap que almacena datos numéricos.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class NumericData extends LinkedHashMap<String, Integer> {

    /**
     * Constructor de la clase NumericData.
     * 
     * @param op1 El primer operando.
     * @param op2 El segundo operando.
     */
    public NumericData(int op1, int op2) {
        this.put("op1", op1);
        this.put("op2", op2);
        this.put("result", 0);
    }

    /**
     * Método to string para imprimir el los datos numéricos.
     * 
     * @return Cadena que representa un dato numérico.
     */
    @Override
    public String toString() {
        return "{op1=" + get("op1") + ", op2=" + get("op2") + ", result=" + get("result") + "}";
    }
}
