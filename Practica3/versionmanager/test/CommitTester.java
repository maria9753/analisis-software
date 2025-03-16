package versionmanager.test;
import versionmanager.changes.*;
import versionmanager.commits.*;
import java.util.*;

public class CommitTester{

    public static void main(String[] args) {
        List<Change> changes1 = createChanges1(); 
        List<Change> changes2 = createChanges2(); 

        List<Commit> commits = createCommits(changes1, changes2); 

        MergeCommit mergeCommit = createMergeCommit(commits); 

        System.out.println(commits.get(0));
        System.out.println(commits.get(1));
        System.out.println(mergeCommit);
    }

    public static List<Change> createChanges1 () {
        List<Change> changes1 = new ArrayList<>();
        changes1.add(new AddChange(0, "/src/main/NuevaClase.java", "Hola\nMundo\n"));
        changes1.add(new ModifyChange(0,0,"/src/main/ClaseExistente.java", "H"));
        changes1.add(new RemoveChange(0, 2,"/src/main/ClaseObsoleta.java"));

        return changes1;
    }

     public static List<Change> createChanges2 () {
        List<Change> changes2 = new ArrayList<>();
        changes2.add(new AddChange(0, "/src/main/NuevaClase.java", "Buenos\ndias\nmundo\n"));
        changes2.add(new AddChange(0,"/src/main/ClaseExistente.java", "Java\n"));
        changes2.add(new ModifyChange(0, 0, "/src/main/ClaseObsoleta.java", "H"));

        return changes2;
    }

    public static List<Commit> createCommits (List<Change> changes1, List<Change> changes2) {
        Commit changeCommit1 = new ChangeCommit("John Doe", changes1);
        Commit changeCommit2 = new ChangeCommit("John Doe", "Decorator interface", changes2);
        return List.of(changeCommit1, changeCommit2);
    }

    public static MergeCommit createMergeCommit (List<Commit> commits) {
        MergeCommit mergeCommit = new MergeCommit(commits);
        return mergeCommit;
    }
}
