package versionmanager.test;
import versionmanager.*;
import versionmanager.changes.*;
import versionmanager.commits.*;
import versionmanager.strategy.*;
import java.util.ArrayList;
import java.util.List;

/** 
 * La clase MergeBranchesTester representa las pruebas de la fusión de ramas en un repositorio.
 * 
 * @author María Pozo, Carmen Gómez
 */
public class MergeBranchesTester {
    
    /**
     * Constructor por defecto de la clase.
     */
    public MergeBranchesTester(){
        /**Constructor vacío */
    }

    /** 
     * Método main que ejecuta las pruebas.
     * 
     * @param args      Argumentos recibidos.
     */
    public static void main(String[] args) {
        /** Test 1.*/
        System.out.println("----------Test 1: (Standar code execution with DestinyStrategy)");
        Branch mainBranch1 = testCreateMainBranch();
        Repository repository1 = testCreateRepository(mainBranch1);
        testAddUserToRepository(repository1);
        testAddCommitToRepository(repository1);

        System.out.println(repository1);

        testCreateBranchFromAnotherBranch(repository1, mainBranch1);
        testChangeMainBranch(repository1);
        List<Change> solvingChanges1 = new ArrayList<>();
        solvingChanges1.add(new ModifyChange(0, 0, "/src/main/NuevaClase.java", "H"));
        Commit solvingCommit1 = new ChangeCommit("John Doe", "Solving the issue", solvingChanges1);
        repository1.addCommitMainBranch(solvingCommit1);

        System.out.println(repository1);

        ConflictStrategy strategy1 = new DestinyStrategy();
        repository1.mergeBranches("main", "Solving issue #1", strategy1);
        repository1.changeMainBranch(mainBranch1);

        System.out.println(repository1);

        /** Test 2.*/
        System.out.println("----------Test 2: (Standar code execution with repository's defect strategy (OriginStrategy))");
        Branch mainBranch2 = testCreateMainBranch();
        Repository repository2 = testCreateRepository(mainBranch2);
        testAddUserToRepository(repository2);
        testAddCommitToRepository(repository2);

        testCreateBranchFromAnotherBranch(repository2, mainBranch2);
        List<Change> solvingChanges2 = new ArrayList<>();
        solvingChanges2.add(new ModifyChange(0, 0, "/src/main/NuevaClase.java", "H"));
        Commit solvingCommit2 = new ChangeCommit("John Doe", "Solving the issue", solvingChanges2);
        repository2.addCommitMainBranch(solvingCommit2);

        System.out.println(repository2);

        testChangeMainBranch(repository2);
        List<Change> solvingChanges22 = new ArrayList<>();
        solvingChanges22.add(new ModifyChange(0, 0, "/src/main/NuevaClase.java", "H2"));
        Commit solvingCommit22 = new ChangeCommit("John Doe", "Solving the issue 2", solvingChanges22);
        repository2.addCommitMainBranch(solvingCommit22);

        System.out.println(repository2);

        repository2.mergeBranches("main", "Solving issue #1", null);
        System.out.println("Main branch: Solving issue #1");
        System.out.println(repository2);
        repository2.changeMainBranch(mainBranch2);
        System.out.println("Main branch: main");
        System.out.println(repository2);

        /** Test 3.*/
        System.out.println("----------Test 3: (Standar code execution with NullStrategy)");
        Branch mainBranch3 = testCreateMainBranch();
        Repository repository3 = testCreateRepository(mainBranch3);
        testAddUserToRepository(repository3);
        testAddCommitToRepository(repository3);

        testCreateBranchFromAnotherBranch(repository3, mainBranch3);
        List<Change> solvingChanges3 = new ArrayList<>();
        solvingChanges3.add(new ModifyChange(0, 0, "/src/main/NuevaClase.java", "H"));
        Commit solvingCommit3 = new ChangeCommit("John Doe", "Solving the issue", solvingChanges3);
        repository3.addCommitMainBranch(solvingCommit3);

        System.out.println(repository3);

        testChangeMainBranch(repository3);
        List<Change> solvingChanges32 = new ArrayList<>();
        solvingChanges32.add(new ModifyChange(0, 0, "/src/main/NuevaClase.java", "H2"));
        Commit solvingCommit32 = new ChangeCommit("John Doe", "Solving the issue 2", solvingChanges32);
        repository3.addCommitMainBranch(solvingCommit32);

        System.out.println(repository3);

        ConflictStrategy strategy3 = new NullStrategy();
        repository3.mergeBranches("main", "Solving issue #1", strategy3);
        System.out.println("Main branch: Solving issue #1");
        System.out.println(repository3);

        System.out.println("Main branch: main");
        repository3.changeMainBranch(mainBranch3);
        System.out.println(repository3);

        /** Test 4.*/
        System.out.println("----------Test 4: (Standar code execution with MergeStrategy with conflicting commit not add change)");
        Branch mainBranch4 = testCreateMainBranch();
        Repository repository4 = testCreateRepository(mainBranch4);
        testAddUserToRepository(repository4);
        testAddCommitToRepository(repository4);

        testCreateBranchFromAnotherBranch(repository4, mainBranch4);
        List<Change> solvingChanges4 = new ArrayList<>();
        solvingChanges4.add(new ModifyChange(0, 0, "/src/main/NuevaClase.java", "H"));
        Commit solvingCommit4 = new ChangeCommit("John Doe", "Solving the issue", solvingChanges4);
        repository4.addCommitMainBranch(solvingCommit4);

        System.out.println(repository4);

        testChangeMainBranch(repository4);
        List<Change> solvingChanges42 = new ArrayList<>();
        solvingChanges42.add(new ModifyChange(0, 0, "/src/main/NuevaClase.java", "H2"));
        Commit solvingCommit42 = new ChangeCommit("John Doe", "Solving the issue 2", solvingChanges42);
        repository4.addCommitMainBranch(solvingCommit42);

        System.out.println(repository4);

        ConflictStrategy strategy4 = new MergeStrategy();
        repository4.mergeBranches("main", "Solving issue #1", strategy4);
        System.out.println("Main branch: Solving issue #1");
        System.out.println(repository4);

        System.out.println("Main branch: main");
        repository4.changeMainBranch(mainBranch4);
        System.out.println(repository4);

        /** Test 5.*/
        System.out.println("----------Test 5: (Standar code execution with MergeStrategy with conflicting commit add change)");
        Branch mainBranch5 = testCreateMainBranch();
        Repository repository5 = testCreateRepository(mainBranch5);
        testAddUserToRepository(repository5);
        testAddCommitToRepository(repository5);


        testCreateBranchFromAnotherBranch(repository5, mainBranch5);
        List<Change> solvingChanges5 = new ArrayList<>();
        solvingChanges5.add(new AddChange(0, "/src/main/NuevaClase.java", "H"));
        Commit solvingCommit5 = new ChangeCommit("John Doe", "Solving the issue", solvingChanges5);
        repository5.addCommitMainBranch(solvingCommit5);

        System.out.println(repository5);

        testChangeMainBranch(repository5);
        List<Change> solvingChanges52 = new ArrayList<>();
        solvingChanges52.add(new AddChange(0, "/src/main/NuevaClase.java", "H2"));
        Commit solvingCommit52 = new ChangeCommit("John Doe", "Solving the issue 2", solvingChanges52);
        repository5.addCommitMainBranch(solvingCommit52);      

        System.out.println(repository5);

        ConflictStrategy strategy5 = new MergeStrategy();
        repository5.mergeBranches("main", "Solving issue #1", strategy5);
        System.out.println("Main branch: Solving issue #1");
        System.out.println(repository5);

        System.out.println("Main branch: main");
        repository5.changeMainBranch(mainBranch5);
        System.out.println(repository5);

        /** Test 6.*/
        System.out.println("----------Test 6: (Standar code execution with no conflicting commit)");
        Branch mainBranch6 = testCreateMainBranch();
        Repository repository6 = testCreateRepository(mainBranch6);
        testAddUserToRepository(repository6);
        testAddCommitToRepository(repository6);

        System.out.println(repository6);

        testCreateBranchFromAnotherBranch(repository6, mainBranch6);
        testChangeMainBranch(repository6);
        List<Change> solvingChanges6 = new ArrayList<>();
        solvingChanges6.add(new ModifyChange(0, 0, "/src/main/NuevaNuevaClase.java", "H"));
        Commit solvingCommit6 = new ChangeCommit("John Doe", "Solving the issue", solvingChanges6);
        repository6.addCommitMainBranch(solvingCommit6);

        System.out.println(repository6);

        ConflictStrategy strategy6 = new MergeStrategy();
        repository6.mergeBranches("main", "Solving issue #1", strategy6);
        System.out.println("Main branch: Solving issue #1");
        System.out.println(repository6);

        System.out.println("Main branch: main");
        repository6.changeMainBranch(mainBranch6);
        System.out.println(repository6);

        /** Test 7.*/
        System.out.println("----------Test 7: (Standar code execution with the same commits)");
        Branch mainBranch7 = testCreateMainBranch();
        Repository repository7 = testCreateRepository(mainBranch7);
        testAddUserToRepository(repository7);
        testAddCommitToRepository(repository7);

        System.out.println(repository7);

        testCreateBranchFromAnotherBranch(repository7, mainBranch7);

        System.out.println(repository7);

        repository7.mergeBranches("main", "Solving issue #1", null);
        System.out.println("Main branch: Solving issue #1");
        System.out.println(repository7);

        System.out.println("Main branch: main");
        repository7.changeMainBranch(mainBranch7);
        System.out.println(repository7);
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

        Commit commit1 = new ChangeCommit("John Doe", changes1);
        Commit commit2 = new ChangeCommit("John Doe", "Decorator interface", changes2);

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
     */
    public static void testAddUserToRepository(Repository repository) {
        repository.addUser("John Doe");
    }

    /**
     * Método que añade un commit a la rama activa de un repositorio.
     * 
     * @param repository    El repositorio.
     */
    public static void testAddCommitToRepository(Repository repository) {
        Commit mergeCommit = new MergeCommit("John Doe", "Merging previous commits", repository.getMainBranch().getCommits());
        repository.addCommitMainBranch(mergeCommit);
    }

    /**
     * Método que cambia la rama activa de un repositorio.
     * 
     * @param repository    El repositorio.
     */
    public static void testChangeMainBranch(Repository repository) {
        Branch solvingBranch = repository.getBranchByName("Solving issue #1");
        repository.changeMainBranch(solvingBranch);
    }
}
