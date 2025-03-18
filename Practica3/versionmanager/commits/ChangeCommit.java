package versionmanager.commits;
import versionmanager.changes.*;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase ChangeCommit representa un tipo de commit en el que se realizan cambios.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class ChangeCommit extends Commit {
    /** Lista de cambios que contiene un ChangeCommit */
    private List<Change> changes;

    /**
     * Constructor de la clase ChangeCommit.
     * 
     * @param author       Autor del ChangeCommit.
     * @param description  Descripción del ChangeCommit.
     * @param changes      Cambios del ChangeCommit.
     */
    public ChangeCommit(String author, String description, List<Change> changes) {
        super(author, description);
        this.changes = new ArrayList<>();
        this.changes.addAll(changes);
    }

    /**
     * Constructor de la clase ChangeCommit.
     * 
     * @param author       Autor del ChangeCommit.
     * @param changes      Cambios del ChangeCommit.
     */
    public ChangeCommit(String author, List<Change> changes) {
        super(author);
        this.changes = new ArrayList<>();
        this.changes.addAll(changes);
    }

    /**
     * Constructor de la clase ChangeCommit.
     * 
     * @param changes      Cambios del ChangeCommit.
     */
    public ChangeCommit(List<Change> changes) {
        super();
        this.changes = new ArrayList<>();
        this.changes.addAll(changes);
    }

    /**
     * Método para añadir un cambio a un ChangeCommit.
     * 
     * @param change        Cambio.
     */
    public void addChange(Change change) {
        changes.add(change);
    }

    /**
     * Método para obtener los cambios de un ChangeCommit.
     * 
     * @return Una lista de los cambios que contiene el ChangeCommit.
     */
    @Override
    public List<Change> obtainTotalChanges() {
        return this.changes;
    }

    /**
     * Método para obtener el número de líneas modificadas del commit.
     * 
     * @return El número de líneas modificadas.
     */
    @Override
    public int getNumberOfModifiedLinesCommit() {
        int total = 0;
        for (Change c: this.changes) {
            total += c.getNumberOfModifiedLines();
        }
        return total;
    }

    /**
     * Método para generar commits.
     * 
     * @return Una cadena que contiene los detalles del commit.
     */
    @Override
    public String toString() {
        String string = super.toString();

        if (this.changes != null && !this.changes.isEmpty()) {
            for (Change c : this.changes) {
                string += c.getType()+" : "+c.getPath()+" ("+c.getNumberOfModifiedLines()+")\n";
            }
        }
        return string;
    }
}
