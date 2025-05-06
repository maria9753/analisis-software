package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import src.tiposDatos.DoubleData;

class DoubleDataTest {

    @Test
    void testConstructorAndGetters() {
        DoubleData data = new DoubleData(5.5, 3.2);

        assertEquals(5.5, data.getValue(), 0.001);
        assertEquals(3.2, data.getAverage(), 0.001);
    }

    @Test
    void testToString() {
        DoubleData data1 = new DoubleData(10.123, 5.456);
        assertEquals("10,1 (avg.= 5,456)", data1.toString());

        DoubleData data2 = new DoubleData(3.14159, 2.71828);
        assertEquals("3,1 (avg.= 2,718)", data2.toString());

        DoubleData data3 = new DoubleData(0.0, 0.0);
        assertEquals("0,0 (avg.= 0,000)", data3.toString());
    }

    @Test
    void testSetAverage() {
        DoubleData data = new DoubleData(7.5, 2.0);
        assertEquals(2.0, data.getAverage(), 0.001);

        data.setAverage(4.8);
        assertEquals(4.8, data.getAverage(), 0.001);

        data.setAverage(0.0);
        assertEquals(0.0, data.getAverage(), 0.001);

        data.setAverage(-3.2);
        assertEquals(-3.2, data.getAverage(), 0.001);
    }

    @Test
    void testEdgeCases() {
        DoubleData maxData = new DoubleData(Double.MAX_VALUE, Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, maxData.getValue());
        assertEquals(Double.MAX_VALUE, maxData.getAverage());

        DoubleData minData = new DoubleData(Double.MIN_VALUE, Double.MIN_VALUE);
        assertEquals(Double.MIN_VALUE, minData.getValue());
        assertEquals(Double.MIN_VALUE, minData.getAverage());

        DoubleData nanData = new DoubleData(Double.NaN, Double.NaN);
        assertTrue(Double.isNaN(nanData.getValue()));
        assertTrue(Double.isNaN(nanData.getAverage()));

        DoubleData infinityData = new DoubleData(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
        assertEquals(Double.POSITIVE_INFINITY, infinityData.getValue());
        assertEquals(Double.NEGATIVE_INFINITY, infinityData.getAverage());
    }

    @Test
    void testPrecisionInToString() {
        DoubleData data1 = new DoubleData(123.456789, 987.654321);
        assertEquals("123,5 (avg.= 987,654)", data1.toString());

        DoubleData data2 = new DoubleData(0.999, 0.0005);
        assertEquals("1,0 (avg.= 0,001)", data2.toString());
    }
}