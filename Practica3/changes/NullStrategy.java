package changes;

/**
 * La clase NullStrategy implementa una solución de conflictos que consiste 
 * en no solucionar el conflicto.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class NullStrategy implements ConflictStrategy {
    /**
     * Método que resuelve un conflicto entre dos commits.
     * 
     * @param originCommit           commit de origen.
     * @param destinyCommit          commit de destino.
     * 
     * @return null.
     */
    @Override
    public Commit resolveConflict(Commit originCommit, Commit destinyCommit) {
        return null; 
    }
}
