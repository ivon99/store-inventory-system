package model;

import org.junit.jupiter.api.Test;
import org.nbu.model.Item;
import org.nbu.model.Receipt;
import org.nbu.model.enums.ItemType;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptTest {

    @Test
    void testConstructorValidArguments() {
        Receipt receipt = new Receipt(1, 1001);
        assertEquals(1, receipt.getId());
        assertEquals(1001, receipt.getAttendingCashierID());
        assertNotNull(receipt.getDateAndTime());
        assertTrue(receipt.getQuantityItems().isEmpty());
        assertEquals(0.0, receipt.getTotal());
    }

    @Test
    void testConstructorInvalidId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Receipt(-1, 1001);
        });
        assertEquals("Receipt ID must be greater than or equal to 0", exception.getMessage());
    }

    @Test
    void testConstructorInvalidAttendingCashierId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Receipt(1, -1001);
        });
        assertEquals("Attending Cashier ID must be greater than or equal to 0", exception.getMessage());
    }

    @Test
    void testAddItemToReceipt() {
        Receipt receipt = new Receipt(1, 1001);
        Item item1 = new Item(1, "Item1", 10.0, ItemType.FOOD, LocalDateTime.now().plusDays(10).toLocalDate());
        receipt.getQuantityItems().put(item1, 2);
        assertEquals(1, receipt.getQuantityItems().size());
        assertTrue(receipt.getQuantityItems().containsKey(item1));
        assertEquals(2, receipt.getQuantityItems().get(item1));
    }

    @Test
    void testAddMultipleItemsToReceipt() {
        Receipt receipt = new Receipt(1, 1001);
        Item item1 = new Item(1, "Item1", 10.0, ItemType.FOOD, LocalDateTime.now().plusDays(10).toLocalDate());
        Item item2 = new Item(2, "Item2", 20.0, ItemType.NONFOOD, LocalDateTime.now().plusDays(20).toLocalDate());

        receipt.getQuantityItems().put(item1, 2);
        receipt.getQuantityItems().put(item2, 1);
        assertEquals(2, receipt.getQuantityItems().size());
        assertTrue(receipt.getQuantityItems().containsKey(item1));
        assertTrue(receipt.getQuantityItems().containsKey(item2));
        assertEquals(2, receipt.getQuantityItems().get(item1));
        assertEquals(1, receipt.getQuantityItems().get(item2));
    }


    @Test
    void testCalculateTotalWithNoItems() {
        Receipt receipt = new Receipt(1, 1001);
        receipt.calculateTotal();
        assertEquals(0.0, receipt.getTotal());
    }

   @Test
    void testCalculateTotalWithItems() {
        Receipt receipt = new Receipt(1, 1001);
        Item item1 = new Item(1, "Item1", 10.0, ItemType.FOOD, LocalDateTime.now().plusDays(10).toLocalDate());
        Item item2 = new Item(2, "Item2", 20.0, ItemType.NONFOOD, LocalDateTime.now().plusDays(20).toLocalDate());
        item1.setPrice(0.1,2,0.5, LocalDate.now());
        item2.setPrice(0.1,2,0.5, LocalDate.now());
        receipt.getQuantityItems().put(item1, 2);
        receipt.getQuantityItems().put(item2, 1);
        receipt.calculateTotal();
        assertEquals(44.0, receipt.getTotal());
    }


    @Test
    void testToStringWithNoItems() {
        Receipt receipt = new Receipt(1, 1001);
        String expected = "Receipt #1\n" +
                "Cashier #1001\n" +
                "Time: " + receipt.getDateAndTime() + "\n" +
                "Products:\n" +
                "Total: 0.00\n"+"-----------------------------";
        assertEquals(expected, receipt.toString());
    }

    @Test
    void testToStringWithItems() {
        Receipt receipt = new Receipt(1, 1001);
        Item item1 = new Item(1, "Item1", 10.0, ItemType.FOOD, LocalDateTime.now().plusDays(10).toLocalDate());
        Item item2 = new Item(2, "Item2", 20.0, ItemType.NONFOOD, LocalDateTime.now().plusDays(20).toLocalDate());
        item1.setPrice(0.1,2,0.5, LocalDate.now());
        item2.setPrice(0.1,2,0.5, LocalDate.now());
        receipt.getQuantityItems().put(item1, 3);
        receipt.getQuantityItems().put(item2, 1);


        String expected = "Receipt #1\n" +
                "Cashier #1001\n" +
                "Time: " + receipt.getDateAndTime() + "\n" +
                "Products:\n" +
                "Item1.....11.00x3pcs.\n" +
                "Item2.....22.00x1pcs.\n" +
                "Total: 55.00\n"+"-----------------------------";
        assertEquals(expected, receipt.toString());
    }
}


