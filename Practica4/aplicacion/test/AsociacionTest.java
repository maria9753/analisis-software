package aplicacion.test;

import aplicacion.*;
import aplicacion.exceptions.*;
import aplicacion.proyectos.*;
import aplicacion.usuarios.*;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AsociacionTest {
    
    private Aplicacion app;
    private Ciudadano representante;
    private Asociacion asociacion;
    private Proyecto proyecto;
    private Ciudadano ciudadano1;
    private Ciudadano ciudadano2;
    private Asociacion subAsociacion;
    
    @BeforeEach
    void setUp() throws RepresentanteInvalidoException, NifInvalidoException {
        app = new Aplicacion();
        representante = new Ciudadano("Representante", "pass", app, "01234567K");
        ciudadano1 = new Ciudadano("Ciudadano1", "pass1", app, "01234567L");
        ciudadano2 = new Ciudadano("Ciudadano2", "pass2", app, "01234567G");
        
        asociacion = new Asociacion("Asociacion Principal", "pass", app, representante);
        subAsociacion = new Asociacion("Sub-Asociacion", "pass", app, representante);
        
        proyecto = new Proyecto("Proyecto Test", "Descripción", representante);
    }
    
    @Test
    void testConstructor() {
        assertEquals("Asociacion Principal", asociacion.getNombre());
        assertEquals(representante, asociacion.getRepresentante());
        assertTrue(asociacion.getCiudadanosDirectos().contains(representante));
        assertEquals(1, asociacion.getCiudadanosDirectos().size());
        assertTrue(asociacion.getAsociaciones().isEmpty());
        assertTrue(asociacion.getProyectosApoyados().isEmpty());
    }
    
    @Test
    void testInscribirCiudadano() {
        asociacion.inscribirCiudadano(ciudadano1);
        assertTrue(asociacion.getCiudadanosDirectos().contains(ciudadano1));
        assertEquals(2, asociacion.getCiudadanos().size());
        
        asociacion.inscribirCiudadano(ciudadano1);
        assertEquals(2, asociacion.getCiudadanos().size());
    }
    
    @Test
    void testDarDeBajaCiudadano() {
        asociacion.inscribirCiudadano(ciudadano1);
        asociacion.darDeBajaCiudadano(ciudadano1);
        assertFalse(asociacion.getCiudadanosDirectos().contains(ciudadano1));
        assertEquals(1, asociacion.getCiudadanos().size());
    }
    
    @Test
    void testAnadirAsociacion() throws RepresentanteInvalidoException {
        asociacion.anadirAsociacion(subAsociacion);
        assertTrue(asociacion.getAsociaciones().contains(subAsociacion));
        assertEquals(1, asociacion.getAsociaciones().size());
    }
    
    @Test
    void testAnadirAsociacionRepresentanteInvalido() throws NifInvalidoException, RepresentanteInvalidoException {
        Ciudadano otroRepresentante = new Ciudadano("Otro", "pass", app, "01224567G");
        Asociacion otraAsociacion = new Asociacion("Otra", "pass", app, otroRepresentante);
        
        try {
            asociacion.anadirAsociacion(otraAsociacion);
            fail("Debería haber lanzado RepresentanteInvalidoException");
        } catch (RepresentanteInvalidoException e) {
            // Test pasa
        }
    }
    
    @Test
    void testProponerProyecto() throws ProponenteNoApoyaException, ProyectoMasDe60Exception, ProyectoYaApoyadoException {
        asociacion.proponerProyecto(proyecto);
        assertEquals(1, proyecto.getNumApoyos());
        assertTrue(asociacion.getProyectosApoyados().containsKey(proyecto));
    }
    
    @Test
    void testApoyarProyecto() throws ProponenteNoApoyaException, ProyectoMasDe60Exception, 
            ProyectoYaApoyadoException, NifInvalidoException {
        Ciudadano otroCiudadano = new Ciudadano("Otro", "pass", app, "02234567G");
        Proyecto otroProyecto = new Proyecto("Otro Proyecto", "Desc", otroCiudadano);
        asociacion.apoyarProyecto(otroProyecto);
        assertTrue(asociacion.getProyectosApoyados().containsKey(otroProyecto));
        assertEquals(1, otroProyecto.getNumApoyos());
    }
    
    @Test
    void testApoyarProyectoProponenteNoApoya() {
        Proyecto propioProyecto = new Proyecto("Propio", "Desc", asociacion);
        
        try {
            asociacion.apoyarProyecto(propioProyecto);
            fail("Debería haber lanzado ProponenteNoApoyaException");
        } catch (ProponenteNoApoyaException e) {
            // Test pasa
        } catch (ProyectoMasDe60Exception | ProyectoYaApoyadoException e) {
            fail("Lanzó excepción incorrecta");
        }
    }
    
    @Test
    void testApoyarProyectoYaApoyado() throws ProponenteNoApoyaException, ProyectoMasDe60Exception, 
            ProyectoYaApoyadoException, NifInvalidoException {
        Ciudadano otroCiudadano = new Ciudadano("Otro", "pass", app, "02234567G");
        Proyecto otroProyecto = new Proyecto("Otro Proyecto", "Desc", otroCiudadano);
        asociacion.apoyarProyecto(otroProyecto);
        
        try {
            asociacion.apoyarProyecto(otroProyecto);
            fail("Debería haber lanzado ProyectoYaApoyadoException");
        } catch (ProyectoYaApoyadoException e) {
            // Test pasa
        }
    }
    
    @Test
    void testApoyarProyectoMasDe60Dias() throws NifInvalidoException {
        Ciudadano otroCiudadano = new Ciudadano("Otro", "pass", app, "02234567G");
        Proyecto proyectoViejo = new Proyecto("Viejo", "Desc", otroCiudadano) {
            @Override
            public LocalDateTime getFechaCreacion() {
                return LocalDateTime.now().minusDays(61);
            }
        };
        
        try {
            asociacion.apoyarProyecto(proyectoViejo);
            fail("Debería haber lanzado ProyectoMasDe60Exception");
        } catch (ProyectoMasDe60Exception e) {
            /** Test pass*/
        } catch (ProponenteNoApoyaException | ProyectoYaApoyadoException e) {
            fail("Lanzó excepción incorrecta");
        }
    }
    
    @Test
    void testGetCiudadanosConSubasociaciones() throws RepresentanteInvalidoException {
        asociacion.inscribirCiudadano(ciudadano1);
        subAsociacion.inscribirCiudadano(ciudadano2);
        asociacion.anadirAsociacion(subAsociacion);
        
        Set<Ciudadano> ciudadanos = asociacion.getCiudadanos();
        assertEquals(3, ciudadanos.size()); 
        assertTrue(ciudadanos.contains(representante));
        assertTrue(ciudadanos.contains(ciudadano1));
        assertTrue(ciudadanos.contains(ciudadano2));
    }
    
    @Test
    void testComprobarCiudadano() {
        asociacion.inscribirCiudadano(ciudadano1);
        assertTrue(asociacion.comprobarCiudadano(ciudadano1));
        assertFalse(asociacion.comprobarCiudadano(ciudadano2));
    }
    
    @Test
    void testFollowUnfollow() throws NifInvalidoException {
        Ciudadano seguidor = new Ciudadano("Seguidor", "pass", app, "03234567G");
        assertTrue(asociacion.follow(seguidor));
        assertTrue(asociacion.getFollowers().contains(seguidor));
        assertTrue(asociacion.unfollow(seguidor));
        assertFalse(asociacion.getFollowers().contains(seguidor));
    }
    
    @Test
    void testToString() {
        asociacion.inscribirCiudadano(ciudadano1);
        String expected = "Asociacion Principal <asociacion con 2 ciudadanos>";
        assertEquals(expected, asociacion.toString());
    }
}