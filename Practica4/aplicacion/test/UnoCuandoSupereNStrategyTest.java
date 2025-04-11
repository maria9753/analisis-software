package aplicacion.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aplicacion.Aplicacion;
import aplicacion.anuncios.AnnouncementStrategy;
import aplicacion.anuncios.Anuncio;
import aplicacion.anuncios.UnoCuandoSupereNStrategy;
import aplicacion.exceptions.NifInvalidoException;
import aplicacion.follower.FollowedEntity;
import aplicacion.follower.Follower;
import aplicacion.proyectos.Proyecto;
import aplicacion.usuarios.Ciudadano;

public class UnoCuandoSupereNStrategyTest {

    private TestFollower follower;
    private TestProyecto proyecto;
    private Anuncio anuncio;

    private static class TestFollower implements Follower {
        @Override
        public void receive(Anuncio t) {}
    }

    private static class TestProyecto extends Proyecto {
        private int apoyos;
        
        public TestProyecto(int apoyosIniciales) throws NifInvalidoException {
            super("Test", "Desc", new Ciudadano("Test", "pass", new Aplicacion(), "12345678A"));
            this.apoyos = apoyosIniciales;
        }
        
        @Override
        public int getNumApoyos() {
            return apoyos;
        }
        
        public void setApoyos(int apoyos) {
            this.apoyos = apoyos;
        }
    }

    @BeforeEach
    void setUp() {
        follower = new TestFollower();
        anuncio = new Anuncio("Test");
    }

    @Test
    void testConstructorValido() {
    	try {
    		UnoCuandoSupereNStrategy strategy1 = new UnoCuandoSupereNStrategy(1); 
    	} catch (Exception e) {
    		fail("no debería lanzar excepciones");
    	}
    	
    	try {
    		UnoCuandoSupereNStrategy strategy2 = new UnoCuandoSupereNStrategy(10); 
    	} catch (Exception e) {
    		fail("no debería lanzar excepciones");
    	}
    }

    @Test
    void testEnviarConProyectoNoSuperaUmbral() throws NifInvalidoException {
        proyecto = new TestProyecto(5); 
        UnoCuandoSupereNStrategy strategy = new UnoCuandoSupereNStrategy(10); 
        
        assertFalse(strategy.enviarOnoEnviar(anuncio, follower, proyecto));
    }

    @Test
    void testEnviarConProyectoSuperaUmbral() throws NifInvalidoException {
        proyecto = new TestProyecto(10); 
        UnoCuandoSupereNStrategy strategy = new UnoCuandoSupereNStrategy(10); 
        
        assertTrue(strategy.enviarOnoEnviar(anuncio, follower, proyecto));
    }

    @Test
    void testEnviarSoloUnaVez() throws NifInvalidoException {
        proyecto = new TestProyecto(10);
        UnoCuandoSupereNStrategy strategy = new UnoCuandoSupereNStrategy(10);
        
        assertTrue(strategy.enviarOnoEnviar(anuncio, follower, proyecto));
        
        assertFalse(strategy.enviarOnoEnviar(anuncio, follower, proyecto));
    }

    @Test
    void testEnviarCuandoSuperaPeroLuegoBaja() throws NifInvalidoException {
        proyecto = new TestProyecto(10);
        UnoCuandoSupereNStrategy strategy = new UnoCuandoSupereNStrategy(10);
        
        assertTrue(strategy.enviarOnoEnviar(anuncio, follower, proyecto));
        
        proyecto.setApoyos(5);
        
        proyecto.setApoyos(10);
        assertFalse(strategy.enviarOnoEnviar(anuncio, follower, proyecto)); 
    }

    @Test
    void testEnviarConEntidadNoProyecto() {
        UnoCuandoSupereNStrategy strategy = new UnoCuandoSupereNStrategy(10);
        FollowedEntity noProyecto = new FollowedEntity() {
            @Override public boolean follow(Follower f) { return false; }
            @Override public boolean unfollow(Follower f) { return false; }
            @Override public void announce(Anuncio t) {}
			@Override public boolean follow(Follower f, AnnouncementStrategy ns) { return false;}
			@Override public AnnouncementStrategy getAnnouncementStrategy(Follower f) { return null;}
			@Override public void setAnnouncementStrategy(Follower f, AnnouncementStrategy ns) {}
        };
        
        try {
        	strategy.enviarOnoEnviar(anuncio, follower, noProyecto);
        	fail("debería mandar un IllegalArgumentException");
        } catch (Exception e) {
        	// test pass
        }
    }

    @Test
    void testValoresLimite() throws NifInvalidoException {
        proyecto = new TestProyecto(10);
        UnoCuandoSupereNStrategy strategy = new UnoCuandoSupereNStrategy(10);
        
        assertTrue(strategy.enviarOnoEnviar(anuncio, follower, proyecto));
        
        proyecto.setApoyos(11);
        assertFalse(strategy.enviarOnoEnviar(anuncio, follower, proyecto)); 
    }
}