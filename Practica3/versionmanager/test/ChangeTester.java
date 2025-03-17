package versionmanager.test;
import versionmanager.changes.*;
import java.util.*;
/**
 * La clase ChangeTester representa las pruebas a los cambios. 
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class ChangeTester {
    public static void main (String[] args) {
        System.out.println("Test 1: (Standar code execution)");
        for (Change change : createChanges())
            System.out.println(change);
       
        System.out.println("Test 2: (Empty changes)");
        if(createEmptyChanges().isEmpty()){
            System.out.println("The list was empty, but caused no error.");
        }
        System.out.println("Test 3: (Null changes)");
        if(createChangesWithNulls().equals(createChanges())){
            System.out.println("Tried to add nulls, but the were not added.");
        }
    }

    public static List<Change> createChanges () {
        List<Change> changes = new ArrayList<>();
        Change c1 = new AddChange(0,"/src/main/NuevaClase.java", // adds in line 0 (i.e.,before line 1)
                                    "import java.util.*;\nimport java.io.*;");
        Change c2 = new ModifyChange(10, 10,"/src/main/ClaseExistente.java", // replaces line 10
                                    "// Modificación en la clase existente");
        Change c3 = new RemoveChange(1, 2,"/src/main/ClaseObsoleta.java"); // removes lines 1 and 2
        changes.add(c1);
        changes.add(c2);
        changes.add(c3);
        return changes;
    }
    public static List<Change> createEmptyChanges () {
        List<Change> emptyList = List.of();
        return emptyList;
    }

    public static List<Change> createChangesWithNulls() {
        List<Change> nullChanges = createChanges();
        Change c1 = new AddChange(-10,"/src/main/NuevaClase.java", "/src/main/NuevaClase.java");
        Change c2 = new ModifyChange(10, -10,"/src/main/ClaseExistente.java", "// Modificación en la clase existente");
        Change c3 = null;

        nullChanges.add(c1);
        nullChanges.add(c2);
        nullChanges.add(c3);
        return nullChanges;
    }
}