package versionmanager.strategy;
import versionmanager.commits.*;

/**
 * La clase OriginStrategy implementa una solución de conflictos que consiste 
 * en añadir el commit de la rama de origen.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class OriginStrategy implements ConflictStrategy {
    /**
     * Constructor por defecto de la clase.
     */
    public OriginStrategy(){
        /**Constructor vacío */
    }

    /**
     * Método que resuelve un conflicto entre dos commits.
     * 
     * @param originCommit           commit de origen.
     * @param destinyCommit          commit de destino.
     * 
     * @return El commit de la rama origen.
     */
    @Override
    public Commit resolveConflict(Commit originCommit, Commit destinyCommit) {
        return originCommit; 
    }
}
