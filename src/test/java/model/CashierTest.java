package model;

import org.junit.jupiter.api.Test;
import org.nbu.model.Cashier;

import static org.junit.jupiter.api.Assertions.*;

public class CashierTest {

    // Test valid construction
    @Test
    public void testValidConstructor() {
        Cashier cashier = new Cashier("John Doe", 1, 2500.0);
        assertEquals("John Doe", cashier.getName());
        assertEquals(1, cashier.getId());
        assertEquals(2500.0, cashier.getSalary(), 0.01);
    }

    // Test null name in constructor
    @Test
    public void testConstructorNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cashier(null, 1, 2500.0);
        });
    }

    // Test empty name in constructor
    @Test
    public void testConstructorEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cashier("", 1, 2500.0);
        });
    }

    // Test negative ID in constructor
    @Test
    public void testConstructorNegativeId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cashier("John Doe", -1, 2500.0);
        });
    }

    // Test negative salary in constructor
    @Test
    public void testConstructorNegativeSalary() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cashier("John Doe", 1, -2500.0);
        });
    }

    // Test equals method
    @Test
    public void testEquals() {
        Cashier cashier1 = new Cashier("John Doe", 1, 2500.0);
        Cashier cashier2 = new Cashier("Jane Smith", 2, 3000.0);

        // Same ID, should be equal
        Cashier cashier3 = new Cashier("Alex Brown", 1, 2800.0);
        assertEquals(cashier1, cashier3);

        // Different IDs, should not be equal
        assertNotEquals(cashier1, cashier2);
    }

    // Test hash code method
    @Test
    public void testHashCode() {
        Cashier cashier1 = new Cashier("John Doe", 1, 2500.0);
        Cashier cashier2 = new Cashier("Jane Smith", 2, 3000.0);

        // Same ID, should have same hash code
        Cashier cashier3 = new Cashier("Alex Brown", 1, 2800.0);
        assertEquals(cashier1.hashCode(), cashier3.hashCode());

        // Different IDs, hash codes should be different
        assertNotEquals(cashier1.hashCode(), cashier2.hashCode());
    }

    // Test toString method
    @Test
    public void testToString() {
        Cashier cashier = new Cashier("John Doe", 1, 2500.0);
        assertEquals("Cashier{name='John Doe', id=1, salary=2500.0}", cashier.toString());
    }
}

