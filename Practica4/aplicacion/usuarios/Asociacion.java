package aplicacion.usuarios;

import java.util.*;
import java.time.Duration;
import java.time.LocalDateTime;

import aplicacion.anuncios.Anuncio;
import aplicacion.exceptions.*;
import aplicacion.*;
import aplicacion.follower.*;
import aplicacion.proyectos.*;

/**
 * La clase Asociacion representa una asociación de la aplicación.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class Asociacion extends Usuario implements FollowedEntity, Follower {
    /** Ciudadanos inscritos en la asociación */
    private Set<Ciudadano> ciudadanos;
    /** Asociaciones que contiene la asociación */
    private Set<Asociacion> asociaciones;
    /** Representante de la asocicacion */
    private Ciudadano representante;
    /** Proyectos que apoya la asociación y fecha del apoyo. */
    private Map<Proyecto, LocalDateTime> proyectosApoyados;
    private Set<Follower> followers;
    private Set<FollowedEntity> following;

    /**
     * Constructor de la clase Asociacion.
     * 
     * @param nombre        Nombre de la asociación.
     * @param contrasena    Contraseña de la asociación.
     * @param representante Representante de la asociación.
     * @throws RepresentanteInvalidoException
     */
    public Asociacion(String nombre, String contrasena, Aplicacion aplicacion, Ciudadano representante)
            throws RepresentanteInvalidoException {
        super(nombre, contrasena, aplicacion);
        this.representante = representante;
        this.ciudadanos = new HashSet<Ciudadano>();
        this.ciudadanos.add(representante);
        this.asociaciones = new HashSet<Asociacion>();
        this.proyectosApoyados = new HashMap<>();
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
        aplicacion.registrarAsociacion(this);
        representante.startToFollow(this);
    }

    /**
     * Obtiene el representante de la asociación.
     * 
     * @return El representante de la asociación.
     */
    public Ciudadano getRepresentante() {
        return representante;
    }

    /**
     * Obtiene los ciudadanos directos de la asociación.
     * 
     * @return Un conjunto de ciudadanos directos en la asociación.
     */
    public Set<Ciudadano> getCiudadanosDirectos() {
        return this.ciudadanos;
    }

    /**
     * Obtiene todos los ciudadanos, tanto directos como los de las asociaciones
     * relacionadas.
     * 
     * @return Un conjunto de todos los ciudadanos de la asociación.
     */
    public Set<Ciudadano> getCiudadanos() {
        Set<Ciudadano> todosLosCiudadanos = new HashSet<>(this.ciudadanos);
        for (Asociacion a : asociaciones) {
            todosLosCiudadanos.addAll(a.getCiudadanos());
        }
        todosLosCiudadanos.addAll(getCiudadanosDirectos());
        return todosLosCiudadanos;
    }

    /**
     * Obtiene las asociaciones relacionadas con esta asociación.
     * 
     * @return Un conjunto de asociaciones relacionadas.
     */
    public Set<Asociacion> getAsociaciones() {
        return asociaciones;
    }

    /**
     * Obtiene los proyectos que apoya la asociación.
     * 
     * @return El conjunto de proyectos que apoya la asociación.
     */
    public Map<Proyecto, LocalDateTime> getProyectosApoyados() {
        return proyectosApoyados;
    }

    /**
     * Comprobar si un ciudadano está inscrito en la asociación o en sus
     * asociaciones relacionadas.
     * 
     * @param ciudadano El ciudadano que se va a comprobar.
     * @return true si el ciudadano está inscrito, false si no lo está.
     */
    public boolean comprobarCiudadano(Ciudadano ciudadano) {
        if (ciudadanos.contains(ciudadano)) {
            return true;
        }

        for (Asociacion a : asociaciones) {
            if (a.comprobarCiudadano(ciudadano) == true) {
                return true;
            }
        }

        return false;
    }

    /**
     * Inscribe un nuevo ciudadano en la asociación si no está ya inscrito.
     * 
     * @param ciudadano El ciudadano a inscribir.
     */
    public void inscribirCiudadano(Ciudadano ciudadano) {
        if (comprobarCiudadano(ciudadano) == true) {
            return;
        }

        ciudadanos.add(ciudadano);
        ciudadano.startToFollow(this);
        anuncioNuevaInscripcion(ciudadano.getNombre());
    }

    /**
     * Da de baja a un ciudadano de la asociación.
     * 
     * @param ciudadano El ciudadano a dar de baja.
     */
    public void darDeBajaCiudadano(Ciudadano ciudadano) {
        ciudadanos.remove(ciudadano);
        ciudadano.eliminarRegistroAsociacion(this);
    }

    /**
     * Añade una nueva asociación relacionada, asegurándose de que el representante
     * sea el mismo.
     * 
     * @param asociacion La asociación a añadir.
     * @throws RepresentanteInvalidoException Si el representante de la nueva
     *                                        asociación no es el mismo.
     */
    public void anadirAsociacion(Asociacion asociacion) throws RepresentanteInvalidoException {
        if (asociacion.getRepresentante() != representante) {
            throw new RepresentanteInvalidoException("El representante de la nueva asociación debe ser el mismo.");
        }

        asociaciones.add(asociacion);
    }

    /**
     * Propone un proyecto en la aplicación.
     * 
     * @param proyecto El proyecto que se propone.
     */
    public void proponerProyecto(Proyecto proyecto) {
        aplicacion.proponerProyecto(proyecto);
        proyectosApoyados.put(proyecto, LocalDateTime.now());
        this.startToFollow(proyecto);
        anuncioPropuestaProyecto(proyecto.getNombre(), proyecto.getDescripcion());
        anuncioApoyoProyecto(proyecto.getNombre(), proyecto.getDescripcion());
        proyecto.addApoyo();
        proyecto.anuncioApoyoProyecto();
    }

    /**
     * Una asociación apoya un proyecto.
     * 
     * @param proyecto El proyecto que se quiere apoyar.
     * 
     * @throws ProponenteNoApoyaException
     * @throws ProyectoMasDe60Exception
     * @throws ProyectoYaApoyadoException
     */
    public void apoyarProyecto(Proyecto proyecto)
            throws ProponenteNoApoyaException, ProyectoMasDe60Exception, ProyectoYaApoyadoException {
        if (proyecto.getProponente() == this) {
            throw new ProponenteNoApoyaException(
                    "Una asociación no puede apoyar un proyecto del cual es el proponente");
        }

        if (proyectosApoyados.containsKey(proyecto)) {
            throw new ProyectoYaApoyadoException("Una asociación no puede apoyar un proyecto que ya apoyaba");
        }

        Duration rango = Duration.between(proyecto.getFechaCreacion(), LocalDateTime.now());
        if (rango.toDays() > 60) {
            throw new ProyectoMasDe60Exception(
                    "Una asociación no puede apoyar un proyecto que ha sido creado hace más de 60 días");
        }

        proyectosApoyados.put(proyecto, LocalDateTime.now());

        for (Ciudadano c : getCiudadanos()) {
            c.getProyectosApoyados().remove(proyecto);
        }
        anuncioApoyoProyecto(proyecto.getNombre(), proyecto.getDescripcion());
        proyecto.anuncioApoyoProyecto();
        proyecto.addApoyo();
    }

    @Override
    public void receive(Anuncio t) {
        for (Follower f : followers) {
            f.receive(t);
        }
        for (Asociacion a : getAsociaciones()) {
            a.receive(t);
        }
    }

    public boolean startToFollow(FollowedEntity entity) {
        if (!following.contains(entity)) {
            following.add(entity);
            if (entity instanceof Asociacion) {
                return ((Asociacion) entity).follow(this);
            } else if (entity instanceof Fundacion) {
                return ((Fundacion) entity).follow(this);
            } else if (entity instanceof Proyecto) {
                return ((Proyecto) entity).follow(this);
            }
        }
        return false;
    }

    public boolean startToUnfollow(FollowedEntity entity) {
        if (following.contains(entity)) {
            following.remove(entity);
            if (entity instanceof Asociacion) {
                return ((Asociacion) entity).unfollow(this);
            } else if (entity instanceof Fundacion) {
                return ((Fundacion) entity).unfollow(this);
            } else if (entity instanceof Proyecto) {
                return ((Proyecto) entity).unfollow(this);
            }
        }
        return false;
    }

    /**
     * Representación en forma de cadena de la asociación, mostrando su nombre y
     * la cantidad de ciudadanos.
     * 
     * @return Una cadena con el nombre de la asociación y el número de ciudadanos.
     */
    @Override
    public String toString() {
        return nombre + " <asociacion con " + getCiudadanos().size() + " ciudadanos>";
    }

    /**
     * Método para seguir a otros usuarios.
     * 
     * @param f Seguidor al que le empieza a seguir.
     * @return True si ha sido correcto, false si no.
     */
    @Override
    public boolean follow(Follower f) {
        return followers.add(f);
    }

    /**
     * Método para dejar de seguir a otros usuarios.
     * 
     * @param f Seguidor al que le deja de seguir.
     * @return True si ha sido correcto, false si no.
     */
    @Override
    public boolean unfollow(Follower f) {
        return followers.remove(f);
    }

    /**
     * Método para recibir anuncios
     * 
     * @param t Anuncio que se recibe
     */
    @Override
    public void announce(Anuncio t) {
        for (Follower f : followers) {
            f.receive(t);
        }
        for (Asociacion a : getAsociaciones()) {
            a.receive(t);
        }
        for (FollowedEntity entity : following) {
            entity.announce(t);
        }
    }

    public void anuncioPropuestaProyecto(String title, String description) {
        announce(new Anuncio(super.nombre + " propone el proyecto" + title + ": \"" + description + "\""));
    }

    public void anuncioApoyoProyecto(String title, String description) {
        announce(new Anuncio(super.nombre + " da apoyo al proyecto" + title + ": \"" + description + "\""));
    }

    public void anuncioNuevaInscripcion(String nombre) {
        announce(new Anuncio(
                "Alta de " + nombre + " en " + super.nombre + " (" + getCiudadanos().size() + " miembros)"));
    }

	public Set<Follower> getFollowers() {
		return this.followers;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asociacion that = (Asociacion) o;
        return Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
