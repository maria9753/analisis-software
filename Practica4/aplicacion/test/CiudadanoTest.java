package aplicacion.test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aplicacion.*;
import aplicacion.anuncios.*;
import aplicacion.exceptions.*;
import aplicacion.usuarios.*;
import aplicacion.proyectos.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Set;

public class CiudadanoTest {

    private Aplicacion app;
    private Ciudadano ciudadano;
    private Asociacion asociacion;
    private Proyecto proyecto;
    private Proyecto proyectoViejo;

    @BeforeEach
    public void setUp() throws NifInvalidoException, RepresentanteInvalidoException {
        app = new Aplicacion();
        ciudadano = new Ciudadano("Juan Perez", "password", app, "12345678A");
        asociacion = new Asociacion("Asociacion Vecinal", "pass", app, ciudadano);
       
        proyecto = new Proyecto("Parque infantil", "Construcción de área de juegos", ciudadano);

        proyectoViejo = new Proyecto("Piscina municipal", "Construcción de piscina", ciudadano) {
            @Override
            public LocalDateTime getFechaCreacion() {
                return LocalDateTime.now().minus(61, ChronoUnit.DAYS);
            }
        };
    }

    @Test
    public void testConstructor() {
        assertEquals("Juan Perez", ciudadano.getNombre());
        assertEquals("12345678A", ciudadano.getNif());
        assertTrue(ciudadano.getAsociaciones().isEmpty());
        assertTrue(ciudadano.getProyectosApoyados().isEmpty());
        assertTrue(ciudadano.getMensajesAnuncios().isEmpty());
    }

    @Test
    public void testConstructorNifInvalido() {
        try {
            new Ciudadano("Maria", "pass", app, "123");
            fail("Debería haber lanzado NifInvalidoException");
        } catch (NifInvalidoException e) {
            // Test pasa
        }
    }

    @Test
    public void testRegistrarAsociacion() {
        ciudadano.registarAsociacion(asociacion);
        assertTrue(ciudadano.getAsociaciones().contains(asociacion));
        assertEquals(1, ciudadano.getAsociaciones().size());
    }

    @Test
    public void testApoyarProyecto() throws ProponenteNoApoyaException, ProyectoMasDe60Exception, ProyectoYaApoyadoException, NifInvalidoException {
        Ciudadano otroCiudadano = new Ciudadano("Ana", "pass", app, "87654321B");
        Proyecto otroProyecto = new Proyecto("Biblioteca", "Ampliación biblioteca", otroCiudadano);
        
        ciudadano.apoyarProyecto(otroProyecto);
        
        Map<Proyecto, LocalDateTime> apoyados = ciudadano.getProyectosApoyados();
        assertTrue(apoyados.containsKey(otroProyecto));
        assertNotNull(apoyados.get(otroProyecto));
    }

    @Test
    public void testApoyarProyectoProponenteNoApoya() {
        try {
            ciudadano.apoyarProyecto(proyecto);
            fail("Debería haber lanzado ProponenteNoApoyaException");
        } catch (ProponenteNoApoyaException e) {
            // Test pasa
        } catch (Exception e) {
            fail("Lanzó una excepción diferente a ProponenteNoApoyaException");
        }
    }

    @Test
    public void testApoyarProyectoYaApoyado() throws ProponenteNoApoyaException, ProyectoMasDe60Exception, ProyectoYaApoyadoException, NifInvalidoException {
        Ciudadano otroCiudadano = new Ciudadano("Ana", "pass", app, "87654321B");
        Proyecto otroProyecto = new Proyecto("Biblioteca", "Ampliación biblioteca", otroCiudadano);
        
        ciudadano.apoyarProyecto(otroProyecto);
        
        try {
            ciudadano.apoyarProyecto(otroProyecto);
            fail("Debería haber lanzado ProyectoYaApoyadoException");
        } catch (ProyectoYaApoyadoException e) {
            // Test pasa
        }
    }

    @Test
    public void testApoyarProyectoMasDe60Dias() {
        try {
        	Ciudadano ciudadano1= new Ciudadano("Maria", "pass", app, "12345688A");
        	ciudadano1.apoyarProyecto(proyectoViejo);
            fail("Debería haber lanzado ProyectoMasDe60Exception");
        } catch (ProyectoMasDe60Exception e) {
            // Test pasa
        } catch (Exception e) {
            fail("Lanzó una excepción diferente a ProyectoMasDe60Exception");
            e.printStackTrace();
        }
    }

    @Test
    public void testStartToFollow() {
    	assertTrue(ciudadano.startToUnfollow(asociacion));
        assertTrue(ciudadano.startToFollow(asociacion));
        assertTrue(ciudadano.getFollowing().contains(asociacion));
        assertTrue(asociacion.getFollowers().contains(ciudadano));
        
        assertFalse(ciudadano.startToFollow(asociacion));
        
        ciudadano.registarAsociacion(asociacion);
        assertFalse(ciudadano.startToFollow(asociacion));
    }

    @Test
    public void testStartToUnfollow() {
        ciudadano.startToFollow(asociacion);
   
        assertTrue(ciudadano.startToUnfollow(asociacion));
        assertFalse(ciudadano.getFollowing().contains(asociacion));
        assertFalse(asociacion.getFollowers().contains(ciudadano));
     
        assertFalse(ciudadano.startToUnfollow(asociacion));
        
    
        ciudadano.registarAsociacion(asociacion);
        ciudadano.startToFollow(asociacion);
        
        try {
            ciudadano.startToUnfollow(asociacion);
            fail("Debería haber lanzado IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("No puedes dejar de seguir una asociación a la que perteneces.", e.getMessage());
        }
    }

    @Test
    public void testUnfollowAfterDarDeBaja() {
        ciudadano.registarAsociacion(asociacion);
        ciudadano.startToFollow(asociacion);

        asociacion.darDeBajaCiudadano(ciudadano);
  
        assertTrue(ciudadano.startToUnfollow(asociacion));
        assertFalse(ciudadano.getFollowing().contains(asociacion));
        assertFalse(asociacion.getFollowers().contains(ciudadano));
    }

    @Test
    public void testReceiveAnuncio() {
        Anuncio anuncio = new Anuncio("Nuevo parque en el barrio");
        ciudadano.receive(anuncio);
        
        Set<String> mensajes = ciudadano.getMensajesAnuncios();
        assertTrue(mensajes.contains("Nuevo parque en el barrio"));
        assertEquals(1, mensajes.size());
    }

    @Test
    public void testToString() {
        String expected = "Juan PerezNIF (12345678A) <usuario>";
        assertEquals(expected, ciudadano.toString());
    }
}