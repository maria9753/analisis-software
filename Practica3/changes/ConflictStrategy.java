package changes;

public interface ConflictStrategy {
    Commit resolveConflict(Commit originCommit, Commit destinyCommit);
}
