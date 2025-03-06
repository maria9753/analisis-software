package changes;

/**
 * La clase Change representa los cambios. 
 * 
 * @author Carmen Gómez, María Pozo.
 */
public abstract class Change {
    /** Ruta del fichero*/
    private String path;
    /** Número de línea inicial*/
    private int startLine;

    /**
	 * Constructor de la clase Change.
	 * 
	 * @param path 		Ruta del fichero.
	 * @param startLine 	Número de línea incial.
	 */
    public Change(int startLine, String path) {
        this.path = path;
        this.startLine = startLine;
    }

    /**
	 * Método para calcular el número de líneas modificadas.
	 * 
	 * @return El número de líneas modificadas.
	 */
    public int getChangedLines() {
        return changedLines;
    }
    
    /**
	 * Método para generar cambios.
	 * 
	 * @return Una cadena que contiene los detalles del cambio.
	 */
    public String toString() {
        return "start line="+this.startLine+",\nfile path='"+this.path+"',\n";
    }
}
