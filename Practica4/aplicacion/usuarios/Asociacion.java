package aplicacion.usuarios;

import java.util.*;
import java.time.Duration;
import java.time.LocalDateTime;

import aplicacion.anuncios.AnnouncementStrategy;
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
    private List<Ciudadano> ciudadanos;
    /** Asociaciones que contiene la asociación */
    private Set<Asociacion> asociaciones;
    /** Representante de la asocicacion */
    private Ciudadano representante;
    /** Proyectos que apoya la asociación y fecha del apoyo. */
    private Map<Proyecto, LocalDateTime> proyectosApoyados;
    /** Seguidores y sus estrategias */
    private Map<Follower, AnnouncementStrategy> followerStrategies;
    /** Entidades a las que se sigue */
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
        this.ciudadanos = new ArrayList<Ciudadano>();
        this.asociaciones = new HashSet<Asociacion>();
        this.proyectosApoyados = new HashMap<>();
        this.followerStrategies = new HashMap<>();
        this.following = new HashSet<>();
        aplicacion.registrarAsociacion(this);
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
    public List<Ciudadano> getCiudadanosDirectos() {
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
     * @param ns 		Estrategia con la que va a seguir a la asociación.
     */
    public void inscribirCiudadano(Ciudadano ciudadano, AnnouncementStrategy ns) {
        if (comprobarCiudadano(ciudadano) == true) {
            return;
        }

        ciudadanos.add(ciudadano);
        ciudadano.getAsociaciones().add(this);
        ciudadano.startToFollow(this, ns);
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
        for (Ciudadano c: asociacion.getCiudadanos()) {
        	c.startToFollow(this, null);
        }
    }

    /**
     * Propone un proyecto en la aplicación.
     * 
     * @param proyecto El proyecto que se propone.
     */
    public void proponerProyecto(Proyecto proyecto) {
        aplicacion.proponerProyecto(proyecto);
        proyectosApoyados.put(proyecto, LocalDateTime.now());
        this.startToFollow(proyecto, null);
        anuncioPropuestaProyecto(proyecto.getNombre(), proyecto.getDescripcion());
        int i;
        for (i = 0; i < getCiudadanos().size(); i++) {
            proyecto.addApoyo();
        }
        anuncioApoyoProyecto(proyecto.getNombre(), proyecto.getDescripcion(), proyecto.getNumApoyos());
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
        	if (c.getProyectosApoyados().containsKey(proyecto)) {
        		proyecto.removeApoyo();
        	}
            c.getProyectosApoyados().remove(proyecto);
            proyecto.addApoyo();
        }
        
        anuncioApoyoProyecto(proyecto.getNombre(), proyecto.getDescripcion(), proyecto.getNumApoyos());
    }

    /**
     * Método para recibir anuncios
     * 
     * @param t Anuncio que se recibe
     */
    @Override
    public void receive(Anuncio t) {
        for (Follower f : followerStrategies.keySet()) {
            f.receive(t);
        }
        for (Asociacion a : getAsociaciones()) {
            a.receive(t);
        }
    }

    /**
     * Método para comenzar a seguir a una entidad según una estrategia.
     * 
     * @param entity Entidad a la que se va a seguir.
     * @param ns Estrategia que se quiere seguir.
     * @return True si sale bien, false si no.
     */
    public boolean startToFollow(FollowedEntity entity, AnnouncementStrategy ns) {
        if (!following.contains(entity)) {
            following.add(entity);
            if (entity instanceof Asociacion) {
                return ((Asociacion) entity).follow(this, ns);
            } else if (entity instanceof Fundacion) {
                return ((Fundacion) entity).follow(this, ns);
            } else if (entity instanceof Proyecto) {
                return ((Proyecto) entity).follow(this, ns);
            }
        }
        return false;
    }

    /**
     * Método para comenzar dejar de seguir a una entidad según una estrategia.
     * 
     * @param entity Entidad a la que se va a dejar de seguir.
     * @return True si sale bien, false si no.
     */
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
        return follow(f, null);
    }

    /**
     * Método para dejar de seguir a otros usuarios.
     * 
     * @param f Seguidor al que le deja de seguir.
     * @return True si ha sido correcto, false si no.
     */
    @Override
    public boolean unfollow(Follower f) {
        if (f == null) {
        	return false;
        }
        
        followerStrategies.remove(f);
        
        return true;
    }

    /**
     * Método para recibir anuncios
     * 
     * @param t Anuncio que se recibe
     */
    @Override
    public void announce(Anuncio t) {
        for (Map.Entry<Follower, AnnouncementStrategy> e : followerStrategies.entrySet()) {
            Follower f = e.getKey();
            AnnouncementStrategy ns = e.getValue();
            
            if (ns == null || ns.enviarOnoEnviar(t, f, this)) {
            	f.receive(t);
            }
        }      
    }

    /**
     * Método para anunciar un nuevo proyecto.
     * 
     * @param title Título del proyecto.
     * @param description Descripción del proyecto.
     */
    public void anuncioPropuestaProyecto(String title, String description) {
        announce(new Anuncio(super.nombre + " propone el proyecto " + title + ": \"" + description + "\""));
    }

    /**
     * Método para anunciar un apoyo a un proyecto.
     * 
     * @param title Título del proyecto.
     * @param description Descripción del proyecto.
     * @param numApoyos Número de apoyos del proyecto.
     */
    public void anuncioApoyoProyecto(String title, String description, int numApoyos) {
        announce(new Anuncio(super.nombre + " da apoyo al proyecto " + title + ": \"" + description + "\" (" + numApoyos + " apoyos)"));
    }

    /**
     * Método para anunciar una nueva suscripción.
     * 
     * @param nombre Nombre del ciudadano.
     */
    public void anuncioNuevaInscripcion(String nombre) {
        announce(new Anuncio(
                "Alta de " + nombre + " en " + super.nombre + " (" + getCiudadanosDirectos().size() + " miembros)"));
    }

    /**
     * Método para obtener los seguidores.
     * @return Los seguidores.
     */
	public Set<Follower> getFollowers() {
		return followerStrategies.keySet();
	}
	
	/**
	 * Compara este objeto con el objeto especificado para determinar si son iguales.
	 *
	 * @param obj el objeto con el que se va a comparar.
	 * @return true si los objetos son iguales; false en caso contrario.
	 */
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asociacion that = (Asociacion) o;
        return Objects.equals(nombre, that.nombre);
    }

	/**
	 * Devuelve un valor hash para este objeto. Este método está diseñado para que
	 * sea coherente con equals(Object): si dos objetos son iguales según el método equals,
	 * entonces deben tener el mismo valor hash.
	 *
	 * @return el valor hash del objeto.
	 */
    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    /**
     * Método para seguir a otros usuarios según una estrategia.
     * 
     * @param f Seguidor al que se empieza a seguir.
     * @param ns Estrategia que se sigue.
     * @return True si ha sido correcto, false si no.
     */
    @Override
	public boolean follow(Follower f, AnnouncementStrategy ns) {
		if (f == null || followerStrategies.containsKey(f)) {
	        return false;
	    }
		
	    followerStrategies.put(f, ns);
	    
	    return true;
	}
    
    /**
     * Método para obtener la estrategia de un seguidor.
     * 
     * @param f El seguidor.
     * @return La estrategia con la que sigue el seguidor a la entidad.
     */
    @Override
	public AnnouncementStrategy getAnnouncementStrategy(Follower f) {
		return followerStrategies.get(f);
	}

    /**
	 * Método para modificar la estrategia de un seguidor.
	 * @param f El seguidor.
	 * @param ns La estrategia con la que sigue el seguidor a la entidad.
	 */
	@Override
	public void setAnnouncementStrategy(Follower f, AnnouncementStrategy ns) {
		if (f != null) {
			followerStrategies.put(f, ns);
		}
	}
}
