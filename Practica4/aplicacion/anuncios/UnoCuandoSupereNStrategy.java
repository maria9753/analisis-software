package aplicacion.anuncios;

import aplicacion.follower.FollowedEntity;
import aplicacion.follower.Follower;
import aplicacion.proyectos.Proyecto;

public class UnoCuandoSupereNStrategy implements AnnouncementStrategy {
	private final int n;
	private int flag;
	
	public UnoCuandoSupereNStrategy(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("El número debe ser mayor que 0");
		}
		
		this.n = n;
		flag = 0;
	}
	
	
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
		
		return false;
	}
}
