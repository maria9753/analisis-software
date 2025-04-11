package aplicacion.proyectos;

import aplicacion.usuarios.*;

/**
 * La clase ProyectoFundacion representa los proyectos que proponen las
 * fundaciones.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class ProyectoFundacion extends Proyecto {
    /** Presupuesto del proyecto */
    private double presupuesto;
    /** Porcentaje de contribución */
    private double porcentaje;

    /**
     * Constructor de la clase ProyectoFundacion.
     * 
     * @param titulo      Título del proyecto.
     * @param descripcion Descripción del proyecto.
     * @param proponente  Usuario que propone el proyecto.
     * @param presupuesto El presupuesto asignado al proyecto.
     * @param porcentaje  El porcentaje de contribución del proyecto.
     */
    public ProyectoFundacion(String titulo, String descripcion, Usuario proponente, double presupuesto,
            double porcentaje) {
        super(titulo, descripcion, proponente);
        if (porcentaje < 1 || porcentaje > 100) {
            throw new IllegalArgumentException("El porccentaje debe ser un valor entre 1 y 100.");
        }
        if (presupuesto <= 0) {
            throw new IllegalArgumentException("El presupuesto debe ser mayor de 0.");
        }
        this.porcentaje = porcentaje;
        this.presupuesto = presupuesto;
    }

    /**
     * Sobrescribe el método toString para representar el ProyectoFundacion como
     * una cadena.
     * 
     * @return Una representación del proyecto de fundación con su presupuesto
     *         y porcentaje.
     */
    @Override
    public String toString() {
        return super.toString() + ". Presupuesto " + presupuesto + "€. Porcentaje: " + porcentaje
                + "% /proyecto de fundación/";
    }
    
    /**
     * Método para obtener el presupuesto de un proyecto de fundación.
     * 
     * @return El presupuesto del proyecto de fundación.
     */
    public double getPresupuesto() {
    	return presupuesto;
    }
    
    /**
     * Método para obtener el porcentaje de un proyecto de fundación.
     * 
     * @return El porcentaje del proyecto de fundación.
     */
    public double getPorcentaje() {
    	return porcentaje;
    }
}
