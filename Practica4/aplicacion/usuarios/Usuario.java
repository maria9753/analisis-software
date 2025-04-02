package aplicacion.usuarios;

import aplicacion.*;

/**
 * La clase Usuario representa a un usuario de la aplicación.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public abstract class Usuario {
    /** Nombre del usuario */
    protected String nombre;
    /** Contraseña del usuario */
    private String contrasena;
    protected Aplicacion aplicacion;

    /**
     * Constructor de la clase Usuario.
     * 
     * @param nombre     Nombre del usuario.
     * @param contrasena Contraseña del usuario.
     * 
     */
    public Usuario(String nombre, String contrasena, Aplicacion aplicacion) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.aplicacion = aplicacion;
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