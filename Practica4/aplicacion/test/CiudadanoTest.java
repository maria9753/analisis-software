package aplicacion.test;

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
import java.util.List;
import java.util.Map;

/**
 * Clase de prueba unitaria para la clase Ciudadano, usando JUnit 5.
 */
public class CiudadanoTest {

    /** Constructor vacío de la clase de prueba */
    public CiudadanoTest() {
        /** Constructor vacío */
    }

    private Aplicacion app;
    private Ciudadano ciudadano;
    private Asociacion asociacion;
    private Proyecto proyecto;
    private Proyecto proyectoViejo;

    /**
     * Configura los objetos necesarios antes de cada prueba.
     *
     * @throws NifInvalidoException si el NIF proporcionado es inválido
     * @throws RepresentanteInvalidoException si el representante de la asociación es inválido
     */
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

    /**
     * Prueba que el constructor de Ciudadano inicializa los campos correctamente.
     */
    @Test
    public void testConstructor() {
        assertEquals("Juan Perez", ciudadano.getNombre());
        assertEquals("12345678A", ciudadano.getNif());
        assertTrue(ciudadano.getAsociaciones().isEmpty());
        assertTrue(ciudadano.getProyectosApoyados().isEmpty());
        assertTrue(ciudadano.getMensajesAnuncios().isEmpty());
    }

    /**
     * Prueba que se lanza una excepción al crear un Ciudadano con NIF inválido.
     */
    @Test
    public void testConstructorNifInvalido() {
        try {
            new Ciudadano("Maria", "pass", app, "123");
            fail("Debería haber lanzado NifInvalidoException");
        } catch (NifInvalidoException e) {
        }
    }

    /**
     * Prueba que un ciudadano puede inscribirse en una asociación.
     */
    @Test
    public void testRegistrarAsociacion() {
        asociacion.inscribirCiudadano(ciudadano, null);
        assertTrue(ciudadano.getAsociaciones().contains(asociacion));
        assertEquals(1, ciudadano.getAsociaciones().size());
    }

    /**
     * Prueba que un ciudadano puede apoyar un proyecto válido.
     * 
     * @throws NifInvalidoException si el NIF es inválido
     * @throws ProponenteNoApoyaException si el ciudadano intenta apoyar su propio proyecto
     * @throws ProyectoMasDe60Exception si el proyecto tiene más de 60 días
     * @throws ProyectoYaApoyadoException si el proyecto ya fue apoyado anteriormente por el ciudadano
     */
    @Test
    public void testApoyarProyecto() throws NifInvalidoException, ProponenteNoApoyaException, ProyectoMasDe60Exception, ProyectoYaApoyadoException {
        Ciudadano otroCiudadano = new Ciudadano("Ana", "pass", app, "87654321B");
        Proyecto otroProyecto = new Proyecto("Biblioteca", "Ampliación biblioteca", otroCiudadano);

        ciudadano.apoyarProyecto(otroProyecto);

        Map<Proyecto, LocalDateTime> apoyados = ciudadano.getProyectosApoyados();
        assertTrue(apoyados.containsKey(otroProyecto));
        assertNotNull(apoyados.get(otroProyecto));
    }

    /**
     * Prueba que un ciudadano no puede apoyar su propio proyecto.
     */
    @Test
    public void testApoyarProyectoProponenteNoApoya() {
        try {
            ciudadano.apoyarProyecto(proyecto);
            fail("Debería haber lanzado ProponenteNoApoyaException");
        } catch (ProponenteNoApoyaException e) {
        } catch (Exception e) {
            fail("Lanzó una excepción diferente");
        }
    }

    /**
     * Prueba que un ciudadano no pueda apoyar el mismo proyecto dos veces.
     * 
     * @throws NifInvalidoException si el NIF es inválido
     * @throws ProponenteNoApoyaException si el ciudadano intenta apoyar su propio proyecto
     * @throws ProyectoMasDe60Exception si el proyecto tiene más de 60 días
     * @throws ProyectoYaApoyadoException si el proyecto ya fue apoyado anteriormente por el ciudadano
     */
    @Test
    public void testApoyarProyectoYaApoyado() throws NifInvalidoException, ProponenteNoApoyaException, ProyectoMasDe60Exception, ProyectoYaApoyadoException {
        Ciudadano otroCiudadano = new Ciudadano("Ana", "pass", app, "87654321B");
        Proyecto otroProyecto = new Proyecto("Biblioteca", "Ampliación biblioteca", otroCiudadano);

        ciudadano.apoyarProyecto(otroProyecto);

        try {
            ciudadano.apoyarProyecto(otroProyecto);
            fail("Debería haber lanzado ProyectoYaApoyadoException");
        } catch (ProyectoYaApoyadoException e) {
        }
    }

    /**
     * Prueba que no se pueda apoyar un proyecto con más de 60 días de antigüedad.
     * 
     * @throws NifInvalidoException si el NIF es inválido
     */
    @Test
    public void testApoyarProyectoMasDe60Dias() throws NifInvalidoException {
        try {
            Ciudadano ciudadano1 = new Ciudadano("Maria", "pass", app, "12345688A");
            ciudadano1.apoyarProyecto(proyectoViejo);
            fail("Debería haber lanzado ProyectoMasDe60Exception");
        } catch (ProyectoMasDe60Exception e) {
        } catch (Exception e) {
            fail("Lanzó una excepción diferente");
        }
    }

    /**
     * Prueba que un ciudadano pueda seguir a una asociación correctamente.
     */
    @Test
    public void testStartToFollow() {
        assertFalse(ciudadano.startToUnfollow(asociacion)); 
        assertTrue(ciudadano.startToFollow(asociacion, null));
        assertTrue(ciudadano.getFollowing().contains(asociacion));
        assertTrue(asociacion.getFollowers().contains(ciudadano));

        assertFalse(ciudadano.startToFollow(asociacion, null));

        asociacion.inscribirCiudadano(ciudadano, null);
        assertFalse(ciudadano.startToFollow(asociacion, null));
    }

    /**
     * Prueba que un ciudadano pueda dejar de seguir una asociación,
     * y que se controle cuando aún pertenece a ella.
     */
    @Test
    public void testStartToUnfollow() {
        ciudadano.startToFollow(asociacion, null);

        assertTrue(ciudadano.startToUnfollow(asociacion));
        assertFalse(ciudadano.getFollowing().contains(asociacion));
        assertFalse(asociacion.getFollowers().contains(ciudadano));

        assertFalse(ciudadano.startToUnfollow(asociacion));

        asociacion.inscribirCiudadano(ciudadano, null);
        ciudadano.startToFollow(asociacion, null);

        try {
            ciudadano.startToUnfollow(asociacion);
            fail("Debería haber lanzado IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("No puedes dejar de seguir una asociación a la que perteneces.", e.getMessage());
        }
    }

    /**
     * Prueba que un ciudadano pueda dejar de seguir una asociación después de darse de baja.
     */
    @Test
    public void testUnfollowAfterDarDeBaja() {
        asociacion.inscribirCiudadano(ciudadano, null);
        ciudadano.startToFollow(asociacion, null);

        asociacion.darDeBajaCiudadano(ciudadano);

        assertTrue(ciudadano.startToUnfollow(asociacion));
        assertFalse(ciudadano.getFollowing().contains(asociacion));
        assertFalse(asociacion.getFollowers().contains(ciudadano));
    }

    /**
     * Prueba que un ciudadano reciba correctamente los anuncios.
     */
    @Test
    public void testReceiveAnuncio() {
        Anuncio anuncio = new Anuncio("Nuevo parque en el barrio");
        ciudadano.receive(anuncio);

        List<String> mensajes = ciudadano.getMensajesAnuncios();
        assertTrue(mensajes.contains("Nuevo parque en el barrio"));
        assertEquals(1, mensajes.size());
    }

    /**
     * Prueba que el método toString del ciudadano devuelve el formato esperado.
     */
    @Test
    public void testToString() {
        String expected = "Juan PerezNIF (12345678A) <usuario>";
        assertEquals(expected, ciudadano.toString());
    }
}
