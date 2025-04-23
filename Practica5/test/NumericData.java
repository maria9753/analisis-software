
package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import src.tiposDatos.NumericData;

class NumericDataTest {

    @Test
    void testConstructorAndInitialValues() {
        NumericData data = new NumericData(5, 3);
        
        assertEquals(5, data.get("op1"));
        assertEquals(3, data.get("op2"));
        assertEquals(0, data.get("result"));
        assertEquals("{op1=5, op2=3, result=0}", data.toString());
    }

    @Test
    void testEdgeCasesInConstructor() {
        // Valores positivos
        NumericData positiveData = new NumericData(Integer.MAX_VALUE, 100);
        assertEquals(Integer.MAX_VALUE, positiveData.get("op1"));
        assertEquals(100, positiveData.get("op2"));
        
        // Valores negativos
        NumericData negativeData = new NumericData(-10, Integer.MIN_VALUE);
        assertEquals(-10, negativeData.get("op1"));
        assertEquals(Integer.MIN_VALUE, negativeData.get("op2"));
        
        // Ceros
        NumericData zeroData = new NumericData(0, 0);
        assertEquals(0, zeroData.get("op1"));
        assertEquals(0, zeroData.get("op2"));
    }

    @Test
    void testMapOperations() {
        NumericData data = new NumericData(8, 2);
        
        // Verificar que contiene las claves esperadas
        assertTrue(data.containsKey("op1"));
        assertTrue(data.containsKey("op2"));
        assertTrue(data.containsKey("result"));
        assertEquals(3, data.size());
        
        // Modificar valores
        data.put("op1", 10);
        data.put("result", 20);
        
        assertEquals(10, data.get("op1"));
        assertEquals(20, data.get("result"));
        assertEquals("{op1=10, op2=2, result=20}", data.toString());
    }

    @Test
    void testToString() {
        NumericData data1 = new NumericData(15, 30);
        assertEquals("{op1=15, op2=30, result=0}", data1.toString());
        
        data1.put("result", 45);
        assertEquals("{op1=15, op2=30, result=45}", data1.toString());
        
        NumericData data2 = new NumericData(-5, -3);
        assertEquals("{op1=-5, op2=-3, result=0}", data2.toString());
    }

    @Test
    void testInheritedMapBehavior() {
        NumericData data = new NumericData(7, 9);
        
        // Probando comportamiento heredado de LinkedHashMap
        assertFalse(data.isEmpty());
        assertTrue(data.containsValue(7));
        assertTrue(data.containsValue(9));
        assertTrue(data.containsValue(0));
        
        // Verificar orden de inserción (comportamiento de LinkedHashMap)
        String[] expectedKeys = {"op1", "op2", "result"};
        int i = 0;
        for (String key : data.keySet()) {
            assertEquals(expectedKeys[i++], key);
        }
    }
}