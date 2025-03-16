package versionmanager.test;
import versionmanager.*;
import versionmanager.changes.*;
import versionmanager.commits.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryTester {

    public static void main(String[] args) {
        Branch mainBranch = testCreateMainBranch();
        Repository repository = testCreateRepository(mainBranch);
        testCreateBranchFromAnotherBranch(repository, mainBranch);
        testAddUserToRepository(repository);
        testAddCommitToRepository(repository);
        System.out.println(repository);
        
    }

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

    public static Repository testCreateRepository(Branch mainBranch) {
        Repository repository = new Repository("ADSOF p3", mainBranch);

        return repository;
    }

    public static void testCreateBranchFromAnotherBranch(Repository repository, Branch mainBranch) {
        repository.createNewBranchFromAnother("Solving issue #1", mainBranch);
    }

    public static void testAddUserToRepository(Repository repository) {
        repository.addUser("John Doe");
    }

    public static void testAddCommitToRepository(Repository repository) {
        Commit commit3 = new MergeCommit("John Doe", "Merging previous commits", repository.getMainBranch().getCommits());
        repository.addCommitMainBranch(commit3);
    }
}