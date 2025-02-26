package changes;
/**
 * La clase ModifyChange representa un tipo de cambio en el que se modifica una línea.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class ModifyChange extends Change {
    /**Contenido del cambio que se va a realizar*/
    private String content;
    /**Línea en la que finaliza el cambio que se va a realizar*/
    private int endLine;
    
    /**
	 * Constructor de la clase ModifyChange.
	 * 
	 * @param path 			    Ruta del fichero de datos.
	 * @param startLine 	    Línea de inicio del cambio.
     * @param content 	        Contenido del cambio.
     * @param endLine 	        Línea de final del cambio.
	 */
    public ModifyChange(String path, int startLine, String content, int endLine){
        super(path, startLine);
            this.content = content;
            this.endLine = endLine;
    }

    /**
	 * Método para generar cambios de modificación de línea.
	 * 
	 * @return Una cadena que contiene los detalles de los cambios de modificación de línea.
	 */
    @Override
    public String toString() {
        return "{\ntype=/,\n"+super.toString()+"content="+this.content+"end line="+this.endLine+"}\n";
    }
}
