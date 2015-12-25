package com.test.junit;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yuwt on 2015/12/24.
 */
public class LargestTest {

    @Test
    public void testSimple() {
        assertEquals(9, Largest.largest(new int[]{7, 8, 9}));
    }

    @Test
    public void testOrder() throws Exception {
        assertEquals(9, Largest.largest(new int[]{7, 8, 9}));
        assertEquals(9, Largest.largest(new int[]{7, 8, 9,6}));
        assertEquals(9, Largest.largest(new int[]{9, 7, 8, 5}));
    }

    @Test
    public void testDups() {
        assertEquals(9, Largest.largest(new int[]{9, 7, 8, 9}));
    }

    @Test
    public void testOne() {
        assertEquals(9, Largest.largest(new int[]{9}));
    }

    @Test
    public void testNegative() {
        assertEquals(-7, Largest.largest(new int[]{-9, -8, -7}));
    }

    @Test
    public void testEmpty() {
        try {
            Largest.largest(new int[]{});
            fail("Should have thrown an exception");
        } catch (RuntimeException e) {
            assertTrue(true);
        }
    }
}