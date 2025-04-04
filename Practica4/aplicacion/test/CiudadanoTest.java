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
import java.util.Map;
import java.util.Set;

public class CiudadanoTest {

    private Aplicacion app;
    private Ciudadano ciudadano;
    private Asociacion asociacion;
    private Proyecto proyecto;
    private Proyecto proyectoViejo;

    @BeforeEach
    void setUp() throws NifInvalidoException, RepresentanteInvalidoException {
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
    void testConstructor() {
        assertEquals("Juan Perez", ciudadano.getNombre());
        assertEquals("12345678A", ciudadano.getNif());
        assertTrue(ciudadano.getAsociaciones().isEmpty());
        assertTrue(ciudadano.getProyectosApoyados().isEmpty());
        assertTrue(ciudadano.getMensajesAnuncios().isEmpty());
    }

    @Test
    void testConstructorNifInvalido() {
        assertThrows(NifInvalidoException.class, () -> {
            new Ciudadano("Maria", "pass", app, "123");
        });
    }

    @Test
    void testRegistrarAsociacion() {
        ciudadano.registarAsociacion(asociacion);
        assertTrue(ciudadano.getAsociaciones().contains(asociacion));
        assertEquals(1, ciudadano.getAsociaciones().size());
    }

    @Test
    void testApoyarProyecto() throws ProponenteNoApoyaException, ProyectoMasDe60Exception, ProyectoYaApoyadoException, NifInvalidoException {
        Ciudadano otroCiudadano = new Ciudadano("Ana", "pass", app, "87654321B");
        Proyecto otroProyecto = new Proyecto("Biblioteca", "Ampliación biblioteca", otroCiudadano);
        
        ciudadano.apoyarProyecto(otroProyecto);
        
        Map<Proyecto, LocalDateTime> apoyados = ciudadano.getProyectosApoyados();
        assertTrue(apoyados.containsKey(otroProyecto));
        assertNotNull(apoyados.get(otroProyecto));
    }

    @Test
    void testApoyarProyectoProponenteNoApoya() {
        assertThrows(ProponenteNoApoyaException.class, () -> {
            ciudadano.apoyarProyecto(proyecto);
        });
    }

    @Test
    void testApoyarProyectoYaApoyado() throws ProponenteNoApoyaException, ProyectoMasDe60Exception, ProyectoYaApoyadoException, NifInvalidoException {
        Ciudadano otroCiudadano = new Ciudadano("Ana", "pass", app, "87654321B");
        Proyecto otroProyecto = new Proyecto("Biblioteca", "Ampliación biblioteca", otroCiudadano);
        
        ciudadano.apoyarProyecto(otroProyecto);
        
        assertThrows(ProyectoYaApoyadoException.class, () -> {
            ciudadano.apoyarProyecto(otroProyecto); 
        });
    }

    @Test
    void testApoyarProyectoMasDe60Dias() {
        assertThrows(ProyectoMasDe60Exception.class, () -> {
            ciudadano.apoyarProyecto(proyectoViejo); 
        });
    }

    @Test
    void testStartToFollow() {
        assertTrue(ciudadano.startToFollow(asociacion));
        assertFalse(ciudadano.startToFollow(asociacion)); 
    }

    @Test
    void testStartToUnfollow() {
        ciudadano.startToFollow(asociacion);
        assertTrue(ciudadano.startToUnfollow(asociacion));
        assertFalse(ciudadano.startToUnfollow(asociacion));
    }

    @Test
    void testReceiveAnuncio() {
        Anuncio anuncio = new Anuncio("Nuevo parque en el barrio");
        ciudadano.receive(anuncio);
        
        Set<String> mensajes = ciudadano.getMensajesAnuncios();
        assertTrue(mensajes.contains("Nuevo parque en el barrio"));
        assertEquals(1, mensajes.size());
    }

    @Test
    void testToString() {
        String expected = "Juan PerezNIF (12345678A) <usuario>";
        assertEquals(expected, ciudadano.toString());
    }
}