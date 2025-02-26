package changes;
/**
 * La clase ChangeTester representa las pruebas a los cambios. 
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class ChangeTester {
    public static void main (String[] args) {
        for (Change change : createChanges())
            System.out.println(change);
        }

    public static List<Change> createChanges () {
        Change c1 = new AddChange (0,"/src/main/NuevaClase.java", // adds in line 0 (i.e.,before line 1)
                                    "import java.util.*;\nimport java.io.*;");
        Change c2 = new ModifyChange(10, 10,"/src/main/ClaseExistente.java", // replaces line 10
                                    "// Modificación en la clase existente");
        Change c3 = new RemoveChange(1, 2,"/src/main/ClaseObsoleta.java"); // removes lines 1 and 2
        return List.of(c1,c2,c3);
    }
}