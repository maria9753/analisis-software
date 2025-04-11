package aplicacion.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aplicacion.anuncios.AnnouncementStrategy;
import aplicacion.anuncios.Anuncio;
import aplicacion.anuncios.UnoDeCadaNStrategy;
import aplicacion.follower.FollowedEntity;
import aplicacion.follower.Follower;

/**
 * Clase prueba junit de uno de cada N strategy.
 */
public class UnoDeCadaNStrategyTest {

    /**
     * Constructor de la clase.
     */
    public UnoDeCadaNStrategyTest(){
    	/**Constructor vacío*/
    }

    private TestFollower follower;
    private TestFollowedEntity followedEntity;
    private Anuncio anuncio;

    private static class TestFollower implements Follower {
        @Override
        public void receive(Anuncio t) {}
    }

    private static class TestFollowedEntity implements FollowedEntity {
        @Override
        public boolean follow(Follower f) { return false; }
        @Override
        public boolean unfollow(Follower f) { return false; }
        @Override
        public void announce(Anuncio t) {}
		@Override
		public boolean follow(Follower f, AnnouncementStrategy ns) { return false;}
		@Override
		public AnnouncementStrategy getAnnouncementStrategy(Follower f) { return null;}
		@Override
		public void setAnnouncementStrategy(Follower f, AnnouncementStrategy ns) {}
    }

    @BeforeEach
    void setUp() {
        follower = new TestFollower();
        followedEntity = new TestFollowedEntity();
        anuncio = new Anuncio("Mensaje de prueba");
    }

    @Test
    void testConstructorValido() {
        assertDoesNotThrow(() -> new UnoDeCadaNStrategy(1));
        assertDoesNotThrow(() -> new UnoDeCadaNStrategy(10));
    }

    @Test
    void testEnviarOnoEnviarN1() {
        UnoDeCadaNStrategy strategy = new UnoDeCadaNStrategy(1);
        
        assertTrue(strategy.enviarOnoEnviar(anuncio, follower, followedEntity));
        assertTrue(strategy.enviarOnoEnviar(anuncio, follower, followedEntity));
        assertTrue(strategy.enviarOnoEnviar(anuncio, follower, followedEntity));
    }

    @Test
    void testEnviarOnoEnviarN2() {
        UnoDeCadaNStrategy strategy = new UnoDeCadaNStrategy(2);
        
        assertTrue(strategy.enviarOnoEnviar(anuncio, follower, followedEntity));  
        assertFalse(strategy.enviarOnoEnviar(anuncio, follower, followedEntity)); 
        assertTrue(strategy.enviarOnoEnviar(anuncio, follower, followedEntity));  
        assertFalse(strategy.enviarOnoEnviar(anuncio, follower, followedEntity)); 
    }

    @Test
    void testEnviarOnoEnviarN3() {
        UnoDeCadaNStrategy strategy = new UnoDeCadaNStrategy(3);
        
        assertTrue(strategy.enviarOnoEnviar(anuncio, follower, followedEntity));  
        assertFalse(strategy.enviarOnoEnviar(anuncio, follower, followedEntity));
        assertFalse(strategy.enviarOnoEnviar(anuncio, follower, followedEntity)); 
        assertTrue(strategy.enviarOnoEnviar(anuncio, follower, followedEntity));  
        assertFalse(strategy.enviarOnoEnviar(anuncio, follower, followedEntity)); 
    }

    @Test
    void testEnviarOnoEnviarConNulls() {
        UnoDeCadaNStrategy strategy = new UnoDeCadaNStrategy(2);
        
        assertTrue(strategy.enviarOnoEnviar(null, null, null));
        assertFalse(strategy.enviarOnoEnviar(null, null, null));
    }

    @Test
    void testContadorIndependientePorInstancia() {
        UnoDeCadaNStrategy strategy1 = new UnoDeCadaNStrategy(2);
        UnoDeCadaNStrategy strategy2 = new UnoDeCadaNStrategy(2);
        
        assertTrue(strategy1.enviarOnoEnviar(anuncio, follower, followedEntity));
        assertTrue(strategy2.enviarOnoEnviar(anuncio, follower, followedEntity));
        
        assertFalse(strategy1.enviarOnoEnviar(anuncio, follower, followedEntity));
        assertFalse(strategy2.enviarOnoEnviar(anuncio, follower, followedEntity));
        
        assertTrue(strategy1.enviarOnoEnviar(anuncio, follower, followedEntity));
        assertTrue(strategy2.enviarOnoEnviar(anuncio, follower, followedEntity));
        
        assertFalse(strategy1.enviarOnoEnviar(anuncio, follower, followedEntity));
        assertFalse(strategy2.enviarOnoEnviar(anuncio, follower, followedEntity));
        
        assertTrue(strategy1.enviarOnoEnviar(anuncio, follower, followedEntity));
        assertTrue(strategy2.enviarOnoEnviar(anuncio, follower, followedEntity));
    }

    @Test
    void testResetDelContador() {
        UnoDeCadaNStrategy strategy = new UnoDeCadaNStrategy(3);
        
        assertTrue(strategy.enviarOnoEnviar(anuncio, follower, followedEntity));  
        assertFalse(strategy.enviarOnoEnviar(anuncio, follower, followedEntity)); 
        assertFalse(strategy.enviarOnoEnviar(anuncio, follower, followedEntity)); 
        assertTrue(strategy.enviarOnoEnviar(anuncio, follower, followedEntity));  
        assertFalse(strategy.enviarOnoEnviar(anuncio, follower, followedEntity)); 
    }
}