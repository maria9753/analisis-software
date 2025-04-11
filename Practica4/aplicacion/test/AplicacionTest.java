package aplicacion.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aplicacion.Aplicacion;
import aplicacion.exceptions.*;
import aplicacion.usuarios.*;
import aplicacion.proyectos.Proyecto;

public class AplicacionTest {
    
    private Aplicacion app;
    private Ciudadano ciudadano;
    private Asociacion asociacion;
    private Fundacion fundacion;
    private Proyecto proyecto;

    @BeforeEach
    void setUp() throws Exception {
        app = new Aplicacion();
        ciudadano = new Ciudadano("Juan", "pass123", app, "12345678A");
        asociacion = new Asociacion("Asociacion Test", "pass456", app, ciudadano);
        fundacion = new Fundacion("Fundacion Test", "pass789", app, "A1234567B");
        proyecto = new Proyecto("Proyecto Test", "Descripción", ciudadano);
    }

    @Test
    void testConstructor() {
        assertNotNull(app);
        assertFalse(app.obtenerTodosLosCiudadanos().isEmpty());
        assertTrue(app.obtenerTodosLosProyectos().isEmpty());
    }

    @Test
    void testRegistrarCiudadano() throws NifInvalidoException {
        assertEquals(1, app.obtenerTodosLosCiudadanos().size());
        assertEquals(ciudadano, app.obtenerCiudadanoPorNombre("Juan"));
    }

    @Test
    void testRegistrarCiudadanoNifInvalido() {
    	try {
        	app.registrarCiudadano(new Ciudadano("MAria", "contra", app, "12345678")); 
        	fail("deberia lanzar NifInvalidoException");
        } catch (Exception e) {
        	//test pass
        }
    }

    @Test
    void testRegistrarAsociacion() throws RepresentanteInvalidoException {
    	assertEquals(1, app.obtenerTodasLasAsociaciones().size());
        assertTrue(app.obtenerTodasLasAsociaciones().contains(asociacion));
    }

    @Test
    void testRegistrarFundacion() throws CifInvalidoException {
    	assertEquals(1, app.obtenerTodasLasFundaciones().size());
        assertTrue(app.obtenerTodasLasFundaciones().contains(fundacion));
    }

    @Test
    void testValidarNif() throws NifInvalidoException {
        try {
        	app.validarNif("12345678A"); 
        } catch (Exception e) {
        	fail("no deberia lanzar NifInvalidoException");
        }
        
        try {
        	app.validarNif("12345678"); 
        	fail("debería haber lanzado NifInvalidoException");
        } catch (Exception e) {
        	//test pass
        }
        
        try {
        	app.validarNif("123456789"); 
        	fail("debería haber lanzado NifInvalidoException");
        } catch (Exception e) {
        	//test pass
        } 
    }

    @Test
    void testValidarCif() throws CifInvalidoException {
    	try {
        	app.validarCif("A1234567B"); 
        } catch (Exception e) {
        	fail("no deberia lanzar NifInvalidoException");
        }
        
        try {
        	app.validarCif("12345678A"); 
        	fail("debería haber lanzado NifInvalidoException");
        } catch (Exception e) {
        	//test pass
        }
        
        try {
        	app.validarCif("A12345678"); 
        	fail("debería haber lanzado NifInvalidoException");
        } catch (Exception e) {
        	//test pass
        } 
    }

    @Test
    void testProponerProyecto() {
        app.proponerProyecto(proyecto);
        assertEquals(1, app.obtenerTodosLosProyectos().size());
        assertEquals(proyecto, app.obtenerProyectoPorNombre("Proyecto Test"));
    }

    @Test
    void testObtenerProyectosConApoyosOrdenados() throws NifInvalidoException, ProponenteNoApoyaException, ProyectoMasDe60Exception, ProyectoYaApoyadoException, RepresentanteInvalidoException {   
        Ciudadano c1 = new Ciudadano("Ana", "pass", app, "87654321B");
        Proyecto p1 = new Proyecto("Proyecto 1", "Desc 1", ciudadano);
        Proyecto p2 = new Proyecto("Proyecto 2", "Desc 2", ciudadano);
        
        asociacion.proponerProyecto(p1);
        asociacion.proponerProyecto(p2);
        c1.apoyarProyecto(p1);
        
        
        Map<Proyecto, Integer> apoyos = app.obtenerProyectosConApoyosOrdenados();
        assertEquals(2, apoyos.get(p1));
        assertEquals(1, apoyos.get(p2)); 
        assertTrue(apoyos.keySet().iterator().next().equals(p1)); 
    }

    @Test
    void testObtenerProyectosYCiudadanosQueLosApoyan() throws NifInvalidoException {
        Ciudadano c1 = new Ciudadano("Ana", "pass", app, "87654321B");
        Ciudadano c2 = new Ciudadano("Ana", "pass", app, "87654321C");
        
        Proyecto p1 = new Proyecto("Proyecto 1", "Desc 1", ciudadano);
        asociacion.proponerProyecto(p1);
        
        try {
            c1.apoyarProyecto(p1);
            c2.apoyarProyecto(p1);
        } catch (Exception e) {
            fail("No debería lanzar excepción");
        }
        
        Map<Proyecto, Set<Ciudadano>> apoyos = app.obtenerProyectosYCiudadanosQueLosApoyan();
        assertEquals(3, apoyos.get(p1).size());
        assertTrue(apoyos.get(p1).contains(c2));
        assertTrue(apoyos.get(p1).contains(c1));
    }

    @Test
    void testRegistrarCiudadanoDuplicado() throws NifInvalidoException {
        try {
        	app.registrarCiudadano(ciudadano);     
        	fail("deberia lanzar NifInvalidoException");
        } catch (Exception e) {
        	//test pass
        }
    }

    @Test
    void testRegistrarFundacionDuplicada() throws CifInvalidoException {
    	try {
        	app.registrarFundacion(fundacion);  
        	fail("deberia lanzar CifInvalidoException");
        } catch (Exception e) {
        	//test pass
        }

    }
}