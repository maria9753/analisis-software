package aplicacion.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import aplicacion.Aplicacion;
import aplicacion.proyectos.Proyecto;
import aplicacion.usuarios.Ciudadano;
import aplicacion.anuncios.AnnouncementStrategy;
import aplicacion.anuncios.Anuncio;
import aplicacion.exceptions.NifInvalidoException;
import aplicacion.follower.FollowedEntity;
import aplicacion.follower.Follower;

/**
 * Clase prueba junit de proyecto.
 */
public class ProyectoTest {

    /**
     * Constructor de la clase.
     */
    public ProyectoTest(){
    	/**Constructor vacío*/
    }
    
    private Aplicacion app;
    private Ciudadano proponente;
    private Proyecto proyecto;
    private TestFollower follower;

    private static class TestFollower implements Follower {
        public Anuncio ultimoAnuncio;
        
        @Override
        public void receive(Anuncio t) {
            this.ultimoAnuncio = t;
        }
    }
    
    @BeforeEach
    void setUp() throws NifInvalidoException {
        app = new Aplicacion();
        proponente = new Ciudadano("Proponente Test", "password", app, "12345678A");
        proyecto = new Proyecto("Proyecto Test", "Descripción test", proponente);
        follower = new TestFollower();
    }
    
    @Test
    void testConstructor() {
        assertNotNull(proyecto);
        assertEquals("Proyecto Test", proyecto.getNombre());
        assertEquals("Descripción test", proyecto.getDescripcion());
        assertEquals(proponente, proyecto.getProponente());
        assertNotNull(proyecto.getFechaCreacion());
        assertTrue(proyecto.getNumApoyos() == 0);
    }
    
    @Test
    void testGetCodigo() {
        Proyecto p2 = new Proyecto("Otro proyecto", "Otra desc", proponente);
        assertTrue(proyecto.getCodigo() < p2.getCodigo()); 
    }
    
    @Test
    void testAddRemoveApoyo() {
        assertEquals(0, proyecto.getNumApoyos());
        proyecto.addApoyo();
        assertEquals(1, proyecto.getNumApoyos());
        proyecto.addApoyo();
        assertEquals(2, proyecto.getNumApoyos());
        proyecto.removeApoyo();
        assertEquals(1, proyecto.getNumApoyos());
    }
    
    @Test
    void testToString() {
        String str = proyecto.toString();
        assertTrue(str.contains("Proyecto Test"));
        assertTrue(str.contains(proponente.toString()));
        assertTrue(str.contains(String.valueOf(proyecto.getCodigo())));
    }
    
    @Test
    void testFollowWithStrategy() {
        AnnouncementStrategy strategy = new AnnouncementStrategy() {
            @Override
            public boolean enviarOnoEnviar(Anuncio anuncio, Follower follower, FollowedEntity followedEntity) {
                return true;
            }
        };
        
        assertTrue(proyecto.follow(follower, strategy));
        assertEquals(strategy, proyecto.getAnnouncementStrategy(follower));
    }
    
    @Test
    void testAnnounce() {
        proyecto.follow(follower);
        Anuncio anuncio = new Anuncio("Mensaje de prueba");
        
        proyecto.announce(anuncio);
        assertEquals(anuncio, follower.ultimoAnuncio);
    }
    
    @Test
    void testAnnounceWithStrategy() {
        AnnouncementStrategy rejectStrategy = new AnnouncementStrategy() {
            @Override
            public boolean enviarOnoEnviar(Anuncio anuncio, Follower follower, FollowedEntity followedEntity) {
                return false;
            }
        };
        
        proyecto.follow(follower, rejectStrategy);
        Anuncio anuncio = new Anuncio("Mensaje que no debería llegar");
        
        proyecto.announce(anuncio);
        assertNull(follower.ultimoAnuncio); 
    }
    
    @Test
    void testSetAnnouncementStrategy() {
        proyecto.follow(follower);
        
        AnnouncementStrategy strategy = new AnnouncementStrategy() {
            @Override
            public boolean enviarOnoEnviar(Anuncio anuncio, Follower follower, FollowedEntity followedEntity) {
                return true;
            }
        };
        
        proyecto.setAnnouncementStrategy(follower, strategy);
        assertEquals(strategy, proyecto.getAnnouncementStrategy(follower));
    }
    
    @Test
    void testFollowNull() {
        assertFalse(proyecto.follow(null));
        assertFalse(proyecto.follow(null, null));
    }
    
    @Test
    void testUnfollowNull() {
        assertFalse(proyecto.unfollow(null));
    }
    
    @Test
    void testGetFechaCreacion() {
        LocalDateTime antes = LocalDateTime.now().minusSeconds(1);
        Proyecto nuevoProyecto = new Proyecto("Nuevo", "Desc", proponente);
        LocalDateTime despues = LocalDateTime.now().plusSeconds(1);
        
        assertTrue(nuevoProyecto.getFechaCreacion().isAfter(antes));
        assertTrue(nuevoProyecto.getFechaCreacion().isBefore(despues));
    }
    
    @Test
    void testContadorCodigo() {
        int codigoInicial = proyecto.getCodigo();
        Proyecto p2 = new Proyecto("Proyecto 2", "Desc 2", proponente);
        assertEquals(codigoInicial + 1, p2.getCodigo());
        
        Proyecto p3 = new Proyecto("Proyecto 3", "Desc 3", proponente);
        assertEquals(codigoInicial + 2, p3.getCodigo());
    }
}