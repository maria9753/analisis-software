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
    private List<Commit> commits;

    /**
     * Constructor de la clase MergeCommit.
     * 
     * @param author       Autor del MergeCommit
     * @param description  Descripción del MergeCommit
     */
    public MergeCommit(String autor, String description, List<ChangeCommit> changeCommits) {
        super(autor, description);
        this.changeCommits = new ArrayList<>();
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

    @Override
    public String toString() {
        String string= "\ncommit "+super.id+"\nAuthor: "+super.author+"\nDate: "+this.date+"\nDescription: "+super.description+"\nMerged commits:\n";

        for (ChangeCommit c: changeCommits) {
            string += c.getId()+" on "+c.getDate()+")\n";
        }
        return string;
    }

}