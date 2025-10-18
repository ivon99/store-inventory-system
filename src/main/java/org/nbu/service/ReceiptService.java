package org.nbu.service;

import org.nbu.model.Item;
import org.nbu.model.Receipt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Service class for handling operations related to Receipts.
 */
public class ReceiptService {

    /**
     * Prints the receipt details to the terminal.
     *
     * @param receipt The receipt to be printed.
     */
    public void printReceiptToTerminal(Receipt receipt) {
        System.out.println(receipt.toString());
    }

    /**
     * Saves the receipt contents to a file.
     *
     * @param receipt The receipt to be saved.
     */
    public void saveReceiptToFile(Receipt receipt) {
        String fileName = "receipt_" + receipt.getId() + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write receipt contents to the file
            writer.write(receipt.toString());
        } catch (IOException e) {
            System.out.println("ERROR while writing receipt to file.");
        }
    }

    /**
     * Adds an item with a specified quantity to the receipt.
     *
     * @param receipt  The receipt to add the item to.
     * @param item     The item to be added.
     * @param quantity The quantity of the item to be added.
     * @throws IllegalArgumentException If item or receipt is null, or quantity is not positive.
     */
    public void addItemToReceipt(Receipt receipt, Item item, Integer quantity) {
        if (receipt == null || item == null) {
            throw new IllegalArgumentException("Receipt and item cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive number.");
        }

        // Update quantity of items in the receipt
        LinkedHashMap<Item, Integer> quantityItems = receipt.getQuantityItems();
        if (quantityItems.containsKey(item)) {
            int oldQuantity = quantityItems.get(item);
            quantityItems.put(item, oldQuantity + quantity);
        } else {
            quantityItems.put(item, quantity);
        }
    }

    /**
     * Creates a new receipt with the given ID and attending cashier ID.
     *
     * @param id                 The ID of the receipt.
     * @param attendingCashierID The ID of the cashier attending the transaction.
     * @return The created Receipt object.
     */
    public Receipt createReceipt(long id, long attendingCashierID) {
        return new Receipt(id, attendingCashierID);
    }
}

