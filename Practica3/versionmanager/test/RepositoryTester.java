package versionmanager.test;
import versionmanager.*;
import versionmanager.changes.*;
import versionmanager.commits.*;
import versionmanager.strategy.*;
import java.util.ArrayList;
import java.util.List;

/** 
 * La clase RepositoryTester representa las pruebas a los repositorios.
 * 
 * @author María Pozo, Carmen Gómez
 */
public class RepositoryTester {

    /** 
     * Método main que ejecuta las pruebas.
     * 
     * @param args      Argumentos recibidos.
     */
    public static void main(String[] args) {
        System.out.println("Test 1: (Standar code execution)");
        Branch mainBranch = testCreateMainBranch();
        Repository repository = testCreateRepository(mainBranch);
        testCreateBranchFromAnotherBranch(repository, mainBranch);
        testAddUserToRepository(repository, "John Doe");
        testAddCommitToRepository1(repository);
        System.out.println(repository);
        

        /** Test para añadir un commit a un repositorio de un usuario no autorizado*/
        System.out.println("Test 2: (Not authorised author)");
        List<Change> changes4 = new ArrayList<>();
        changes4.add(new AddChange(0, "/src/main/NuevaClase.java", "Hola\nMundo\n"));
        Commit commit4 = new ChangeCommit( "Doe John", changes4);
        testAddCommitToRepository2(repository, commit4);
        System.out.println(repository);
        System.out.println("As the author of the commit wasn't authorised, the commit wasn't added to the main branch.\n");

    }

    /**
     * Método que crea una rama principal.
     * 
     * @return La rama principal creada.
     */
    public static Branch testCreateMainBranch() {
        List<Change> changes1 = new ArrayList<>();
        changes1.add(new AddChange(0, "/src/main/NuevaClase.java", "Hola\nMundo\n"));
        changes1.add(new ModifyChange(0,0,"/src/main/ClaseExistente.java", "H"));
        changes1.add(new RemoveChange(0, 2,"/src/main/ClaseObsoleta.java"));

        List<Change> changes2 = new ArrayList<>();
        changes2.add(new AddChange(0, "/src/main/NuevaClase.java", "Buenos\ndias\nmundo\n"));
        changes2.add(new AddChange(0,"/src/main/ClaseExistente.java", "Java\n"));
        changes2.add(new ModifyChange(0, 0, "/src/main/ClaseObsoleta.java", "H"));

        Commit commit1 = new ChangeCommit( "John Doe", changes1);
        Commit commit2 = new ChangeCommit( "John Doe", "Decorator interface", changes2);

        List<Commit> mainCommits = new ArrayList<>();
        mainCommits.add(commit1);
        mainCommits.add(commit2);
        Branch mainBranch = new Branch("main", mainCommits);

        return mainBranch;
    }

    /**
     * Método que crea un repositorio.
     * 
     * @param mainBranch    La rama principal del repositorio.
     * 
     * @return El repositorio creado.
     */
    public static Repository testCreateRepository(Branch mainBranch) {
        ConflictStrategy strategy = new OriginStrategy();
        Repository repository = new Repository("ADSOF p3", mainBranch, strategy);
        return repository;
    }

    /**
     * Método que crea una rama a partir de otra en un repositorio.
     * 
     * @param repository    El repositorio.
     * @param mainBranch    La rama principal del repositorio.
     */
    public static void testCreateBranchFromAnotherBranch(Repository repository, Branch mainBranch) {
        repository.createNewBranchFromAnother("Solving issue #1", mainBranch);
    }

    /**
     * Método que crea añade un usuario autorizado a un repositorio.
     * 
     * @param repository    El repositorio.
     * @param name          Nombre del usuario.
     */
    public static void testAddUserToRepository(Repository repository, String name) {
        repository.addUser(name);
    }

    /**
     * Método que añade un commit a la rama activa de un repositorio.
     * 
     * @param repository    El repositorio.
     */
    public static void testAddCommitToRepository1(Repository repository) {
        Commit commit3 = new MergeCommit("John Doe", "Merging previous commits", repository.getMainBranch().getCommits());
        repository.addCommitMainBranch(commit3);
    }

    /**
     * Método que añade un commit a la rama activa de un repositorio.
     * 
     * @param repository    El repositorio.
     * @param commit        El commit.
     */
    public static void testAddCommitToRepository2(Repository repository, Commit commit) {
        repository.addCommitMainBranch(commit);
    }
}