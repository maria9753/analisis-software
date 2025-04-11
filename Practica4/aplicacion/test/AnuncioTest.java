package aplicacion.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import aplicacion.anuncios.Anuncio;

/**
 * Clase de prueba junit de anuncio.
 */
public class AnuncioTest {

    /**
     * Constructor de la clase.
     */
    public AnuncioTest(){
    	/**Constructor vacío*/
    }

    @Test
    void testConstructorYGetter() {
        String mensaje = "Este es un mensaje de prueba";
        Anuncio anuncio = new Anuncio(mensaje);
        
        assertNotNull(anuncio);
        assertEquals(mensaje, anuncio.getContenidoAnuncio());
    }

    @Test
    void testConstructorConNull() {
        Anuncio anuncio = new Anuncio(null);
        
        assertNotNull(anuncio);
        assertNull(anuncio.getContenidoAnuncio());
    }

    @Test
    void testConstructorConStringVacio() {
        Anuncio anuncio = new Anuncio("");
        
        assertNotNull(anuncio);
        assertEquals("", anuncio.getContenidoAnuncio());
    }

    @Test
    void testToString() {
        String mensaje = "Mensaje para toString";
        Anuncio anuncio = new Anuncio(mensaje);
        
        assertEquals(mensaje, anuncio.toString());
    }

    @Test
    void testContenidoLargo() {
        String mensajeLargo = "Este es un mensaje muy largo ".repeat(1000);
        Anuncio anuncio = new Anuncio(mensajeLargo);
        
        assertEquals(mensajeLargo, anuncio.getContenidoAnuncio());
    }

    @Test
    void testContenidoConCaracteresEspeciales() {
        String mensajeEspecial = "¡Mensaje con carácter€s €speciale$ y áccéntós!";
        Anuncio anuncio = new Anuncio(mensajeEspecial);
        
        assertEquals(mensajeEspecial, anuncio.getContenidoAnuncio());
    }
}