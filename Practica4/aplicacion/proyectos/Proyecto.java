package aplicacion.proyectos;

import java.time.LocalDateTime;

import aplicacion.anuncios.Anuncio;
import aplicacion.follower.Follower;
import aplicacion.follower.FollowedEntity;
import aplicacion.usuarios.*;

/**
 * La clase Proyecto representa los proyectos que proponen las asociaciones y
 * los ciudadanos.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class Proyecto implements FollowedEntity {
    /** Contador para generar códigos */
    private static int contador = 0;
    /** Título del proyecto */
    private String titulo;
    /** Descripción del proyecto */
    private String descripcion;
    /** Proponente del proyecto */
    private Usuario proponente;
    /** Fecha de creación del proyecto */
    private LocalDateTime fechaCreacion;
    /** Código del proyecto */
    private int codigo;

    /**
     * Constructor de la clase Proyecto.
     * 
     * @param titulo      Nombre del proyecto.
     * @param descripcion Descripción del proyecto.
     * @param proponente  Usuario que propone el proyecto.
     */
    public Proyecto(String titulo, String descripcion, Usuario proponente) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.proponente = proponente;
        this.codigo = contador++;
        this.fechaCreacion = LocalDateTime.now();
    }

    /**
     * Método para obtener el nombre del proyecto.
     * 
     * @return El título del proyecto.
     */
    public String getNombre() {
        return this.titulo;
    }

    /**
     * Método para obtener el código del proyecto.
     * 
     * @return El código único del proyecto.
     */
    public int getCodigo() {
        return this.codigo;
    }

    /**
     * Método para obtener la descripción del proyecto.
     * 
     * @return La descripción del proyecto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método para obtener el proponente del proyecto.
     * 
     * @return El proponente del proyecto.
     */
    public Usuario getProponente() {
        return proponente;
    }

    /**
     * Método para obtener la fecha de creación del proyecto.
     * 
     * @return La fecha de creación del proyecto.
     */
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Sobrescribe el método toString para representar el proyecto como una cadena.
     * 
     * @return Una cadena del proyecto.
     */
    @Override
    public String toString() {
        return codigo + ": " + titulo + ". Proponente: " + proponente;
    }

    /**
     * Método para seguir a otros usuarios.
     * 
     * @param f Seguidor al que se empieza a seguir.
     * @return True si ha sido correcto, false si no.
     */
    @Override
    public boolean follow(Follower f) {
        return followers.add(f);
    }

    /**
     * Método para dejar de seguir a otros usuarios.
     * 
     * @param f Seguidor al que se deja de seguir.
     * @return True si ha sido correcto, false si no.
     */
    @Override
    public boolean unfollow(Follower f) {
        return followers.remove(f);
    }

    /**
     * Método para recibir anuncios
     * 
     * @param t Anuncio que se recibe
     */
    @Override
    public void announce(Anuncio t) {
        for (Follower f : followers) {
            f.recieve(t);
        }
    }

    public void anuncioApoyoProyecto(String title, String description) {
        announce(new Anuncio("(" + " apoyos)"));
    }
}
