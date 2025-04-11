package aplicacion.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aplicacion.*;
import aplicacion.anuncios.*;
import aplicacion.usuarios.*;
import aplicacion.exceptions.*;
import aplicacion.proyectos.*;

public class FundacionTest {

    private Aplicacion app;
    private Fundacion fundacion;
    private ProyectoFundacion proyecto;
    private Ciudadano seguidor;
    private Ciudadano seguidor2;

    @BeforeEach
    void setUp() throws CifInvalidoException, NifInvalidoException {
        app = new Aplicacion();
        fundacion = new Fundacion("Fundacion Test", "password", app, "A1234798B");
        proyecto = new ProyectoFundacion("Proyecto Social", "Ayuda a comunidades", fundacion, 1000.0, 80.0);
        seguidor = new Ciudadano("Juan Perez", "pass123", app, "12345678Z");
        seguidor2 = new Ciudadano("Maria Garcia", "pass456", app, "87654321X");
    }

    @Test
    void testConstructor() {
        assertEquals("Fundacion Test", fundacion.getNombre());
        assertEquals("A1234798B", fundacion.getCif());
        assertEquals("password", fundacion.getContrasena());
        assertTrue(fundacion.toString().contains("Fundacion Test"));
        assertTrue(fundacion.toString().contains("A1234798B"));
    }


    @Test
    void testConstructorCifInvalido() {
        try {
            new Fundacion("Fundacion Invalida", "pass", app, "A123"); 
            fail("Debería haber lanzado CifInvalidoException para CIF corto");
        } catch (CifInvalidoException e) {
            // Test pass
        }
        try {
            new Fundacion("Fundacion Invalida", "pass", app, null);
            fail("Debería haber lanzado CifInvalidoException para CIF null");
        } catch (CifInvalidoException e) {
            // Test pass
        }
        try {
            new Fundacion("Fundacion Invalida", "pass", app, "A12345678");
            fail("Debería haber lanzado CifInvalidoException para CIF con letra final inválida");
        } catch (CifInvalidoException e) {
            // Test pass
        }
    }

	@Test
	void testFollowUnfollow() {
		assertTrue(fundacion.follow(seguidor));
		assertTrue(fundacion.getFollowers().contains(seguidor));
	
		assertFalse(fundacion.follow(seguidor));
		
		assertTrue(fundacion.unfollow(seguidor));
		assertFalse(fundacion.getFollowers().contains(seguidor));
		
		assertTrue(fundacion.unfollow(seguidor));
	}

    @Test
    void testMultipleFollowers() {
        fundacion.follow(seguidor);
        fundacion.follow(seguidor2);
        
        assertEquals(2, fundacion.getFollowers().size());
        assertTrue(fundacion.getFollowers().contains(seguidor));
        assertTrue(fundacion.getFollowers().contains(seguidor2));
    }

    @Test
    void testProponerProyecto() {
        fundacion.proponerProyecto(proyecto);
        
        Map<Proyecto, Set<Ciudadano>> proyectosMap = app.obtenerProyectosYCiudadanosQueLosApoyan();
        assertTrue(proyectosMap.containsKey(proyecto));
    }
    
    @Test
    void testProponerProyectoNull() {
        try {
            fundacion.proponerProyecto(null);
            fail("Debería haber lanzado IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Test pass
        }
    }
    @Test
    void testAnnounce() {
        fundacion.follow(seguidor);
        Anuncio anuncio = new Anuncio("Nuevo programa educativo");
        fundacion.announce(anuncio);
        
        List<String> mensajes = new ArrayList<>(seguidor.getMensajesAnuncios());
        assertFalse(mensajes.isEmpty());
        assertTrue(mensajes.get(0).contains("Nuevo programa educativo"));
    }

    @Test
    void testAnnounceMultipleFollowers() {
        fundacion.follow(seguidor);
        fundacion.follow(seguidor2);
        Anuncio anuncio = new Anuncio("Anuncio para todos");
        fundacion.announce(anuncio);
       
        List<String> mensajesSeguidor1 = new ArrayList<>(seguidor.getMensajesAnuncios());
        List<String> mensajesSeguidor2 = new ArrayList<>(seguidor2.getMensajesAnuncios());
        
        assertFalse(mensajesSeguidor1.isEmpty());
        assertTrue(mensajesSeguidor1.get(0).contains("Anuncio para todos"));
        
        assertFalse(mensajesSeguidor2.isEmpty());
        assertTrue(mensajesSeguidor2.get(0).contains("Anuncio para todos"));
    }

    @Test
    void testAnuncioPropuestaProyecto() {
        fundacion.follow(seguidor);
        fundacion.anuncioPropuestaProyecto("Parque infantil", "Construcción de área de juegos");
        
        assertTrue(seguidor.getMensajesAnuncios().stream()
            .anyMatch(m -> m.contains("Parque infantil") || m.contains("propone el proyecto")));
    }

    @Test
    void testToString() throws CifInvalidoException {
        String expected = "Fundacion TestCIF (A1234798B) <fundacion>";
        assertEquals(expected, fundacion.toString());
        
        Fundacion otraFundacion = new Fundacion("Otra Fundacion", "pass", app, "B8765432C");
        assertEquals("Otra FundacionCIF (B8765432C) <fundacion>", otraFundacion.toString());
    }
}