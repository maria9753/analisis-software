package aplicacion.proyectos;

import java.time.LocalDateTime;

import aplicacion.usuarios.*;

/**
 * La clase Proyecto representa los proyectos que proponen las asociaciones y
 * los ciudadanos.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class Proyecto {
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
     * Sobrescribe el método toString para representar el proyecto como una cadena.
     * 
     * @return Una cadena del proyecto.
     */
    @Override
    public String toString() {
        return codigo + ": " + titulo + ". Proponente: " + proponente;
    }
}
