package ua.edu.ucu.stream;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class AsIntStreamTest {

    private IntStream intStream;
    private IntStream emptyStream;

    @Before
    public void init() {
        int[] intArr = {-1, 0, 1, 2, 3};
        int[] emptyArr = new int[0];
        intStream = AsIntStream.of(intArr);
        emptyStream = AsIntStream.of(emptyArr);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyAverage() {
        emptyStream.average();
    }

    @Test
    public void testAverage() {
        double expResult = 1;
        double result = intStream.average();
        assertEquals(expResult, result, 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyMin() {
        emptyStream.min();
    }

    @Test
    public void testMin() {
        int expResult = -1;
        int result = intStream.min();
        assertEquals(expResult, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyMax() {
        emptyStream.max();
    }

    @Test
    public void testMax() {
        int expResult = 3;
        int result = intStream.max();
        assertEquals(expResult, result);
    }

    @Test
    public void testEmptyCount() {
        long expResult = 0;
        long result = emptyStream.count();
        assertEquals(expResult, result);
    }

    @Test
    public void testCount(){
        int[] repArr = {1, 1, 2};
        IntStream repStream = AsIntStream.of(repArr);
        long expResult = 2;
        long result = repStream.count();
        assertEquals(expResult, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptySum() {
        emptyStream.sum();
    }

    @Test
    public void testSum(){
        int expResult = 5;
        int result = intStream.sum();
        assertEquals(expResult, result);
    }
}
