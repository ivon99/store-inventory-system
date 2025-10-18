package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nbu.model.Store;

import static org.junit.jupiter.api.Assertions.*;

public class StoreTest {

    private Store store;

    @BeforeEach
    public void setUp() {
        store = new Store(10.0, 5.0, 15.0, 30);
    }

    @Test
    public void testConstructorValidParameters() {
        assertEquals(10.0, store.getMarkupFoodItem());
        assertEquals(5.0, store.getMarkupNonfoodItem());
        assertEquals(15.0, store.getDiscountExpiringItems());
        assertEquals(30, store.getDaysUntilExpirationDiscount());
    }

    @Test
    public void testConstructorNegativeMarkup() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Store(-10.0, 5.0, 15.0, 30);
        });
    }

    @Test
    public void testConstructorNegativeDiscount() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Store(10.0, 5.0, -15.0, 30);
        });
    }

    @Test
    public void testGettersAndSetters() {
        // Test getter methods
        assertEquals(0.0, store.getWagesExpense());
        assertEquals(0.0, store.getDeliveryExpense());
        assertEquals(0.0, store.getRevenueItems());

        // Test setter methods
        store.setWagesExpense(1000.0);
        store.setDeliveryExpense(500.0);
        store.setRevenueItems(5000.0);

        assertEquals(1000.0, store.getWagesExpense());
        assertEquals(500.0, store.getDeliveryExpense());
        assertEquals(5000.0, store.getRevenueItems());
    }

}
