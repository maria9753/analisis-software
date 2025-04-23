package src.tiposDatos;

import java.util.LinkedHashMap;

/**
 * La clase StringData es un LinkedHashMap que almacena cadenas de texto.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class StringData extends LinkedHashMap<String, String> {
    /**
     * Constructor de la clase StringData.
     * 
     * @param input cadena.
     */
    public StringData(String input) {
        this.put("text", input);
        this.put("result", "");
    }

    /**
     * Método to string para imprimir el texto.
     * 
     * @return Cadena que representa una cadena.
     */
    @Override
    public String toString() {
        return "{text=" + get("text") + ", result=" + get("result") + "}";
    }
}