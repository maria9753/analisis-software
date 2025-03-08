package changes;
/**
 * La clase RemoveChange representa un tipo de cambio en el que se elimina una línea.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class RemoveChange extends Change {
    /**Línea en la que finaliza el cambio que se va a realizar*/
    private int endLine;
    
    /**
	 * Constructor de la clase RemoveChange.
	 * 
	 * @param path 			    Ruta del fichero de datos.
	 * @param startLine 	    Línea de inicio del cambio.
     * @param endLine 	        Línea de final del cambio.
	 */
    public RemoveChange(int startLine, int endLine, String path){
        super(startLine, path);
            this.endLine = endLine;
    }

    @Override
    public int getNumberOfModifiedLines() {
        return -(this.endLine - super.startLine);
    }

    @Override
    public String getType(){
        return "-";
    }

    /**
	 * Método para generar cambios de eliminación de línea.
	 * 
	 * @return Una cadena que contiene los detalles de los cambios de eliminación de línea.
	 */
    @Override
    public String toString() {
        return "{\ntype=-,\n"+super.toString()+"end line="+this.endLine+"\n}\n";
    }
}
