package changes;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase ChangeCommit representa un tipo de commit en el que se realizan cambios.
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class ChangeCommit extends Commit {
    /** Lista de cambios que contiene un ChangeCommit */
    private List<Change> changes;

    /**
     * Constructor de la clase ChangeCommit.
     * 
     * @param author       Autor del ChangeCommit.
     * @param description  Descripción del ChangeCommit.
     * @param changes      Cambios del ChangeCommit.
     */
    public ChangeCommit(String author, String description, List<Change> changes) {
        super(author, description);
        this.changes = new ArrayList<>();
    }

    /**
     * Método para añadir un cambio a un ChangeCommit.
     * 
     * @param change        Cambio.
     */
    public void addChange(Change change) {
        changes.add(change);
    }

    /**
     * Método para obtener los cambios de un ChangeCommit.
     * 
     * @return Una lista de los cambios que contiene el ChangeCommit.
     */
    @Override
    public List<Change> obtainTotalChanges() {
        return this.changes;
    }

    @Override
    public String toString() {
        String string= "\ncommit "+super.id+"\nAuthor: "+super.author+"\nDate: "+this.date+"\nDescription: "+super.description+"\n";
        int modifiedLines;

        for (Change c: this.changes) {
            if(c.getType()=="/"){
                modifiedLines=0;
            }
            else{
                modifiedLines=c.getNumberOfLines();
            }
            string += c.getType()+" : "+ c.getPath()+" ("+modifiedLines+")\n";
        }
        return string;
    }
}
