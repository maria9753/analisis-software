package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import src.tiposDatos.StringData;

class StringDataTest {

    @Test
    void testConstructorAndInitialState() {
        StringData data = new StringData("hello");
        
        assertEquals("hello", data.get("text"));
        assertEquals("", data.get("result"));
        assertEquals("{text=hello, result=}", data.toString());
    }

    @Test
    void testEmptyStringInput() {
        StringData emptyData = new StringData("");
        
        assertEquals("", emptyData.get("text"));
        assertEquals("", emptyData.get("result"));
        assertEquals("{text=, result=}", emptyData.toString());
    }

    @Test
    void testNullInput() {
        StringData nullData = new StringData(null);
        
        assertNull(nullData.get("text"));
        assertEquals("", nullData.get("result"));
        assertEquals("{text=null, result=}", nullData.toString());
    }

    @Test
    void testModifyingValues() {
        StringData data = new StringData("initial");
        
        // Modificar el texto
        data.put("text", "modified");
        assertEquals("modified", data.get("text"));
        assertEquals("{text=modified, result=}", data.toString());
        
        // Modificar el resultado
        data.put("result", "output");
        assertEquals("output", data.get("result"));
        assertEquals("{text=modified, result=output}", data.toString());
    }

    @Test
    void testSpecialCharacters() {
        StringData specialData = new StringData("áéíóú@#€123");
        
        assertEquals("áéíóú@#€123", specialData.get("text"));
        assertEquals("", specialData.get("result"));
        assertEquals("{text=áéíóú@#€123, result=}", specialData.toString());
    }

    @Test
    void testMapBehavior() {
        StringData data = new StringData("test");
        
        // Verificar propiedades del mapa
        assertEquals(2, data.size());
        assertTrue(data.containsKey("text"));
        assertTrue(data.containsKey("result"));
        assertFalse(data.containsKey("invalid"));
        assertTrue(data.containsValue("test"));
        assertTrue(data.containsValue(""));
    }

    @Test
    void testToStringFormat() {
        StringData data1 = new StringData("sample");
        assertEquals("{text=sample, result=}", data1.toString());
        
        data1.put("result", "processed");
        assertEquals("{text=sample, result=processed}", data1.toString());
        
        StringData data2 = new StringData("multi\nline");
        assertEquals("{text=multi\nline, result=}", data2.toString());
    }
}