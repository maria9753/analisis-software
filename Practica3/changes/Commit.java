package changes;

/**
 * La clase Commit representa una serie de cambios. 
 * 
 * @author Carmen Gómez, María Pozo.
 */
public abstract class Commit {
    /** Autor por defecto*/
    private static String defectAuthor = 'Cervantes';
    /** Descripción por defecto*/
    private static String defectDescription = 'No description';
  
    /** Primera parte del identificador del commit*/
    private int id1;
    /** Segunda parte del identificador del commit*/
    private String id2;
    /** Autor del commit*/
    private String author;
    /** Fecha del commit*/
    private Date date;
    /** Descripción del commit*/
    private String description;
    

    /** Setter de defectDescription.
   *
   * @param description  Nueva descripción por defecto
   */
    public static void setDefectDescription(String description) {
      defectDescription = description;
    }

    /** Setter de defectAuthor.
   *
   * @param author  Nueva autor por defecto
   */
    public static void setDefectAuthor(String author) {
      defectAuthor = author;
    }

    /**
	 * Constructor de la clase Commit.
	 * 
	 * @param id1          Primera parte del identificador
   * @param id2          Segunda parte del indentificador
   * @param author       Autor del commit
   * @param date         Fecha del commit
   * @param description  Descripción del commit
   * @param changes      Serie de cambios del commit
	 */
    public Commit(int id1, String id2, String author, Date date, String description, List<Change> changes) {
        this.id1 = id1;
        this.id2 = id2;
        if (author.equals(null)) {
          this.author = defectAuthor;
        } else {
          this.author = author;
        this.date = date;
        if (description.equals(null)) {
          this.description = defectDescription;
        } else {
          this.description = description;
        }
        this.changes = changes;
    }

    /**
	 * Método para generar commits.
	 * 
	 * @return Una cadena que contiene los detalles del commit.
	 */
    public String toString() {
        return "\ncommit "+this.id1+this.id2+"\nAuthor: "+this.author+"\nDate: "+this.description;
    }
}
