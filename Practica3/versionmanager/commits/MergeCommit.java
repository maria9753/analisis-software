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
     * Método para obtener el número de líneas modificadas del commit.
     * 
     * @return El número de líneas modificadas.
     */
    @Override
    public int getNumberOfModifiedLinesCommit() {
        int total = 0;
        for (Commit c: this.commits) {
            total += c.getNumberOfModifiedLinesCommit();
        }
        return total;
    }

    /**
     * Método para obtener los commits del merge commit.
     * 
     * @return Una lista con los commits del merge commit.
     */
    public List<Commit> obtainTotalCommits() {
        return this.commits;
    }

    /**
     * Método para generar commits.
     * 
     * @return Una cadena que contiene los detalles del commit.
     */
    @Override
    public String toString() {
        String string= super.toString()+"\nMerged commits:\n";

        for (Commit c: commits) {
            string += c.getId()+" on "+c.getDate()+"\n";
        }
        return string;
    }

}