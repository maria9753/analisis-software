package changes;
import java.util.*;

public class CommitTester{

    public static void main (String[] args) {
        System.out.println(commit1);
        System.out.println(commit2);
        System.out.println(mergeCommit);

    }

    public static List<Change> createChanges () {
        List<Change> changes1 = new ArrayList<>();
        changes1.add(new Change("+", "/src/main/NuevaClase.java", 2));
        changes1.add(new Change("/", "/src/main/ClaseExistente.java", 0));
        changes1.add(new Change("-", "/src/main/ClaseObsoleta.java", -2));

        List<Change> changes2 = new ArrayList<>();
        changes2.add(new Change("+", "/src/pkg1/Decorator.java", 3));
        changes2.add(new Change("+", "/src/pkg1/Decorator.java", 1));
        changes2.add(new Change("/", "/src/pkg1/Decorator.java", 0));

        return List.of(changes1, changes2);
    }

    public static List<ChangeCommit> createChangeCommits () {
        ChangeCommit changeCommit1 = new ChangeCommit("John Doe", "no comment", changes1);
        ChangeCommit changeCommit2 = new ChangeCommit("John Doe", "Decorator interface", changes2);

        return List.of(changeCommit1, changeCommit2);
    }

    public static MergeCommit createMergeCommit () {
        List<ChangeCommit> changeCommits = new ArrayList<>();
        changeCommits.add(changeCommit1);
        changeCommits.add(changeCommit2);
        MergeCommit mergeCommit = new MergeCommit("John Doe", changeCommits);

        return mergeCommit;
    }
}
