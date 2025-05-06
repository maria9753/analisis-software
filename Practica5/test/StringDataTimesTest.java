package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.tiposDatos.StringDataTimes;
import src.tiposDatos.NumericData;

class StringDataTimesTest {

    private StringDataTimes stringData;

    @BeforeEach
    void setUp() {
        stringData = new StringDataTimes("test", 3);
    }

    @Test
    void testConstructorAndInitialState() {
        assertEquals("test", stringData.getWord());
        assertEquals(3, stringData.times());
        assertEquals("", stringData.getResult());
        assertEquals("word: test, times: 3, result: ", stringData.toString());
    }

    @Test
    void testReplicate() {
        // Primera replicación
        stringData.replicate();
        assertEquals("test", stringData.getResult());
        assertEquals(2, stringData.times());

        // Segunda replicación
        stringData.replicate();
        assertEquals("testtest", stringData.getResult());
        assertEquals(1, stringData.times());

        // Tercera replicación
        stringData.replicate();
        assertEquals("testtesttest", stringData.getResult());
        assertEquals(0, stringData.times());

        // Intentar replicar cuando times es 0
        stringData.replicate();
        assertEquals("testtesttest", stringData.getResult()); // No debe cambiar
        assertEquals(0, stringData.times());
    }

    @Test
    void testSetTimes() {
        stringData.setTimes(10);
        assertEquals(10, stringData.times());

        stringData.setTimes(0);
        assertEquals(0, stringData.times());

        stringData.setTimes(-5);
        assertEquals(-5, stringData.times());
    }

    @Test
    void testEdgeCases() {
        StringDataTimes emptyStringData = new StringDataTimes("", 2);
        emptyStringData.replicate();
        assertEquals("", emptyStringData.getResult()); 
        assertEquals(1, emptyStringData.times());  

        StringDataTimes zeroTimesData = new StringDataTimes("abc", 0);
        zeroTimesData.replicate();
        assertEquals("", zeroTimesData.getResult()); 
        assertEquals(0, zeroTimesData.times());    

        StringDataTimes normalData = new StringDataTimes("x", 3);
        normalData.replicate();
        assertEquals("x", normalData.getResult());    
        assertEquals(2, normalData.times());       
        
        normalData.replicate();
        assertEquals("xx", normalData.getResult());  
        assertEquals(1, normalData.times());       
        
        normalData.replicate();
        assertEquals("xxx", normalData.getResult()); 
        assertEquals(0, normalData.times());        
        
        normalData.replicate();
        assertEquals("xxx", normalData.getResult());  
        assertEquals(0, normalData.times());         
    }

    @Test
    void testToString() {
        StringDataTimes data = new StringDataTimes("hello", 2);
        assertEquals("word: hello, times: 2, result: ", data.toString());

        data.replicate();
        assertEquals("word: hello, times: 1, result: hello", data.toString());

        data.replicate();
        assertEquals("word: hello, times: 0, result: hellohello", data.toString());
    }

    @Test
    void testGetWordWithNull() {
        StringDataTimes nullWordData = new StringDataTimes(null, 1);
        assertEquals("", nullWordData.getWord());
    }
}