package versionmanager.commits;
import versionmanager.changes.*;

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
     * @param changes      Cambios del MergeCommit.
     */
    public MergeCommit(String autor, String description, List<Commit> commits) {
        super(autor, description);
        this.commits = new ArrayList<>();
        this.commits.addAll(commits);
    }

    /**
     * Constructor de la clase MergeCommit.
     * 
     * @param author       Autor del MergeCommit
     * @param changes      Cambios del MergeCommit.
     */
    public MergeCommit(String autor, List<Commit> commits) {
        super(autor);
        this.commits = new ArrayList<>();
        this.commits.addAll(commits);
    }

    /**
     * Constructor de la clase MergeCommit.
     * 
     * @param changes      Cambios del MergeCommit.
     */
    public MergeCommit( List<Commit> commits) {
        super();
        this.commits = new ArrayList<>();
        this.commits.addAll(commits);
    }
    
    /**
     * Método para añadir un commit a un MergeCommit.
     * 
     * @param commit        Commit.
     */
    public void addCommit(Commit commit) {
        commits.add(commit);
    }

    /**
     * Método para obtener los cambios de un MergeCommit.
     * 
     * @return Una lista de los cambios que contiene el MergeCommit.
     */
    @Override
    public List<Change> obtainTotalChanges() {
        List<Change> totalChanges = new ArrayList<>();
        for (Commit c : commits) {
            totalChanges.addAll(c.obtainTotalChanges());
        }
        return totalChanges;
    }

    /**
     * Método para generar commits.
     * 
     * @return Una cadena que contiene los detalles del commit.
     */
    @Override
    public String toString() {
        String string= "\ncommit "+super.id+"\nAuthor: "+super.author+"\nDate: "+this.date+"\nDescription: "+super.description+"\nMerged commits:\n";

        for (Commit c: commits) {
            string += c.getId()+" on "+c.getDate()+"\n";
        }
        return string;
    }

}