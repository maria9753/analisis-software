package changes;

public class OriginStrategy implements ConflictStrategy {
    @Override
    public Commit resolveConflict(Commit originCommit, Commit destinyCommit) {
        return originCommit; 
    }
}
