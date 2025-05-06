package src.tiposDatos;

/**
 * La clase DoubleData almacena datos numéricos.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class DoubleData {
    /** Valor recibido */
    private double value;

    /** Media de los valores recibidos */
    private double average;

    /**
     * Constructor de DoubleData.
     * 
     * @param value   Valor actual.
     * @param average Media de los valores anteriores.
     */
    public DoubleData(double value, double average) {
        this.value = value;
        this.average = average;
    }

    /**
     * Método que devuelve el valor actual.
     * 
     * @return valor.
     */
    public double getValue() {
        return value;
    }

    /**
     * Método que devuelve la media acumulada.
     * 
     * @return media.
     */
    public double getAverage() {
        return average;
    }

    /**
     * Método to string para imprimir el los datos double.
     * 
     * @return Cadena que representa un dato double.
     */
    @Override
    public String toString() {
        return String.format("%.1f (avg.= %.3f)", value, average);
    }

    /**
     * Establece la media.
     * @param avg Media de los valores.
     */
    public void setAverage(double avg) {
        average = avg;
    }
}