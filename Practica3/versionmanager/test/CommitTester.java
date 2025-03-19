package versionmanager.test;
import versionmanager.changes.*;
import versionmanager.commits.*;
import java.util.*;

/**
 * La clase CommitTester representa las pruebas a los commits. 
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class CommitTester{

    /**
     * Constructor por defecto de la clase.
     */
    public CommitTester(){
        /**Constructor vacío */
    }

    /**
     * Método main que ejecuta las pruebas.
     * 
     * @param args      Argumentos recibidos.
     */
    public static void main(String[] args) {
        System.out.println("Test 1: (Standar code execution)");
        List<Change> changes1 = createChanges1(); 
        List<Change> changes2 = createChanges2(); 
        List<Commit> commits = createCommits(changes1, changes2); 
        MergeCommit mergeCommit = createMergeCommit(commits); 
        System.out.println(commits.get(0));
        System.out.println(commits.get(1));
        System.out.println(mergeCommit);

        System.out.println("Test 2: (Empty changes)");
        if(testEmptyChanges().obtainTotalChanges().isEmpty()){
            System.out.println("The list was empty, but caused no error.");
        }

        System.out.println("Test 3: (Empty commits)");
        if(testEmptyCommits().obtainTotalCommits().isEmpty()){
            System.out.println("The list was empty, but caused no error.");
        }
    }

    /**
     * Método que crea cambios.
     * 
     * @return Lista de cambios.
     */
    public static List<Change> createChanges1 () {
        List<Change> changes1 = new ArrayList<>();
        changes1.add(new AddChange(0, "/src/main/NuevaClase.java", "Hola\nMundo\n"));
        changes1.add(new ModifyChange(0,0,"/src/main/ClaseExistente.java", "H"));
        changes1.add(new RemoveChange(0, 2,"/src/main/ClaseObsoleta.java"));

        return changes1;
    }

    /**
     * Método que crea cambios.
     * 
     * @return Lista de cambios.
     */
    public static List<Change> createChanges2 () {
        List<Change> changes2 = new ArrayList<>();
        changes2.add(new AddChange(0, "/src/main/NuevaClase.java", "Buenos\ndias\nmundo\n"));
        changes2.add(new AddChange(0,"/src/main/ClaseExistente.java", "Java\n"));
        changes2.add(new ModifyChange(0, 0, "/src/main/ClaseObsoleta.java", "H"));

        return changes2;
    }

    /**
     * Método que crea commits.
     * 
     * @param changes1      Lista de cambios.
     * @param changes2      Lista de cambios.
     * @return Lista de commits.
     */
    public static List<Commit> createCommits (List<Change> changes1, List<Change> changes2) {
        Commit changeCommit1 = new ChangeCommit("John Doe", changes1);
        Commit changeCommit2 = new ChangeCommit("John Doe", "Decorator interface", changes2);
        return List.of(changeCommit1, changeCommit2);
    }

     /**
     * Método que crea un MergeCommit.
     * 
     * @param commits   Lista de commits.
     * @return MergeCommit.
     */
    public static MergeCommit createMergeCommit (List<Commit> commits) {
        MergeCommit mergeCommit = new MergeCommit("Bob","Decorator interface",commits);
        return mergeCommit;
    }

    /**
     * Método que crea un ChangeCommit vacío.
     * 
     * @return ChangeCommit.
     */
    public static ChangeCommit testEmptyChanges() {
        ChangeCommit emptyCommit = new ChangeCommit("Shakespeare", new ArrayList<>());
        return emptyCommit;
    }

    /**
     * Método que crea un MergeCommit vacío.
     * 
     * @return MergeCommit.
     */
    public static MergeCommit testEmptyCommits() {
        MergeCommit mergeCommit = new MergeCommit("Bob","Decorator interface",new ArrayList<>());
        return mergeCommit;
    }
}
