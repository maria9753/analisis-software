package aplicacion.usuarios;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import aplicacion.anuncios.Anuncio;
import aplicacion.follower.Follower;
import aplicacion.proyectos.Proyecto;
import aplicacion.exceptions.ProponenteNoApoyaException;
import aplicacion.exceptions.ProyectoMasDe60Exception;
import aplicacion.exceptions.ProyectoYaApoyadoException;

/**
 * La clase Ciudadano representa los ciudadanos que forman asociaciones, o
 * pertenecen a fundaciones.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class Ciudadano extends Usuario implements Follower {
    /** Nif del ciudadano */
    private String nif;
    /** Asociaciones a las que pertenece un ciudadano. */
    private Set<Asociacion> asociaciones;
    /** Proyectos que apoya un ciudadano y fecha del apoyo. */
    private Map<Proyecto, LocalDateTime> proyectosApoyados;
    private List<String> mensajes;

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
        this.proyectosApoyados = new HashMap<>();
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
    public Map<Proyecto, LocalDateTime> getProyectosApoyados() {
        return proyectosApoyados;
    }

    /**
     * Un ciudadano apoya un proyecto.
     * 
     * @param proyecto El proyecto que se quiere apoyar.
     * 
     * @throws ProponenteNoApoyaException
     * @throws ProyectoMasDe60Exception
     * @throws ProyectoYaApoyadoException
     */
    public void apoyarProyecto(Proyecto proyecto) throws ProponenteNoApoyaException, ProyectoMasDe60Exception, ProyectoYaApoyadoException {
        if (proyecto.getProponente() == this) {
            throw new ProponenteNoApoyaException("Un ciudadano no puede apoyar un proyecto del cual es el proponente");
        }

        if (proyectosApoyados.containsKey(proyecto)) {
            throw new ProyectoYaApoyadoException("Un ciudadano no puede apoyar un proyecto que ya apoyaba");
        }

        Duration rango = Duration.between(proyecto.getFechaCreacion(), LocalDateTime.now());
        if (rango.toDays() > 60) {
            throw new ProyectoMasDe60Exception("Un ciudadano no puede apoyar un proyecto que ha sido creado hace más de 60 días");
        }

        proyectosApoyados.put(proyecto, LocalDateTime.now());
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

    @Override
    public void recieve(Anuncio t) {
        mensajes.add(t.getContenidoAnuncio());
    }
}
