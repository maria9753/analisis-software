import java.util.*;

/**
 * Esta aplicación representa una cartelera de cine y sus respectivas películas
 * disponibles. Permite buscar películas por género o año de estreno
 * 
 * @author Carmen Gómez, María Pozo
 */
public class CarteleraCine {
	
	/** Nombre del cine*/
	private String nombreCine;
	
	/** Lista de películas del cine*/
	private List<Pelicula> peliculas = new ArrayList<>();
  
	/**
	 * Constructor de la clase CarteleraCine
	 * 
	 * @param cine 			Nombre del cine
	 * @param peliculas 	Array de las películas del cine
	 */
	public CarteleraCine(String cine, Pelicula[] peliculas) {
		this.nombreCine = cine;
		for (Pelicula p: peliculas)
			this.peliculas.add(p);
	}

	/**
	 * Método para buscar películas por su género
	 * 
	 * @param genero 		El género de la película
	 * @return Una cadena que contiene las películas cuyo género coincide con el 
	 * recibido
	 */
	public String peliculasPorGenero(String genero) {
		List<Pelicula> peliculasporgenero = new ArrayList<>();
		for (Pelicula p: peliculas) {
			if (p.getGenero().equals(genero)) {
				peliculasporgenero.add(p);
			}
		}
		return genero+": "+peliculasporgenero;
	}

	/**
	 * Método para buscar posteriores a un año dado
	 * 
	 * @param anyo 		El año de estreno de la película
	 * @return Una cadena que contiene las películas estenadas después del año 
	 * recibido
	 */
	public String peliculasPosterioresA(int anyo) {
		List<Pelicula> peliculasposterioresa = new ArrayList<>();
		for (Pelicula p: peliculas) {
			if (p.getAnyo() >= anyo) {
				peliculasposterioresa.add(p);
			}
		}
		return "Recientes:"+peliculasposterioresa;
	}
  
	/**
	 * Método para generar las películas de un cine
	 * 
	 * @return Una cadena que contiene el cine y las películas que tiene en 
	 * cartelera
	 */
	@Override
	public String toString() {
		return "Cine: "+this.nombreCine+"\nPeliculas en cartelera: "+this.peliculas;
	}
  
	/**
	 * Punto de entrada a la aplicación. 
	 * Este método genera la cartelera del cine, imprime las películas cuyo género
	 * es "Drama" y las películas estrenadas despúes del año 2020
	 * 
	 * @param args 		Los argumentos de la línea de comandos
	 */
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
