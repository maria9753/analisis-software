package aplicacion.anuncios;

import aplicacion.follower.FollowedEntity;
import aplicacion.follower.Follower;

/**
 * La clase UnoDeCadaNStrategy representa las estrategias que mandan un anuncio una vez cada parámetro N veces.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class UnoDeCadaNStrategy implements AnnouncementStrategy {
	/** Parámetro N */
	private final int n;
	/** Contador */
	private int counter;
	
	/**
	 * Constructor de la clase UnoDeCadaNStrategy.
	 * @param n Parámetro N de la estrategia.
	 */
	public UnoDeCadaNStrategy(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("El número debe ser mayor que 0");
		}
		
		this.n = n;
		this.counter = 0;
	}
	
	/**
	 * Método para decidir si enviar o no un anuncio según una estrategia.
	 * 
	 * @param anuncio Anuncio que se quiere enviar.
	 * @param follower Seguidor al que le va a llegar el anuncio.
	 * @param followedEntity Entidad a la que se sigue.
	 * @return True si se puede enviar, false si no.
	 */
	@Override
	public boolean enviarOnoEnviar(Anuncio anuncio, Follower follower, FollowedEntity followedEntity) {
		counter++;
        if (counter % n == 1 || n == 1) {  
            return true;
        }
        return false;
	}
}
