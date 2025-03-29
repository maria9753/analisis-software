package aplicacion.proyectos;

import java.time.LocalDateTime;

import aplicacion.usuarios.*;

public class Proyecto {
    private static int contador = 0;
    private String titulo;
    private String descripcion;
    private Usuario proponente;
    private LocalDateTime fechaCreacion;
    private int codigo;

    public Proyecto(String titulo, String descripcion, Usuario proponente) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.proponente = proponente;
        this.codigo = contador++;
        this.fechaCreacion = LocalDateTime.now();
    }

    public String getNombre() {
        return this.titulo;
    }

    public int getCodigo() {
        return this.codigo;
    }

    @Override
    public String toString() {
        return codigo + ": " + titulo + ". Proponente: " + proponente;
    }
}
