package versionmanager.test;
import versionmanager.changes.*;
import java.util.*;
/**
 * La clase ChangeTester representa las pruebas a los cambios. 
 * 
 * @author Carmen Gómez, María Pozo.
 */
public class ChangeTester {

    /**
     * Constructor por defecto de la clase.
     */
    public ChangeTester(){
        /**Constructor vacío */
    }

     /**
     * Método main que ejecuta las pruebas.
     * 
     * @param args      Argumentos recibidos.
     */
    public static void main (String[] args) {
        System.out.println("Test 1: (Standar code execution)");
        for (Change change : createChanges())
            System.out.println(change);
       
        System.out.println("Test 2: (Empty changes)");
        if(createEmptyChanges().isEmpty()){
            System.out.println("The list was empty, but caused no error.");
        }
    }

    /**
     * Método que crea cambios.
     * 
     * @return Lista de cambios.
     */
    public static List<Change> createChanges () {
        List<Change> changes = new ArrayList<>();
        Change c1 = new AddChange(0,"/src/main/NuevaClase.java", "import java.util.*;\nimport java.io.*;");
        Change c2 = new ModifyChange(10, 10,"/src/main/ClaseExistente.java", "// Modificación en la clase existente");
        Change c3 = new RemoveChange(1, 2,"/src/main/ClaseObsoleta.java");
        changes.add(c1);
        changes.add(c2);
        changes.add(c3);
        return changes;
    }

    /**
     * Método que crea cambios vacíos.
     * 
     * @return Lista de cambios.
     */
    public static List<Change> createEmptyChanges () {
        List<Change> emptyList = new ArrayList<>();
        return emptyList;
    }
}