package versionmanager.strategy;
import versionmanager.changes.*;
import versionmanager.commits.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase MergeStrategy implementa una solución de conflictos que consiste 
 * en combinar ambos commits si sus cambios son sólo de tipo addChange.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class MergeStrategy implements ConflictStrategy {
    /**
     * Método que resuelve un conflicto entre dos commits.
     * 
     * @param originCommit           commit de origen.
     * @param destinyCommit          commit de destino.
     * 
     * @return La combinacion de ambos si son tipo AddChange.
     */
    @Override
    public Commit resolveConflict(Commit originCommit, Commit destinyCommit) {
        if (isAddOnlyConflict(originCommit, destinyCommit)) {
            List<Commit> mergeCommits =new ArrayList<>();
            mergeCommits.add(originCommit);
            mergeCommits.add(destinyCommit);
            MergeCommit mergeCommit = new MergeCommit("Merge add only changes", mergeCommits);
            return mergeCommit;
        }
        return null;
    }

    /**
     * Método que comprueba si los cambios son de tipo AddChange.
     *
     * @param originCommit           commit de origen.
     * @param destinyCommit          commit de destino.
     * @return true si los cambios de los commits son AddChange, si no false.
     */
    private boolean isAddOnlyConflict(Commit originCommit, Commit destinyCommit) {
        boolean isAdd=true;
        for(Change c: originCommit.obtainTotalChanges()){
            if(!c.getType().equals("+")){
                isAdd=false;
            }
        }
        for(Change c: destinyCommit.obtainTotalChanges()){
            if(!c.getType().equals("+")){
                isAdd=false;
            }
        }
        return isAdd;
    }
}