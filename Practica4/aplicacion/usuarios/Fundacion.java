package aplicacion.usuarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import aplicacion.proyectos.*;
import aplicacion.anuncios.AnnouncementStrategy;
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
    private Map<Follower, AnnouncementStrategy> followerStrategies;
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
        if(cif==null) {
        	throw new CifInvalidoException("El cif no puede ser null.");
        }
        this.cif = cif;
        this.followerStrategies = new HashMap<>();
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
        return follow(f, null);
    }

    /**
     * Método para dejar de seguir a otros usuarios.
     * 
     * @param f Seguidor al que se deja de seguir.
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
     * Propone un proyecto en la aplicación.
     * 
     * @param proyecto El proyecto que se propone.
     */
    public void proponerProyecto(ProyectoFundacion proyecto) {
    	if(proyecto==null) {
    		throw new IllegalArgumentException("El proyecto no puede ser null.");
    	}
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
    	Follower f = null;
    	AnnouncementStrategy ns = null;
        for (Map.Entry<Follower, AnnouncementStrategy> e : followerStrategies.entrySet()) {
            f = e.getKey();
            ns = e.getValue();
            
            if (ns == null || ns.enviarOnoEnviar(t, f, this)) {
            	f.receive(t);
            }
        }        
    }

    public void anuncioPropuestaProyecto(String title, String description) {
        announce(new Anuncio(super.nombre + " propone el proyecto" + title + ": \"" + description + "\""));
    }

	public Set<Follower> getFollowers() {
		return this.followerStrategies.keySet();
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fundacion that = (Fundacion) o;
        return Objects.equals(cif, that.cif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cif);
    }

    @Override
	public boolean follow(Follower f, AnnouncementStrategy ns) {
		if (f == null || followerStrategies.containsKey(f)) {
			return false;
		}
		
		followerStrategies.put(f, ns);
		
		return true;
	}

    @Override
	public AnnouncementStrategy getAnnouncementStrategy(Follower f) {
		return followerStrategies.get(f);
	}

	@Override
	public void setAnnouncementStrategy(Follower f, AnnouncementStrategy ns) {
		if (f != null) {
			followerStrategies.put(f, ns);
		}
	}
}