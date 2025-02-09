
public class Pelicula {
  private String titulo;
  private String genero;
  private int anyo;
  private String director;
  
  public Pelicula (String titulo, String genero, int anyo, String director) {
    this.titulo = titulo;
    this.genero = genero;
    this.anyo = anyo;
    this.director = director;
  }
  
  @Override
  public String toString() {
    return this.titulo+" - dirigida por: "+this.director+" ("+this.genero+"): "+this.anyo;
  }
  
  public String getTitulo() {
    return titulo;
  }
  
  public String getGenero() {
    return genero;
  }
  
  public int getAnyo() {
    return anyo;
  }

  public String getDirector() {
    return director;
  }
}
