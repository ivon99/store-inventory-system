package model;

import org.junit.jupiter.api.Test;
import org.nbu.model.Checkout;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTest {

    // Test valid constructor
    @Test
    public void testValidConstructor() {
        Checkout checkout = new Checkout(1);
        assertEquals(1, checkout.getId());
    }

    // Test negative ID in constructor
    @Test
    public void testConstructorNegativeId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Checkout(-1);
        });
    }

    // Test zero ID in constructor
    @Test
    public void testConstructorZeroId() {
        Checkout checkout = new Checkout(0);
        assertEquals(0, checkout.getId());
    }

    // Test getter method
    @Test
    public void testGetId() {
        Checkout checkout = new Checkout(123);
        assertEquals(123, checkout.getId());
    }

    // Test toString method
    @Test
    public void testToString() {
        Checkout checkout = new Checkout(456);
        assertEquals("Checkout{id=456}", checkout.toString());
    }

    // Test equals method
    @Test
    public void testEquals() {
        Checkout checkout1 = new Checkout(1);
        Checkout checkout2 = new Checkout(2);
        Checkout checkout3 = new Checkout(1);

        // Same ID, should be equal
        assertEquals(checkout1, checkout3);

        // Different IDs, should not be equal
        assertNotEquals(checkout1, checkout2);
    }

    // Test hash code method
    @Test
    public void testHashCode() {
        Checkout checkout1 = new Checkout(1);
        Checkout checkout2 = new Checkout(2);
        Checkout checkout3 = new Checkout(1);

        // Same ID, should have same hash code
        assertEquals(checkout1.hashCode(), checkout3.hashCode());

        // Different IDs, hash codes should be different
        assertNotEquals(checkout1.hashCode(), checkout2.hashCode());
    }
}

