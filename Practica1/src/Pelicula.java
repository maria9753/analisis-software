/**
 * Representa películas que contienen título, género, año y director.
 * Esta clase se utiliza en CarteleraCine
 * 
 * @author Carmen Gómez, María Pozo
 */
public class Pelicula {
	private String titulo;
	private String genero;
	private int anyo;
	private String director;
  
	/**
	 * Constructor de la clase Pelicula
	 * 
	 * @param titulo 			Nombre de la película
	 * @param genero 			Género de la película
	 * @param anyo 				Año de estreno de la película
	 * @param director 			Director de la película
	 */
	public Pelicula (String titulo, String genero, int anyo, String director) {
		this.titulo = titulo;
		this.genero = genero;
		this.anyo = anyo;
		this.director = director;
	}
  
	/**
	 * Método para generar cadenas de las películas con sus respectivos atributos
	 * 
	 * @return Una cadena que contiene el título, gñenero, anyo y director de la película
	 */
	@Override
	public String toString() {
		return this.titulo+" - dirigida por: "+this.director+" ("+this.genero+"): "+this.anyo;
	}
  
	/**
	 * Método para obtener el título de una película
	 * 
	 * @return Una cadena con el título de la película
	 */
	public String getTitulo() {
		return titulo;
	}
  
	/**
	 * Método para obtener el género de una película
	 * 
	 * @return Una cadena con el género de la película
	 */
	public String getGenero() {
		return genero;
	}
  
	/**
	 * Método para obtener el anyo de una película
	 * 
	 * @return Una entero que representa el año de estreno de la película
	 */
	public int getAnyo() {
		return anyo;
	}
	
	/**
	 * Método para obtener el director de una película
	 * 
	 * @return Una cadena con el director de la película
	 */
	public String getDirector() {
		return director;
	}
}
