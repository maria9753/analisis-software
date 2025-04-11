package aplicacion.anuncios;

import aplicacion.follower.FollowedEntity;
import aplicacion.follower.Follower;

public class UnoDeCadaNStrategy implements AnnouncementStrategy {
	private final int n;
	private int counter;
	
	public UnoDeCadaNStrategy(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("El número debe ser mayor que 0");
		}
		
		this.n = n;
		this.counter = 0;
	}

	@Override
	public boolean enviarOnoEnviar(Anuncio anuncio, Follower follower, FollowedEntity followedEntity) {
		int aux = counter;
		counter++;
		if (aux == n || aux == 0) {
			counter = 0;
			return true;
		}
		
		return false;
	}
}
