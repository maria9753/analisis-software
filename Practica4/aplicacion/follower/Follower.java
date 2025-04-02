package aplicacion.follower;

import aplicacion.anuncios.Anuncio;

/**
 * La interfaz Follower representa los tipos de seguidores y los anuncios que
 * reciben.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public interface Follower {
    /**
     * Método para recibir anuncios
     * 
     * @param t Anuncio que se recibe
     */
    public void receive(Anuncio t);
}
