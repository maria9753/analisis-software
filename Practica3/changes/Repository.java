package changes;

import java.util.*;

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
    public Repository(String name) {
        this.name = name;
        this.branches = new ArrayList<Branch>();
        this.users = new ArrayList<String>();
        this.mainBranch = new Branch("main", ); /** cambair esto para que cree un main branch, faltan los commit: ¿poder hacer un branch sin meterle commits?*/
        this.branches.add(mainBranch);
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
   public void createNewBranch(String name, Branch originBranch) {
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
     * Método para generar un repositorio.
     * @return Una cadena que contiene los detalles del repositorio.
     */
    @Override
    public String toString() {
        String string = "\nRepository: "+this.name+"\nBranches:";
        for (List<Branch> b: branches) {
            string += "\n "+b.getName();
            if (b == this.mainBranch) {
                string += " (active)";
            }
        }

        string += this.mainBranch.toString();

        return string;
    }
}