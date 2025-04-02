package aplicacion.anuncios;

/**
 * La clase Anuncio define los anuncios que se muestran a los usuarios.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class Anuncio {
    /** Cadena con el contenido del anuncio */
    private String contenidoAnuncio;

    /**
     * Constructor de Anuncio.
     * 
     * @param contenido Contenido que muestra el anuncio.
     */
    public Anuncio(String contenido) {
        this.contenidoAnuncio = contenido;
    }

    /**
     * Método que devuelve el contenido del anuncio.
     * 
     * @return El contenido del anuncio.
     */
    public String getContenidoAnuncio() {
        return contenidoAnuncio;
    }

    /**
     * Método que sobreescribe toString de manera que devuelve el contenido del
     * anuncio.
     * 
     * @return El contenido del anuncio.
     */
    @Override
    public String toString() {
        return contenidoAnuncio;
    }
}
