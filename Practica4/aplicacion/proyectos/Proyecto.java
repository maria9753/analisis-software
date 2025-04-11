package aplicacion.proyectos;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import aplicacion.anuncios.AnnouncementStrategy;
import aplicacion.anuncios.Anuncio;
import aplicacion.follower.Follower;
import aplicacion.follower.FollowedEntity;
import aplicacion.usuarios.*;

/**
 * La clase Proyecto representa los proyectos que proponen las asociaciones y
 * los ciudadanos.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class Proyecto implements FollowedEntity {
    /** Contador para generar códigos */
    private static int contador = 0;
    /** Título del proyecto */
    private String titulo;
    /** Descripción del proyecto */
    private String descripcion;
    /** Proponente del proyecto */
    private Usuario proponente;
    /** Fecha de creación del proyecto */
    private LocalDateTime fechaCreacion;
    /** Código del proyecto */
    private int codigo;
    /** Número de apoyos */
    private int numApoyos;
    /** Seguidores y sus estrategias */
    private Map<Follower, AnnouncementStrategy> followerStrategies;

    /**
     * Constructor de la clase Proyecto.
     * 
     * @param titulo      Nombre del proyecto.
     * @param descripcion Descripción del proyecto.
     * @param proponente  Usuario que propone el proyecto.
     */
    public Proyecto(String titulo, String descripcion, Usuario proponente) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.proponente = proponente;
        this.codigo = contador++;
        this.fechaCreacion = LocalDateTime.now();
        this.numApoyos = 0;
        this.followerStrategies = new HashMap<>();
    }

    /**
     * Método para obtener el nombre del proyecto.
     * 
     * @return El título del proyecto.
     */
    public String getNombre() {
        return this.titulo;
    }

    /**
     * Método para obtener el código del proyecto.
     * 
     * @return El código único del proyecto.
     */
    public int getCodigo() {
        return this.codigo;
    }

    /**
     * Método para obtener la descripción del proyecto.
     * 
     * @return La descripción del proyecto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método para obtener el proponente del proyecto.
     * 
     * @return El proponente del proyecto.
     */
    public Usuario getProponente() {
        return proponente;
    }

    /**
     * Método para obtener la fecha de creación del proyecto.
     * 
     * @return La fecha de creación del proyecto.
     */
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Método para añadir un apoyo.
     */
    public void addApoyo() {
        this.numApoyos++;
    }

    /**
     * Método para quitar un apoyo.
     */
    public void removeApoyo() {
        this.numApoyos--;
    }

    /**
     * Sobrescribe el método toString para representar el proyecto como una cadena.
     * 
     * @return Una cadena del proyecto.
     */
    @Override
    public String toString() {
        return codigo + ": " + titulo + ". Proponente: " + proponente;
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
		if (ns == null || followerStrategies.containsKey(f)) {
			return false;
		}
		
		followerStrategies.put(f, ns);
		return true;
	}

    /**
     * Método para seguir a otros usuarios.
     * 
     * @param f Seguidor al que se empieza a seguir.
     * @return True si ha sido correcto, false si no.
     */
	@Override
	public boolean follow(Follower f) {
		if (f == null) {
			return false;
		}
		
        followerStrategies.put(f, null);
        
        return true;
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
    	
        return followerStrategies.remove(f) != null;
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
    	
    	for (Map.Entry<Follower, AnnouncementStrategy> entry : followerStrategies.entrySet()) {
            f = entry.getKey();
            ns = entry.getValue();
            
            if (ns == null || ns.enviarOnoEnviar(t, f, this)) {
                f.receive(t);
            }
        }
    }

    /**
     * Método para obtener el número de apoyos de un proyecto.
     * @return El número de apoyos.
     */
	public int getNumApoyos() {
		return this.numApoyos;
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
