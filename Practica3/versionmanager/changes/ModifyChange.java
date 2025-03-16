package versionmanager.changes;
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
    public ModifyChange(int startLine, int endLine, String path, String content){
        super(startLine, path);
            this.content = content;
            this.endLine = endLine;
    }

    /**
         * Método para calcular el número de líneas del nuevo contenido.
	 * 
  	 * @return 	El número de líneas del nuevo contenido.
	 */
    public int getNumberOfLines() {	    
	    return this.content.split("\n").length;
    }

     /**
	 * Método para calcular el número de líneas modificadas.
	 * 
	 * @return El número de líneas en el modificadas.
	 */
    @Override
    public int getNumberOfModifiedLines() {
        return 0;
    }

    /**
	 * Método para obtener el tipo de cambio.
	 * 
	 * @return El tipo del cambio.
	 */
    @Override
    public String getType(){
        return "/";
    }

    /**
	 * Método para generar cambios de modificación de línea.
	 * 
	 * @return Una cadena que contiene los detalles de los cambios de modificación de línea.
	 */
    @Override
    public String toString() {
        return "{\ntype=/,\n"+super.toString()+"content='"+this.content+"'\nnumber of lines="+getNumberOfLines()+"end line="+this.endLine+"\n}\n";
    }
}
