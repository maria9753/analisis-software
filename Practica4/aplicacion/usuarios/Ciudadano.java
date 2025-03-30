package aplicacion.usuarios;

import java.util.*;

/**
 * La clase Ciudadano representa los ciudadanos que forman asociaciones, o
 * pertenecen a fundaciones.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class Ciudadano extends Usuario {
    /** Nif del ciudadano */
    private String nif;
    /** Asociaciones a las que pertenece un ciudadano. */
    private Set<Asociacion> asociaciones;
    /** Proyectos que apoya un ciudadano. */
    private Set<Proyecto> proyectos;

    /**
     * Constructor de la clase Ciudadano.
     * 
     * @param nombre     Nombre del ciudadano.
     * @param contrasena Contraseña del ciudadano.
     * @param nif        NIF del ciudadano.
     */
    public Ciudadano(String nombre, String contrasena, String nif) {
        super(nombre, contrasena);
        this.nif = nif;
        this.asociaciones = new HashSet<>();
        this.proyectos = new HashSet<>();
    }

    /**
     * Obtiene las asociaciones a las que pertenece el ciudadano.
     * 
     * @return Un conjunto de asociaciones a las que pertenece el ciudadano.
     */
    public Set<Asociacion> getAsociaciones() {
        return this.asociaciones;
    }

    /**
     * Registra a un ciudadano en una asociación.
     * 
     * @param asociacion La asociación a la que se desea agregar el ciudadano.
     */
    public void registarAsociacion(Asociacion asociacion) {
        this.asociaciones.add(asociacion);
    }

    /**
     * Obtiene el NIF del ciudadano.
     * 
     * @return El NIF del ciudadano.
     */
    public String getNif() {
        return this.nif;
    }

    /**
     * Obtiene los proyectos que apoya un ciudadano.
     * 
     * @return El conjunto de proyectos que apoya el ciudadano.
     */
    public Set<Proyecto> getProyectos() {
        return proyectos;
    }

    /**
     * Apoya un proyecto.
     * 
     * @param proyecto El proyecto que se quiere apoyar.
     */
    public void apoyarProyecto(Proyecto proyecto) {
        if (proyecto.getProponente() == this) {
            throw new 
        }
    }

    /**
     * Representación en forma de cadena del ciudadano.
     * 
     * @return Una cadena que representa al ciudadano, incluyendo su nombre y NIF.
     */
    @Override
    public String toString() {
        return nombre + " (" + nif + ") <usuario>";
    }
}