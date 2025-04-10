package aplicacion.anuncios;

import aplicacion.follower.FollowedEntity;
import aplicacion.follower.Follower;

/**
 * La interfaz AnnouncementStrategy permite recibir anuncios mediante una estrategia.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public interface AnnouncementStrategy {
	public boolean enviarOnoEnviar(Anuncio anuncio, Follower follower, FollowedEntity followedEntity);
}
