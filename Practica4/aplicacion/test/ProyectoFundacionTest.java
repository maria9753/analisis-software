package aplicacion.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aplicacion.Aplicacion;
import aplicacion.exceptions.CifInvalidoException;
import aplicacion.exceptions.NifInvalidoException;
import aplicacion.proyectos.ProyectoFundacion;
import aplicacion.usuarios.Ciudadano;
import aplicacion.usuarios.Fundacion;

/**
 * Clase prueba junit de rpoyecto fundacion.
 */
public class ProyectoFundacionTest {

    /**
     * Constructor de la clase.
     */
    public ProyectoFundacionTest(){
    	/**Constructor vacío*/
    }
    
    private Aplicacion app;
    private Fundacion proponente;
    private ProyectoFundacion proyecto;
    
    @BeforeEach
    void setUp() throws CifInvalidoException {
        app = new Aplicacion();
        proponente = new Fundacion("Fundacion Test", "password", app, "A1234567B");
        proyecto = new ProyectoFundacion("Proyecto Test", "Descripción test", proponente, 100000.0, 50.0);
    }
    
    @Test
    void testConstructorValido() {
        assertNotNull(proyecto);
        assertEquals("Proyecto Test", proyecto.getNombre());
        assertEquals("Descripción test", proyecto.getDescripcion());
        assertEquals(proponente, proyecto.getProponente());
        assertEquals(100000.0, proyecto.getPresupuesto(), 0.001);
        assertEquals(50.0, proyecto.getPorcentaje(), 0.001);
    }
    
    @Test
    void testConstructorPorcentajeInvalido() {
    	try {
    		ProyectoFundacion p1 = new ProyectoFundacion("Proyecto", "Desc", proponente, 1000.0, 0.0);
    		fail("deberia lanzar un IllegalArgumentException");
    	} catch (Exception e){
    		//test pass
    	}

    	try {
    		ProyectoFundacion p1 = new ProyectoFundacion("Proyecto", "Desc", proponente, 1000.0, 101.0);
    		fail("deberia lanzar un IllegalArgumentException");
    	} catch (Exception e){
    		//test pass
    	}
    }
    
    @Test
    void testConstructorPresupuestoInvalido() {
    	try {
    		ProyectoFundacion p1 = new ProyectoFundacion("Proyecto", "Desc", proponente, 0.0, 50.0);
    		fail("deberia lanzar un IllegalArgumentException");
    	} catch (Exception e){
    		//test pass
    	}

    	try {
    		ProyectoFundacion p1 = new ProyectoFundacion("Proyecto", "Desc", proponente, -100.0, 50.0);
    		fail("deberia lanzar un IllegalArgumentException");
    	} catch (Exception e){
    		//test pass
    	}
    }
    
    @Test
    void testToString() {
        String str = proyecto.toString();
        assertTrue(str.contains("Proyecto Test"));
        assertTrue(str.contains(proponente.toString()));
        assertTrue(str.contains("100000.0€"));
        assertTrue(str.contains("50.0%"));
        assertTrue(str.contains("/proyecto de fundación/"));
    }
    
    @Test
    void testGetPresupuesto() {
        assertEquals(100000.0, proyecto.getPresupuesto(), 0.001);
    }
    
    @Test
    void testGetPorcentaje() {
        assertEquals(50.0, proyecto.getPorcentaje(), 0.001);
    }
    
    @Test
    void testHerenciaDeProyecto() {
        assertEquals(0, proyecto.getNumApoyos());
        proyecto.addApoyo();
        assertEquals(1, proyecto.getNumApoyos());
        assertNotNull(proyecto.getFechaCreacion());
    }
    
    @Test
    void testValoresLimite() {
        ProyectoFundacion pMin = new ProyectoFundacion("Min", "Desc", proponente, 0.01, 1.0);
        assertEquals(1.0, pMin.getPorcentaje(), 0.001);
        
        ProyectoFundacion pMax = new ProyectoFundacion("Max", "Desc", proponente, 0.01, 100.0);
        assertEquals(100.0, pMax.getPorcentaje(), 0.001);
        
        ProyectoFundacion pPres = new ProyectoFundacion("Pres", "Desc", proponente, Double.MIN_VALUE, 50.0);
        assertTrue(pPres.getPresupuesto() > 0);
    }
    
    @Test
    void testProponenteNoFundacion() throws NifInvalidoException {
        Ciudadano ciudadano = new Ciudadano("Ciudadano", "pass", app, "12345678A");
        ProyectoFundacion p = new ProyectoFundacion("Test", "Desc", ciudadano, 100.0, 50.0);
        assertEquals(ciudadano, p.getProponente());
    }
}