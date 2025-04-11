package aplicacion.anuncios;

import aplicacion.follower.FollowedEntity;
import aplicacion.follower.Follower;

/**
 * La interfaz AnnouncementStrategy permite recibir anuncios mediante una estrategia.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public interface AnnouncementStrategy {
	/**
	 * Método para decidir si enviar o no un anuncio según una estrategia.
	 * 
	 * @param anuncio Anuncio que se quiere enviar.
	 * @param follower Seguidor al que le va a llegar el anuncio.
	 * @param followedEntity Entidad a la que se sigue.
	 * @return True si se puede enviar, false si no.
	 */
	public boolean enviarOnoEnviar(Anuncio anuncio, Follower follower, FollowedEntity followedEntity);
}
