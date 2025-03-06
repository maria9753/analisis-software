package changes;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase MergeCommit representa un tipo de commit en el que se realizan commits de cambios.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class MergeCommit extends Commit {
    /** Lista de ChangeCommits que contiene un MergeCommit */
    private List<ChangeCommit> changeCommits;

    /**
     * Constructor de la clase MergeCommit.
     * 
     * @param author       Autor del MergeCommit
     * @param description  Descripción del MergeCommit
     */
    public MergeCommit(String autor, String description, List<ChangeCommit> changeCommits) {
        super(autor, description);
        this.changeCommits = new ArrayList<>();
        for (ChangeCommit changeCommit : changeCommits) { 
            this.changeCommits.add(changeCommit); 
        }
    }

     /**
     * Método para añadir un cambio a un ChangeCommit.
     * 
     * @param change        Cambio.
     */
    public void addChangeCommit(ChangeCommit changeCommit) {
        changeCommits.add(changeCommit);
    }

    /**
     * Método para obtener los cambios de un MergeCommit.
     * 
     * @return Una lista de los cambios que contiene el MergeCommit.
     */
    @Override
    public List<Change> obtainTotalChanges() {
        List<Change> totalChanges = new ArrayList<>();
        for (ChangeCommit changeCommit : changeCommits) {
            totalChanges.addAll(changeCommit.obtainTotalChanges());
        }
        return totalChanges;
    }

}