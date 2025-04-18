package versionmanager.strategy;
import versionmanager.commits.*;
/**
 * La clase DestinyStrategy implementa una solución de conflictos que consiste 
 * en añadir el commit de la rama de destino.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class DestinyStrategy implements ConflictStrategy {
    /**
     * Constructor por defecto de la clase.
     */
    public DestinyStrategy(){
        /**Constructor vacío */
    }

    /**
     * Método que resuelve un conflicto entre dos commits.
     * 
     * @param originCommit           commit de origen.
     * @param destinyCommit          commit de destino.
     * 
     * @return El commit de la rama destino.
     */
    @Override
    public Commit resolveConflict(Commit originCommit, Commit destinyCommit) {
        return destinyCommit; 
    }
}
