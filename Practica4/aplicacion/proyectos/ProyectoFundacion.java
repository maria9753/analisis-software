package aplicacion.proyectos;

import aplicacion.usuarios.*;

public class ProyectoFundacion extends Proyecto {
    private double presupuesto;
    private double porcentaje;

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

    @Override
    public String toString() {
        return super.toString() + ". Presupuesto " + presupuesto + " . Porcentaje: " + porcentaje
                + "% /proyecto de fundación/";
    }
}
