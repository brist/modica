package org.afpparser.afp.modca.triplets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public abstract class AbstractTripletTest<T> {
    private T x;
    private T y;
    private T z;
    private T notEqual;

    public abstract void setUp();

    public void setXYZ(T x, T y, T z, T notEqual) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.notEqual = notEqual;
    }

    @Test
    public void testEqualsHashCode() {
        // Consistency
        for (int i = 0; i < 100; i++) {
            // Reflexivity
            assertTrue(x.equals(x));
            assertTrue(y.equals(y));
            assertTrue(z.equals(z));

            // Symmetry
            assertTrue(x.equals(y));
            assertTrue(y.equals(x));
            assertFalse(x.equals(notEqual));
            assertFalse(notEqual.equals(x));

            // Transitivity
            assertTrue(x.equals(y));
            assertTrue(y.equals(z));
            assertTrue(x.equals(z));

            // Non-nullity
            assertFalse(x.equals(null));
            assertFalse(y.equals(null));
            assertFalse(z.equals(null));
        }
        assertEquals(x.hashCode(), y.hashCode());
        assertEquals(y.hashCode(), z.hashCode());
        assertNotSame(x.hashCode(), notEqual.hashCode());
    }
}