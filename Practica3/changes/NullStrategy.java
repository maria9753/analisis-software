package changes;

public class NullStrategy implements ConflictStrategy {
    @Override
    public Commit resolveConflict(Commit originCommit, Commit destinyCommit) {
        return null; 
    }
}
