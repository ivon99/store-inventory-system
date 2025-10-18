package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nbu.model.Customer;
import org.nbu.model.Item;
import org.nbu.model.enums.ItemType;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private Customer customer;
    private Item item1;
    private Item item2;

    @BeforeEach
    void setUp() {
        item1 = new Item(1, "Item1", 10.0, ItemType.FOOD, LocalDate.now().plusDays(10));
        item2 = new Item(2, "Item2", 20.0, ItemType.NONFOOD, LocalDate.now().plusDays(20));
        customer = new Customer(100.0); // Starting with $100
    }

    @Test
    void testConstructorValidMoney() {
        Customer customer = new Customer(50.0);
        assertEquals(50.0, customer.getMoney());
    }

    @Test
    void testConstructorNegativeMoney() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Customer(-10.0);
        });
        assertEquals("Initial money cannot be negative", exception.getMessage());
    }

    @Test
    void testGetMoney() {
        assertEquals(100.0, customer.getMoney());
    }

    @Test
    void testGetBasketInitiallyEmpty() {
        assertTrue(customer.getBasket().isEmpty());
    }

    @Test
    void testAddToBasketValidItemAndQuantity() {
        customer.addToBasket(item1, 5);
        Map<Item, Integer> basket = customer.getBasket();
        assertEquals(1, basket.size());
        assertEquals(5, basket.get(item1).intValue());
    }

    @Test
    void testAddToBasketNullItem() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customer.addToBasket(null, 5);
        });
        assertEquals("Item cannot be null", exception.getMessage());
    }

    @Test
    void testAddToBasketNullQuantity() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customer.addToBasket(item1, null);
        });
        assertEquals("Quantity must be a positive integer", exception.getMessage());
    }

    @Test
    void testAddToBasketNegativeQuantity() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customer.addToBasket(item1, -1);
        });
        assertEquals("Quantity must be a positive integer", exception.getMessage());
    }

    @Test
    void testAddToBasketZeroQuantity() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customer.addToBasket(item1, 0);
        });
        assertEquals("Quantity must be a positive integer", exception.getMessage());
    }

    @Test
    void testAddToBasketMergesQuantity() {
        customer.addToBasket(item1, 3);
        customer.addToBasket(item1, 2);
        Map<Item, Integer> basket = customer.getBasket();
        assertEquals(1, basket.size());
        assertEquals(5, basket.get(item1).intValue());
    }

    @Test
    void testAddMultipleItemsToBasket() {
        customer.addToBasket(item1, 2);
        customer.addToBasket(item2, 3);
        Map<Item, Integer> basket = customer.getBasket();
        assertEquals(2, basket.size());
        assertEquals(2, basket.get(item1).intValue());
        assertEquals(3, basket.get(item2).intValue());
    }

    @Test
    void testGetBasketReturnsCopy() {
        customer.addToBasket(item1, 1);
        Map<Item, Integer> basket = customer.getBasket();
        basket.put(item2, 1); // Modify the copy
        assertFalse(customer.getBasket().containsKey(item2)); // Original basket should not be affected
    }
}




