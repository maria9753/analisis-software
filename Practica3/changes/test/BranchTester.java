package test;
import changes.*;
import java.util.*;

public class BranchTester {

    public static void main(String[] args) {
        Branch mainBranch= testCreateBranch();
        testAddCommitToBranch(mainBranch);
        Branch issueBranch= testBranchFromAnotherBranch(mainBranch);
        System.out.println(mainBranch);
        System.out.println(issueBranch);
    }

    public static Branch testCreateBranch() {
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

    public static void testAddCommitToBranch(Branch mainBranch) {
        Commit commit3 = new MergeCommit( "John Doe", "Merging previous commits", mainBranch.getCommits());
        mainBranch.commit(commit3);
    }

    public static Branch testBranchFromAnotherBranch(Branch mainBranch) {
        Branch issueBranch = new Branch("Solving issue #1", mainBranch);
        return issueBranch;
    }
}
