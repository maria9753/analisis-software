package changes;
/**
 * La clase AddChange representa un tipo de cambio en el que se añade una línea.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class AddChange extends Change {
    /**Contenido del cambio que se va a realizar*/
    private String content;
    
    /**
	 * Constructor de la clase AddChange.
	 * 
	 * @param path 			    Ruta del fichero de datos.
	 * @param startLine 	    Línea de inicio del cambio.
     * @param content 	        Contenido del cambio.
	 */
    public AddChange(String path, int startLine, String content){
        super(path, startLine);
            this.content = content;
    }

    /**
	 * Método para generar cambios de agregación de línea.
	 * 
	 * @return Una cadena que contiene los detalles de los cambios de agregación de línea.
	 */
    @Override
    public String toString() {
        return super.toString()+"\nEmail: "+this.email;
    }
}
