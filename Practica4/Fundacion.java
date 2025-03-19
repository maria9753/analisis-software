/**
 * La clase Fundacion representa a una fundación de la aplicación.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class Fundacion extends Usuario {
    /** Cif de la fundación*/
    private String cif;
    
    /**
     * Constructor de la clase Fundacion.
     * 
     * @param nombre        Nombre de la fundación.
     * @param contrasena    Contraseña de la fundación.
     * @param cif           Cif de la fundación.
     */
    public Fundacion (String nombre, String contrasena, String cif) {
        super(nombre, contrasena);
        this.cif = cif;
    }

    /**
     * Método para obtener el cif de una fundación.
     * 
     * @return cif de la fundación.
     */
    public String getCif() {
        return cif;
    }
}