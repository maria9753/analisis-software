package aplicacion.follower;

import aplicacion.anuncios.AnnouncementStrategy;
import aplicacion.anuncios.Anuncio;

/**
 * La interfaz FollowerEntity permite comenzar y dejar de seguir a otros
 * usuarios.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public interface FollowedEntity {
    /**
     * Método para seguir a otros usuarios.
     * 
     * @param f Seguidor al que se empieza a seguir.
     * @return True si ha sido correcto, false si no.
     */
    public boolean follow(Follower f);

    /**
     * Método para dejar de seguir a otros usuarios.
     * 
     * @param f Seguidor al que se deja de seguir.
     * @return True si ha sido correcto, false si no.
     */
    public boolean unfollow(Follower f);

    /**
     * Método para recibir anuncios
     * 
     * @param t Anuncio que se recibe
     */
    public void announce(Anuncio t);

    /**
     * Método para seguir a otros usuarios según una estrategia.
     * 
     * @param f Seguidor al que se empieza a seguir.
     * @param ns Estrategia que se sigue.
     * @return True si ha sido correcto, false si no.
     */
    public boolean follow(Follower f, AnnouncementStrategy ns);

    /**
     * Método para obtener la estrategia de un seguidor.
     * 
     * @param f El seguidor.
     * @return La estrategia con la que sigue el seguidor a la entidad.
     */
	public AnnouncementStrategy getAnnouncementStrategy(Follower f);

	/**
	 * Método para modificar la estrategia de un seguidor.
	 * @param f El seguidor.
	 * @param ns La estrategia con la que sigue el seguidor a la entidad.
	 */
	public void setAnnouncementStrategy(Follower f, AnnouncementStrategy ns);
}
