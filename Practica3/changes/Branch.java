package changes;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Branch representa una rama que contiene commits.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class Branch {
    /** Lista de commits que tiene una Branch */
    private List<Commit> branchCommits;
    /** Nombre de la Branch */
    private String name;
    /** Branch de la que ha sido creada */
    private Branch originBranch;

    /**
     * Constructor de la clase Branch.
     * 
     * @param name          Nombre de la Branch.
     * @param commits       Commits de la Branch.
     */
    public Branch(String name, List<Commit> commits) {
        this.name=name;
        this.branchCommits = new ArrayList<>();
        for (Commit commit : commits) { 
            this.branchCommits.add(commit); 
        }
    }

    /**
     * Constructor de la clase Branch.
     * 
     * @param name          Nombre de la Branch.
     * @param commits       Commits de la Branch.
     */
    public Branch(String name, Branch originBranch) {
        this.name=name;
        this.originBranch=originBranch;
        this.branchCommits = new ArrayList<>(originBranch.getCommits());
    }

    /**
     * Método para añadir un commit a la Branch.
     * 
     * @param commit       Commit a añadir a la Branch.
     */
    public void commit(Commit commit){
        branchCommits.add(commit);
    }

    /**
     * Método que obtiene una lista de los commits de una Branch.
     * 
     * @return Lista de commits.
     */
    public List<Commit> getCommits() {
        return this.branchCommits;
    }

    /**
     * Método que obtiene el nombre de una Branch.
     * 
     * @return Name of the Branch .
     */
    public String getName() {
        return this.name;
    }

    public int numberOfCommits(){
        return this.branchCommits.size();
    }

    /**
     * Método para generar branches.
     * 
     * @return Una cadena que contiene los detalles de una branch.
     */
    public String toString() {
        String string = "\nBranch: "+this.name;
        if(this.originBranch!=null){
            string += " (from "+this.originBranch.name+")";
        }
        string += "\n"+numberOfCommits()+" commits:\n";

        for (Commit c: this.branchCommits) {
            string += c.getId().substring(0, 5)+" - ";
            if(c.getDescription().length()>30){
                string += c.getDescription().substring(0,30);
            }
            else{
                string += c.getDescription();
            }
            string +=" at "+c.getDate()+"\n";
        }
        return string;
    }
}