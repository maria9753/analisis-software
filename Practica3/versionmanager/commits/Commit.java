package versionmanager.commits;
import versionmanager.changes.*;

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

    /**
	 * Método para obtener el id del commit.
	 * 
	 * @return El id del commit.
	 */
    public String getId() {
        return this.id;
    }

    /**
	 * Método para obtener la fecha del commit.
	 * 
	 * @return La fecha del commit.
	 */
    public LocalDate getDate() {
        return this.date;
    }

    /**
	 * Método para obtener el autor del commit.
	 * 
	 * @return El autor del commit.
	 */
    public String getAuthor() {
        return this.author;
    }

    /**
	 * Método para obtener la descripción del commit.
	 * 
	 * @return La descripción del commit.
	 */
    public String getDescription() {
        return this.description;
    }

    /**
	 * Método para obtener el path en el que dos commits que están siendo
     * fusionados tienen un conflicto.
	 * 
     * @param destinyCommit     commit de destino.
	 * @return El path en el que hay conflicto.
	 */
    public String getModifiedConflictFile(Commit destinyCommit){
        for (Change originChange : obtainTotalChanges()) {
            for (Change destinyChange : destinyCommit.obtainTotalChanges()) {
                if (originChange.getPath().equals(destinyChange.getPath())) {
                    return originChange.getPath();
                }
            }
        }
        return null;
    }

    /**
	 * Método para obtener si hay un conflicto entre dos commits que están 
     * siendo fusionados.
	 * 
     * @param destinyCommit     commit de destino.
	 * @return True si hay conflicto, false si no.
	 */
    public boolean detectConflicts(Commit destinyCommit) {
        for (Change originChange : obtainTotalChanges()) {
            for (Change destinyChange : destinyCommit.obtainTotalChanges()) {
                if (originChange.getPath().equals(destinyChange.getPath())) {
                    return true;
                }
            }
        }
        return false;
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
    @Override
    public String toString() {
        return "\ncommit "+this.id+"\nAuthor: "+this.author+"\nDate: "+this.date+"\nDescription: "+this.description+"\n";
    }
}
