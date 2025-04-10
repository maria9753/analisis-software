package aplicacion.anuncios;

import aplicacion.follower.FollowedEntity;
import aplicacion.follower.Follower;

public class UnoDeCadaNStrategy implements AnnouncementStrategy {
	private int n;
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
		counter++;
		if (counter == n) {
			counter = 0;
			return true;
		}
		
		return false;
	}
	
	public void setN(int n) {
		this.n = n;
	}
}
