package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.nbu.model.Item;
import org.nbu.model.Receipt;
import org.nbu.model.enums.ItemType;
import org.nbu.service.ReceiptService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReceiptServiceTest {

    private ReceiptService receiptService = new ReceiptService();

    @Test
    void testPrintReceiptToTerminal() {
        Receipt receipt = new Receipt(1, 101);
        receiptService.printReceiptToTerminal(receipt);

        // Redirect System.out for testing
        String output = captureOutput(() -> receiptService.printReceiptToTerminal(receipt));

        Assertions.assertTrue(output.contains("Receipt #1"));
        Assertions.assertTrue(output.contains("Cashier #101"));
        Assertions.assertTrue(output.contains("Total: 0.00"));

    }

    @Test
    void testSaveReceiptToFile() throws IOException {
        Receipt receipt = new Receipt(2, 102);
        String expectedContent = receipt.toString();

        receiptService.saveReceiptToFile(receipt);

        try (BufferedReader reader = new BufferedReader(new FileReader("receipt_2.txt"))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            content.setLength(content.length()-1);
            Assertions.assertEquals(expectedContent, content.toString());
        } finally {
            // Clean up: delete the created file
            boolean deleted = new java.io.File("receipt_2.txt").delete();
            Assertions.assertTrue(deleted);
        }
    }

    @Test
    void testAddItemToReceipt() {
        Receipt receipt = new Receipt(3, 103);
        Item item = new Item(1,"shampoo",22.0, ItemType.NONFOOD, LocalDateTime.now().plusDays(10).toLocalDate());
        receiptService.addItemToReceipt(receipt, item, 5);

        Assertions.assertEquals(1, receipt.getQuantityItems().size());
        Assertions.assertTrue(receipt.getQuantityItems().containsKey(item));
        Assertions.assertEquals(5, receipt.getQuantityItems().get(item));
    }

    @Test
    void testAddItemToReceiptInvalidArguments() {
        Receipt receipt = new Receipt(4, 104);
        Item item = new Item(1,"shampoo",22.0, ItemType.NONFOOD, LocalDateTime.now().plusDays(10).toLocalDate());

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> receiptService.addItemToReceipt(null, item, 3));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> receiptService.addItemToReceipt(receipt, null, 3));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> receiptService.addItemToReceipt(receipt, item, 0));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> receiptService.addItemToReceipt(receipt, item, -1));
    }

    @Test
    void testCreateReceipt() {
        long id = 5;
        long cashierId = 105;
        Receipt receipt = receiptService.createReceipt(id, cashierId);

        Assertions.assertNotNull(receipt);
        Assertions.assertEquals(id, receipt.getId());
        Assertions.assertEquals(cashierId, receipt.getAttendingCashierID());
    }

    private String captureOutput(Runnable action) {
        // Redirect System.out for testing
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
        action.run();
        System.setOut(System.out); // Reset System.out
        return outputStream.toString();
    }
}

