package changes;

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
     * @param author       Autor del ChangeCommit
     * @param description  Descripción del ChangeCommit
     */
    public ChangeCommit(String autor, String description, List<Change> changes) {
        super(autor, description);
        this.changes = new ArrayList<>();
        for (Change change : changes) { 
            this.changes.add(change); 
        }
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
        return changes;
    }


}