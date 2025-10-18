package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nbu.model.Item;
import org.nbu.model.enums.ItemType;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    private Item validItem;
    private LocalDate futureDate;
    private LocalDate currentDate;

    @BeforeEach
    void setUp() {
        futureDate = LocalDate.now().plusDays(10);
        currentDate = LocalDate.now();
        validItem = new Item(1, "Valid Item", 10.0, ItemType.FOOD, futureDate);
    }

    @Test
    void testConstructorValidArguments() {
        assertNotNull(validItem);
        assertEquals(1, validItem.getId());
        assertEquals("Valid Item", validItem.getName());
        assertEquals(10.0, validItem.getDeliveryPrice());
        assertEquals(ItemType.FOOD, validItem.getItemType());
        assertEquals(futureDate, validItem.getExpirationDate());
    }

    @Test
    void testConstructorInvalidId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Item(0, "Item", 10.0, ItemType.FOOD, futureDate);
        });
        assertEquals("ID for item Item must be greater than zero", exception.getMessage());
    }

    @Test
    void testConstructorNullName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Item(1, null, 10.0, ItemType.FOOD, futureDate);
        });
        assertEquals("Name for item#1 cannot be null or empty", exception.getMessage());
    }

    @Test
    void testConstructorEmptyName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Item(1, "  ", 10.0, ItemType.FOOD, futureDate);
        });
        assertEquals("Name for item#1 cannot be null or empty", exception.getMessage());
    }

    @Test
    void testConstructorNegativeDeliveryPrice() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Item(1, "Item", -10.0, ItemType.FOOD, futureDate);
        });
        assertEquals("Delivery price for itemItem cannot be negative", exception.getMessage());
    }

    @Test
    void testConstructorNullItemType() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Item(1, "Item", 10.0, null, futureDate);
        });
        assertEquals("ItemItem type cannot be null", exception.getMessage());
    }

    @Test
    void testConstructorPastExpirationDate() {
        LocalDate pastDate = LocalDate.now().minusDays(1);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Item(1, "Item", 10.0, ItemType.FOOD, pastDate);
        });
        assertEquals("Expiration date must be in the future for item Item", exception.getMessage());
    }

    @Test
    void testConstructorNullExpirationDate() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Item(1, "Item", 10.0, ItemType.FOOD, null);
        });
        assertEquals("Expiration date must be in the future for item Item", exception.getMessage());
    }

    @Test
    void testSetPriceValidArguments() {
        validItem.setPrice(0.2, 5, 0.1, currentDate);
        double expectedPrice = 10.0 + 0.2 * 10.0;
        assertEquals(expectedPrice, validItem.getSellingPrice());
    }

    @Test
    void testSetPriceWithExpirationDiscount() {
        validItem.setPrice(0.2, 15, 0.1, currentDate);
        double markupPrice = 10.0 + 0.2 * 10.0;
        double expectedPrice = markupPrice - markupPrice * 0.1;
        assertEquals(expectedPrice, validItem.getSellingPrice());
    }

    @Test
    void testSetPriceNullCurrentDate() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validItem.setPrice(0.2, 5, 0.1, null);
        });
        assertEquals("Current date cannot be null", exception.getMessage());
    }

    @Test
    void testSetPriceInvalidDiscountExpiration() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validItem.setPrice(0.2, 5, 1.1, currentDate);
        });
        assertEquals("Discount expiration should be less than 1.1", exception.getMessage());
    }

    @Test
    void testSetPriceNegativeMarkup() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validItem.setPrice(-0.1, 5, 0.1, currentDate);
        });
        assertEquals("Markup should not be negative", exception.getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        Item item1 = new Item(1, "Item1", 10.0, ItemType.FOOD, futureDate);
        Item item2 = new Item(1, "Item1", 10.0, ItemType.FOOD, futureDate);
        Item item3 = new Item(2, "Item2", 20.0, ItemType.NONFOOD, futureDate.plusDays(10));

        assertEquals(item1, item2);
        assertNotEquals(item1, item3);
        assertEquals(item1.hashCode(), item2.hashCode());
        assertNotEquals(item1.hashCode(), item3.hashCode());
    }
}

