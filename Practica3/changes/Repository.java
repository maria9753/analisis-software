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
    private List<String> = users;

    /**
     * Constructor de la clase Repository.
     * 
     * @param name          Nombre del repositorio.
     * @param mainBranch    Rama activa del repositorio.
     */
    public Repository(String name, Branch mainBranch) {
        this.name = name;
        this.mainBranch = mainBranch;
        this.branches.add(mainBranch);
    }

    /** Método para añadir usuarios con permiso al repositorio.
     * 
     * @param user  Usuario nuevo.
     */
    public void addUser(String user) {
        this.users.add(user);
    }

    /** Método para crear una rama nueva a partir de otra.
     * 
     * @param name  Nombre de la nueva rama.
     * @param branch Rama a partir de la cual se crea una nueva.
    */
   public void createNewBranch(String name, Branch branch) {
        Branch newBranch = new Branch(name);

   }

   /** Método para cambiar la rama activa del repositorio
    * @param newMainBranch
    */
   public void changeMainBranch(Branch newMainBranch) {
    this.mainBranch = newMainBranch;
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
            if (b.getName().equals(this.mainBranch.getName())) {
                string += " (active)";
            }
        }

        string += this.mainBranch.toString();

        return string;
    }
}