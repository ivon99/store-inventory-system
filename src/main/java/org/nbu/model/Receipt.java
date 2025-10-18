package org.nbu.model;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a receipt with information about purchased items,time of purchase, total amount, and attending cashier.
 */
public class Receipt {
    private long id;
    private long attendingCashierID;
    private LocalDateTime dateAndTime;
    private LinkedHashMap<Item, Integer> quantityItems;
    private double total;

    /**
     * Constructs a Receipt object with given ID and attending cashier ID.
     *
     * @param id                 The unique identifier of the receipt.
     * @param attendingCashierID The ID of the cashier who attended the transaction.
     */
    public Receipt(long id, long attendingCashierID) {
        if (id < 0) {
            throw new IllegalArgumentException("Receipt ID must be greater than or equal to 0");
        }
        if (attendingCashierID < 0) {
            throw new IllegalArgumentException("Attending Cashier ID must be greater than or equal to 0");
        }
        this.id = id;
        this.attendingCashierID = attendingCashierID;
        this.dateAndTime = LocalDateTime.now();
        this.quantityItems = new LinkedHashMap<>();
    }

    /**
     * Retrieves the ID of the receipt.
     *
     * @return The ID of the receipt.
     */
    public long getId() {
        return id;
    }

    /**
     * Retrieves the ID of the attending cashier.
     *
     * @return The ID of the attending cashier.
     */
    public long getAttendingCashierID() {
        return attendingCashierID;
    }

    /**
     * Retrieves the date and time when the receipt was created.
     *
     * @return The date and time of the receipt.
     */
    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    /**
     * Retrieves the map of items and their quantities in the receipt.
     *
     * @return The map of items and their quantities.
     */
    public LinkedHashMap<Item, Integer> getQuantityItems() {
        return quantityItems;
    }

    /**
     * Retrieves the total amount of the receipt.
     *
     * @return The total amount of the receipt.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Calculates the total amount of the receipt based on the items and their quantities.
     */
    public void calculateTotal() {
        double currTotal = 0;
        for (Map.Entry<Item, Integer> entry : this.quantityItems.entrySet()) {
            Item itemBought = entry.getKey();
            double pricePerItem = itemBought.getSellingPrice();
            Integer quantityItem = entry.getValue();
            currTotal += pricePerItem * quantityItem;
        }
        this.total = currTotal;
    }

    /**
     * Generates a string representation of the receipt.
     *
     * @return A string representation of the receipt.
     */
    @Override
    public String toString() {
        String items = "";
        for (Map.Entry<Item, Integer> entry : this.quantityItems.entrySet()) {
            Item itemBought = entry.getKey();
            Integer quantityItem = entry.getValue();
            String formatSellingPrice = String.format("%.2f", itemBought.getSellingPrice());
            items += itemBought.getName() + "....." + formatSellingPrice + "x" + quantityItem + "pcs.\n";
        }
        calculateTotal();
        String formatTotal = String.format("%.2f", this.total);
        return "Receipt #" + this.id + "\n" +
                "Cashier #" + this.attendingCashierID + "\n" +
                "Time: " + this.dateAndTime + "\n" +
                "Products:\n" + items +
                "Total: " + formatTotal+"\n"+
                "-----------------------------";
    }
}

