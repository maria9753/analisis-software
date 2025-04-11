package aplicacion.anuncios;

import aplicacion.follower.FollowedEntity;
import aplicacion.follower.Follower;
import aplicacion.proyectos.Proyecto;

/**
 * La clase UnoCuandoSupereNStrategy representa las estrategias que mandan un anuncio cada vez que se supere un parámetro N.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class UnoCuandoSupereNStrategy implements AnnouncementStrategy {
	/** Parámetro N */
	private final int n;
	/** Bandera */
	private int flag;
	
	/**
	 * Constructor de la clase UnoCuandoSupereNStrategy.
	 * @param n Parámetro N de la estrategia.
	 */
	public UnoCuandoSupereNStrategy(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("El número debe ser mayor que 0");
		}
		
		this.n = n;
		flag = 0;
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
		if (followedEntity instanceof Proyecto == false) {
			throw new IllegalArgumentException("La entidad seguida debería ser un proyecto");
		}
		
		int numApoyos = ((Proyecto) followedEntity).getNumApoyos();
		
		if (numApoyos >= n && flag == 0) {
			flag = 1;
			return true;
		}
		
		if (numApoyos < n) {
		    flag = 0; 
		    return false;
		}
		
		return false;
	}
}
