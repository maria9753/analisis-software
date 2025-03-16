package versionmanager;
import versionmanager.changes.*;
import versionmanager.commits.*;
import versionmanager.strategy.*;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Repository representa un repositorio que contiene una rama principal
 * y opcionalmente más ramas.
 * 
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
    /** Estrategia para resolver conflictos*/
    private ConflictStrategy strategy;

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
        this.strategy= new OriginStrategy();
    }

    /**
     * Constructor de la clase Repository.
     * 
     * @param name          Nombre del repositorio.
     */
    public Repository(String name, Branch mainBranch, ConflictStrategy strategy) {
        this.name = name;
        this.branches = new ArrayList<Branch>();
        this.users = new ArrayList<String>();
        this.mainBranch = mainBranch;
        this.branches.add(mainBranch);
        this.strategy= strategy;
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

    /**
     * Método que fusiona dos ramas, siempre y cuando no haya conflictos, o los
     * conflictos se resuelvan.
     * 
     * @param originName           Nombre de la rama origen.
     * @param destinyName          Nombre de la rama destino.
     */
    public void mergeBranches(String originName, String destinyName) {
        Branch originBranch = getBranchByName(originName);
        Branch destinyBranch = getBranchByName(destinyName);
        
        if (originBranch == null || destinyBranch == null) {
            return;
        }
        
        List<Commit> originCommits = originBranch.getCommits();
        List<Commit> destinyCommits = destinyBranch.getCommits();

        /** Se obtiene el índice en el que está el último commit común */
        int indexLastCommit = -1;
        for (int i = 0; i < originCommits.size() && i < destinyCommits.size(); i++) {
            if (originCommits.get(i).equals(destinyCommits.get(i))) {
                indexLastCommit = i; 
            } else {
                break;
            }
        }

        /** Si no hay commits en común, la fusión no se puede realizar */
        if(indexLastCommit == -1){
            return;
        }

        /** Se crean listas con los commits que no tienen en común, para fusionarlos */
        List<Commit> originNewCommits = originCommits.subList(indexLastCommit, originCommits.size());
        List<Commit> destinyNewCommits = destinyCommits.subList(indexLastCommit, destinyCommits.size());

        List<Commit> mergedCommits = new ArrayList<>();
        List<String> conflicts = new ArrayList<>();
        List<Commit> commitsToRemove= new ArrayList<>();
        
        /** Se buscan conflictos */
        for(Commit originCommit : originNewCommits) {
            boolean hasConflict=false;
            for (Commit destinyCommit : destinyNewCommits) {
                if (originCommit.detectConflicts(destinyCommit)==true){
                    hasConflict=true;
                    conflicts.add("Conflict on '" +originCommit.getModifiedConflictFile(destinyCommit)+ "'");
                    Commit resolvedCommit = this.strategy.resolveConflict(originCommit, destinyCommit);
                    if (resolvedCommit == null) {
                        return;
                    }
                    else if(resolvedCommit!=destinyCommit){
                        commitsToRemove.add(destinyCommit);
                    }
                    mergedCommits.add(resolvedCommit);
                    break;
                }  
            }
            if(hasConflict==false){
                mergedCommits.add(originCommit);  
            }
        }

        for (Commit commitToRemove : commitsToRemove) {
            destinyBranch.removeCommit(commitToRemove);
        }

        /** Si hay conflictos sin solucionar, estos se imprimen y la fusión no se realiza*/
        if (conflicts.isEmpty()==false) {
            System.out.println(conflicts);
            return;
        }

        /** Se crea un MergeCommit con los commits fusionados en la rama destino*/
        MergeCommit mergeCommit = new MergeCommit("Merge branches " + originName + " into " + destinyName, mergedCommits);
        destinyBranch.commit(mergeCommit);
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