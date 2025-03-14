package changes;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Repository representa 
 * @author Carmen Gómez, María Pozo.
 */
public class Repository {
    /** Nombre del repositorio*/
    private String name;
    /** Rama activa del repositorio*/
    private Branch mainBranch;
    /** Ramas del repositorio*/
    private List<Branch> branches;
    /** Usuarios con permiso*/
    private List<String> users;

    /**
     * Constructor de la clase Repository.
     * 
     * @param name          Nombre del repositorio.
     */
    public Repository(String name, Branch mainBranch) {
        this.name = name;
        this.branches = new ArrayList<Branch>();
        this.users = new ArrayList<String>();
        this.mainBranch = mainBranch;
        this.branches.add(mainBranch);
    }

    /**
     * Método para obtener la rama activa del repositorio.
     * 
     * @return La rama activa del repositorio.
     */
    public Branch getMainBranch(){
        return this.mainBranch;
    }

    /** Método para añadir usuarios con permiso al repositorio.
     * 
     * @param user  Usuario nuevo.
     */
    public void addUser(String user) {
        if (!this.users.contains(user)){
            this.users.add(user);
        }
    }

    /** Método para crear una rama nueva a partir de otra.
     * 
     * @param name  Nombre de la nueva rama.
     * @param branch Rama a partir de la cual se crea una nueva.
    */
   public void createNewBranchFromAnother(String name, Branch originBranch) {
        Branch newBranch = new Branch(name, originBranch);
        if (newBranch != null) {
            this.branches.add(newBranch);
        }
   }

    /** 
     * Método para cambiar la rama activa del repositorio.
     * 
     * @param newMainBranch  Nueva rama activa del repositorio.
    */
    public void changeMainBranch(Branch newMainBranch) {
        if (newMainBranch != null && this.branches.contains(newMainBranch)) {
            this.mainBranch = newMainBranch;
        }
    }

    /** 
     * Método para realizar un commit en la rama activa.
     * 
     * @param commit   Commit a añadir a la rama activa del repositorio.
     */
    public void addCommitMainBranch(Commit commit) {
        if (this.users.contains(commit.getAuthor())) {
            this.mainBranch.commit(commit);
        }
    }

    /** 
     * Método para obtener una rama del repositorio por su nombre.
     * 
     * @param name  Nombre de la rama.
     * 
     * @return La rama con nombre name.
     */
    public Branch getBranchByName(String name) {
        for (Branch b: this.branches) {
            if (name == b.getName()) {
                return b;
            }
        }

        return null;
    }

    public void mergeBranchs(String originName, String destinyName) {
        Branch originBranch = getBranchByName(originName);
        if (originBranch == null) {
            return;
        }

        List<Commit> originCommits = originBranch.getCommits();

        Branch destinyBranch = getBranchByName(destinyName);
        if (destinyBranch == null) {
            return;
        }

        List<Commit> destinyCommits = destinyBranch.getCommits();

        int indexLastCommit = 0;
        while (originCommits[indexLastCommit] == destinyCommits[indexLastCommit]) {
            indexLastCommit += 1;
        }

        
    }

    /**
     * Método para generar un repositorio.
     * 
     * @return Una cadena que contiene los detalles del repositorio.
     */
    @Override
    public String toString() {
        String string = "\nRepository: "+this.name+"\nBranches:";
        for (Branch b: this.branches) {
            string += "\n- "+b.getName();
            if (b == this.mainBranch) {
                string += " (active)";
            }
        }

        string += this.mainBranch.toString();

        return string;
    }
}