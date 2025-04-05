package aplicacion.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aplicacion.Aplicacion;
import aplicacion.usuarios.Usuario;

public class UsuarioTest {
    
    private Aplicacion app;
    private UsuarioConcreto usuario;

    private static class UsuarioConcreto extends Usuario {
        public UsuarioConcreto(String nombre, String contrasena, Aplicacion app) {
            super(nombre, contrasena, app);
        }

        public Aplicacion getAplicacion() {
            return this.aplicacion;
        }
    }
    
    @BeforeEach
    void setUp() {
        app = new Aplicacion();
        usuario = new UsuarioConcreto("TestUser", "securePassword123", app);
    }
    
    @Test
    void testConstructor() {
        assertNotNull(usuario);
        assertEquals("TestUser", usuario.getNombre());
        assertEquals("securePassword123", usuario.getContrasena());
        assertSame(app, usuario.getAplicacion());
    }
    
    @Test
    void testGetNombre() {
        assertEquals("TestUser", usuario.getNombre());
    }
    
    @Test
    void testGetContrasena() {
        assertEquals("securePassword123", usuario.getContrasena());
    }
    
    @Test
    void testAplicacion() {
        assertNotNull(usuario.getAplicacion());
        assertSame(app, usuario.getAplicacion());
    }
    
    @Test
    void testConstructorConNombreNull() {
        UsuarioConcreto user = new UsuarioConcreto(null, "pass", app);
        assertNull(user.getNombre());
    }
    
    @Test
    void testConstructorConContrasenaNull() {
        UsuarioConcreto user = new UsuarioConcreto("user", null, app);
        assertNull(user.getContrasena());
    }
    
    @Test
    void testConstructorConAplicacionNull() {
        UsuarioConcreto user = new UsuarioConcreto("user", "pass", null);
        assertNull(user.getAplicacion());
    }
    
    @Test
    void testInmutabilidad() {
        String nombreOriginal = usuario.getNombre();
        String contraOriginal = usuario.getContrasena();
        Aplicacion appOriginal = usuario.getAplicacion();

        assertEquals(nombreOriginal, usuario.getNombre());
        assertEquals(contraOriginal, usuario.getContrasena());
        assertSame(appOriginal, usuario.getAplicacion());
    }
}