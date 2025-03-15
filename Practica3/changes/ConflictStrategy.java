package changes;

/**
 * La interfaz ConflictStrategy representa cómo se van a solucionar los conflictos
 * que se produzcan entre commits a la hora de fusionar ramas.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public interface ConflictStrategy {
    /**
     * Método que resuelve un conflicto entre dos commits.
     * 
     * @param originCommit           commit de origen.
     * @param destinyCommit          commit de destino.
     * 
     * @return El commit que se ha seleccionado para añadir.
     */
    Commit resolveConflict(Commit originCommit, Commit destinyCommit);
}
