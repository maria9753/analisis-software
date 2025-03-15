package changes;

public class DestinyStrategy implements ConflictStrategy {
    @Override
    public Commit resolveConflict(Commit originCommit, Commit destinyCommit) {
        return destinyCommit; 
    }
}
