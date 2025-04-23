package src.tiposDatos;

import java.util.LinkedHashMap;

/**
 * La clase StringData almacena cadenas de texto.
 * 
 * @author María Pozo, Carmen Gómez.
 */
public class StringDataTimes extends LinkedHashMap<String, String> {
    /** Cadena */
    private String word;
    /** Número de veces */
    private int times;
    /** Resultado */
    private String result;

    /**
     * Constructor de la clase StringData.
     * 
     * @param word  Cadena.
     * @param times Número de veces.
     */
    public StringDataTimes(String word, int times) {
        this.word = word;
        this.times = times;
        this.result = "";
    }

    /**
     * Método para convertir el número de veces a NumericData.
     * 
     * @return El número de veces en NumericData.
     */
    public NumericData toNumericData() {
        return new NumericData(times, 0);
    }

    /**
     * Método para añadir una copia de la cadena al resultado.
     */
    public void replicate() {
        if (times > 0) {
            result += word;
            times--;
        }
    }

    /**
     * Getter del atributo times.
     * 
     * @return El número de veces.
     */
    public int times() {
        return times;
    }

    /**
     * Setter del atributo times.
     * 
     * @param times Número de veces.
     */
    public void setTimes(int times) {
        this.times = times;
    }

    /**
     * Método to string para imprimir el texto.
     * 
     * @return Cadena que representa una cadena.
     */
    @Override
    public String toString() {
        return "word: " + word + ", times: " + times + ", result: " + result;
    }

    public String getWord() {
        return word != null ? word : "";
    }

    public String getResult() {
        return result;
    }
}