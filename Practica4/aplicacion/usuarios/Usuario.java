package aplicacion.usuarios;

/**
 * La clase Usuario representa a un usuario de la aplicación.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public abstract class Usuario {
    /** Nombre del usuario */
    private String nombre;
    /** Contraseña del usuario */
    private String contrasena;

    /**
     * Constructor de la clase Usuario.
     * 
     * @param nombre     Nombre del usuario.
     * @param contrasena Contraseña del usuario.
     * 
     */
    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    /**
     * Método para obtener el nombre de un usuario.
     * 
     * @return nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método para obtener la contraseña de un usuario.
     * 
     * @return contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }
}