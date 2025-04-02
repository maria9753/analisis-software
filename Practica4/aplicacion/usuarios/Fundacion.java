package aplicacion.usuarios;

import java.util.ArrayList;
import java.util.List;

import aplicacion.proyectos.*;
import aplicacion.anuncios.Anuncio;
import aplicacion.exceptions.CifInvalidoException;
import aplicacion.follower.*;
import aplicacion.*;

/**
 * La clase Fundacion representa a una fundación de la aplicación.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class Fundacion extends Usuario implements FollowedEntity {
    /** Cif de la fundación */
    private String cif;
    private List<Follower> followers;
    private List<ProyectoFundacion> proyectos;

    /**
     * Constructor de la clase Fundacion.
     * 
     * @param nombre     Nombre de la fundación.
     * @param contrasena Contraseña de la fundación.
     * @param cif        Cif de la fundación.
     * @throws CifInvalidoException
     */
    public Fundacion(String nombre, String contrasena, Aplicacion aplicacion, String cif) throws CifInvalidoException {
        super(nombre, contrasena, aplicacion);
        this.cif = cif;
        this.followers = new ArrayList<>();
        aplicacion.registrarFundacion(this);
        this.proyectos = new ArrayList<>();
    }

    /**
     * Método para obtener el cif de una fundación.
     * 
     * @return cif de la fundación.
     */
    public String getCif() {
        return cif;
    }

    /**
     * Representación en forma de cadena de la fundación.
     * 
     * @return Una cadena que representa la fundación, incluyendo su nombre y CIF.
     */
    @Override
    public String toString() {
        return nombre + "CIF (" + cif + ") <fundacion>";
    }

    /**
     * Método para seguir a otros usuarios.
     * 
     * @param f Seguidor al que se empieza a seguir.
     * @return True si ha sido correcto, false si no.
     */
    @Override
    public boolean follow(Follower f) {
        return followers.add(f);
    }

    /**
     * Método para dejar de seguir a otros usuarios.
     * 
     * @param f Seguidor al que se deja de seguir.
     * @return True si ha sido correcto, false si no.
     */
    @Override
    public boolean unfollow(Follower f) {
        return followers.remove(f);
    }

    /**
     * Propone un proyecto en la aplicación.
     * 
     * @param proyecto El proyecto que se propone.
     */
    public void proponerProyecto(ProyectoFundacion proyecto) {
        aplicacion.proponerProyecto(proyecto);
        proyectos.add(proyecto);
        anuncioPropuestaProyecto(proyecto.getNombre(), proyecto.getDescripcion());
    }

    /**
     * Método para recibir anuncios
     * 
     * @param t Anuncio que se recibe
     */
    @Override
    public void announce(Anuncio t) {
        for (Follower f : followers) {
            f.recieve(t);
        }
    }

    public void anuncioPropuestaProyecto(String title, String description) {
        announce(new Anuncio(super.nombre + " propone el proyecto" + title + ": \"" + description + "\""));
    }
}