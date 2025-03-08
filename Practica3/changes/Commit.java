package changes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * La clase Commit representa una serie de cambios. 
 * 
 * @author Carmen Gómez, María Pozo.
 */
public abstract class Commit {
    /** Autor por defecto*/
    private static String defectAuthor = "Cervantes";
    /** Descripción por defecto*/
    private static String defectDescription = "No description";
    /** Contador para generar los ids*/
    private static int counter = 1;
  
    /** Identificador del commit*/
    protected String id;
    /** Autor del commit*/
    protected String author;
    /** Fecha del commit*/
    protected LocalDate date;
    /** Descripción del commit*/
    protected String description;
    
    /**
     * Constructor de la clase Commit.
     * 
     * @param author       Autor del commit
     * @param description  Descripción del commit
     */
    public Commit(String author, String description) {
        this.id= generateId();
        this.author = author;
        this.description = description;
        this.date = LocalDate.now();
    }

    /**
     * Constructor de la clase Commit.
     * 
     * @param author       Autor del commit
     */
    public Commit(String author) {
        this.id= generateId();
        this.author = author;
        this.description = defectDescription;
        this.date = LocalDate.now();
    }

    /**
     * Constructor de la clase Commit.
     * 
     */
    public Commit() {
        this.id= generateId();
        this.author = defectAuthor;
        this.description = defectDescription;
        this.date = LocalDate.now();
    }



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
     * Método para generar ids.
     * 
     * @return Una String con el nuevo id del commit.
     */
    private static String generateId() {
        return String.format("%05d", counter++) + UUID.randomUUID().toString().replace("-", "").substring(0, 15);
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAuthor() {
        return this.author;
    }

    /**
     * Método para obtener todos los cambios del commit.
     * 
     * @return Un array de cambios.
     */
    public abstract List<Change> obtainTotalChanges();

    /**
     * Método para generar commits.
     * 
     * @return Una cadena que contiene los detalles del commit.
     */
    public String toString() {
        return "\ncommit "+this.id+"\nAuthor: "+this.author+"\nDate: "+this.date+"\nDescription: "+this.description+"\n";
    }
}
