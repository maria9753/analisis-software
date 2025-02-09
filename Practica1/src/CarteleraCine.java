import java.util.*;

public class CarteleraCine {
  private String nombreCine;
  private List<Pelicula> peliculas = new ArrayList<>();
  
  public CarteleraCine(String cine, Pelicula[] peliculas) {
    this.nombreCine = cine;
    for (Pelicula p: peliculas)
    this.peliculas.add(p);
  }

  public String peliculasPorGenero(String genero) {
    List<Pelicula> peliculasporgenero = new ArrayList<>();
    for (Pelicula p: peliculas) {
      if (p.getGenero() == genero) {
        peliculasporgenero.add(p);
      }
    }
    return genero+": "+peliculasporgenero;
  }

  public String peliculasPosterioresA(int anyo) {
    List<Pelicula> peliculasposterioresa = new ArrayList<>();
    for (Pelicula p: peliculas) {
      if (p.getAnyo() >= anyo) {
        peliculasposterioresa.add(p);
      }
    }
    return "Recientes:"+peliculasposterioresa;
  }
  
  @Override
  public String toString() {
    return "Cine: "+this.nombreCine+"\nPeliculas en cartelera: "+this.peliculas;
  }
  
  public static void main(String ...args) {
    Pelicula peliculas[] = { new Pelicula("Perfect days", "Drama", 2023, "Wim Wenders"),
                             new Pelicula("Inception", "Accion", 2010, "Christopher Nolan"),
                             new Pelicula("Jumanji", "Aventura", 1995, "Joe Johnston") };
    CarteleraCine cinesTelmo = new CarteleraCine("Telmo", peliculas);
    System.out.println(cinesTelmo);
    System.out.println(cinesTelmo.peliculasPorGenero("Drama"));
    System.out.println(cinesTelmo.peliculasPosterioresA(2020));
  }
}
