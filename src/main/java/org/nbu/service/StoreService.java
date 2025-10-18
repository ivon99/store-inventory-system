package org.nbu.service;

import org.nbu.exceptions.InsufficientQuantityException;
import org.nbu.model.*;
import org.nbu.model.enums.ItemType;

import java.time.LocalDate;
import java.util.*;

/**
 * Service class for handling store operations.
 */
public class StoreService {

    public StoreService() {
    }

    /**
     * Updates the quantity of an item in a given HashMap.
     *
     * @param hashmap the map to update
     * @param item    the item to update
     * @param quantity the quantity to add
     */
    private static void updateHashMap(HashMap<Item, Integer> hashmap, Item item, int quantity) {
        if (hashmap.containsKey(item)) {
            int currentQuantity = hashmap.get(item);
            hashmap.put(item, currentQuantity + quantity);
        } else {
            hashmap.put(item, quantity);
        }
    }

    /**
     * Removes the quantity of an item from a given HashMap.
     *
     * @param hashmap the map to update
     * @param item    the item to update
     * @param quantity the quantity to remove
     */
    private static void removeHashMap(HashMap<Item, Integer> hashmap, Item item, int quantity) {
        if (hashmap.containsKey(item)) {
            int currentQuantity = hashmap.get(item);
            hashmap.put(item, currentQuantity - quantity);
        } else {
            throw new IllegalArgumentException("Item " + item.getName() + " not found in hashmap.");
        }
    }

    /**
     * Adds an item to the store's inventory.
     *
     * @param store    the store
     * @param item     the item to add
     * @param quantity the quantity to add
     */
    public static void addItemToInventory(Store store, Item item, int quantity) {
        LocalDate currentDate = LocalDate.now();
        if (currentDate.isAfter(item.getExpirationDate())) {
            System.out.println("The item " + item.getName() + " is expired and will not be added to inventory.");
            return;
        }

        HashMap<Item, Integer> deliveredItems = store.getDeliveredItems();
        double markup = (item.getItemType() == ItemType.FOOD) ? store.getMarkupFoodItem() : store.getMarkupNonfoodItem();
        item.setPrice(markup, store.getDaysUntilExpirationDiscount(), store.getDiscountExpiringItems(), currentDate);
        updateHashMap(deliveredItems, item, quantity);
        HashMap<Item, Integer> inventory = store.getInventory();
        updateHashMap(inventory, item, quantity);
        store.setDeliveryExpense(store.getDeliveryExpense() + (item.getDeliveryPrice() * quantity));
    }

    public static void showInventory(Store store){
        System.out.println("Inventory is: " + store.getInventory());
    }

    /**
     * Sells an item from the store.
     *
     * @param store    the store
     * @param item     the item to sell
     * @param quantity the quantity to sell
     * @return true if the item was sold, false otherwise
     */
    public static boolean sellItem(Store store, Item item, int quantity) {
        try {
            if (!store.getInventory().containsKey(item)) {
                System.out.println("Product " + item.getName() + " not found in store!");
                return false;
            }

            LocalDate currentDate = LocalDate.now();

            if (currentDate.isAfter(item.getExpirationDate())) {
                System.out.println("The item is expired! It can't be sold!");
                store.getInventory().remove(item);
                return false;
            }

            int availableQuantity = store.getInventory().get(item);

            if (availableQuantity >= quantity) {
                store.getInventory().put(item, availableQuantity - quantity);
                updateHashMap(store.getSoldItems(), item, quantity);
                store.setRevenueItems(store.getRevenueItems() + (item.getSellingPrice() * quantity));
                return true;
            } else {
                int missingQuantity = quantity - availableQuantity;
                throw new InsufficientQuantityException("Insufficient quantity of " + item.getName() + " in inventory. " + missingQuantity + " pieces missing from desired quantity.");
            }
        } catch (InsufficientQuantityException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Adds an item back to the store's inventory.
     *
     * @param store    the store
     * @param item     the item to add
     * @param quantity the quantity to add
     */
    public static void addItemBackToStore(Store store, Item item, int quantity) {
        updateHashMap(store.getInventory(), item, quantity);
        store.setRevenueItems(store.getRevenueItems() - (item.getSellingPrice() * quantity));
        removeHashMap(store.getSoldItems(), item, quantity);
    }

    /**
     * Handles the selling process with a receipt.
     *
     * @param store       the store
     * @param receiptId   the receipt ID
     * @param cashier     the cashier handling the transaction
     * @param customer    the customer making the purchase
     * @return the receipt if the sale is successful, null otherwise
     */
    public static Receipt sellingWithReceipt(Store store, long receiptId, Cashier cashier, Customer customer) {
        if (!store.getCheckoutAssignments().checkIfCashierAssigned(cashier)) {
            System.out.println("The provided cashier is not assigned to any checkout!");
            return null;
        }

        ReceiptService receiptService = new ReceiptService();
        Receipt receipt = receiptService.createReceipt(receiptId, cashier.getId());
        for (Map.Entry<Item, Integer> entry : customer.getBasket().entrySet()) {
            if (sellItem(store, entry.getKey(), entry.getValue())) {
                receiptService.addItemToReceipt(receipt, entry.getKey(), entry.getValue());
            } else {
                System.out.println("Item not added to receipt.");
            }
        }
        System.out.println(receipt);
        receipt.calculateTotal();

        if (customer.getMoney() < receipt.getTotal()) {
            System.out.println("Impossible payment! Items will be removed back to store!");

            for (Map.Entry<Item, Integer> entry : customer.getBasket().entrySet()) {
                addItemBackToStore(store, entry.getKey(), entry.getValue());
            }

            return null;
        } else {
            receipt.calculateTotal();
            String formatTotal = String.format("%.2f", receipt.getTotal());
            System.out.println("Successful payment of " + formatTotal);
            store.getReceipts().add(receipt);
            receiptService.saveReceiptToFile(receipt);
            customer.setMoney(customer.getMoney()-receipt.getTotal());
        }

        return receipt;
    }

    /**
     * Adds a receipt to the store's receipt inventory.
     *
     * @param store   the store
     * @param receipt the receipt to add
     */
    public static void addReceiptToReceiptInventory(Store store, Receipt receipt) {
        store.getReceipts().add(receipt);
    }

    /**
     * Hires a cashier for the store.
     *
     * @param store   the store
     * @param cashier the cashier to hire
     */
    public static void hireCashier(Store store, Cashier cashier) {
        store.getCashiers().add(cashier);
        store.getCheckoutAssignments().addNewCashier(cashier);
        store.setWagesExpense(store.getWagesExpense() + cashier.getSalary());
    }

    /**
     * Shows the hired cashiers in the store.
     *
     * @param store the store
     */
    public static void showHiredCashiers(Store store) {
        if (!store.getCashiers().isEmpty()) {
            System.out.print("Hired cashiers are: ");
            System.out.println(store.getCashiers());
        } else {
            System.out.println("No hired cashiers!");
        }
    }

    /**
     * Shows the salary expense of the store.
     *
     * @param store the store
     */
    public static void showSalaryExpense(Store store) {
        String formatSalaryExpense = String.format("%.2f", store.getWagesExpense());
        System.out.println("Salary expenses are: " + formatSalaryExpense);
    }

    /**
     * Shows the revenue from sold items in the store.
     *
     * @param store the store
     */
    public static void showRevenueFromSoldItems(Store store) {
        String formatRevenue = String.format("%.2f", store.getRevenueItems());
        System.out.println("Items revenue is: " + formatRevenue);
    }

    /**
     * Shows the profit of the store.
     *
     * @param store the store
     */
    public static void showProfit(Store store) {
        double profit = store.getRevenueItems() - (store.getWagesExpense() + store.getDeliveryExpense());
        String formatProfit = String.format("%.2f", profit);
        System.out.println("Store profit is: "+ formatProfit);
    }

    /**
     * Adds a checkout to the store.
     *
     * @param store    the store
     * @param checkout the checkout to add
     */
    public static void addACheckout(Store store, Checkout checkout) {
        store.getCheckouts().add(checkout);
        store.getCheckoutAssignments().addNewCheckout(checkout);
    }

    /**
     * Frees a checkout in the store.
     *
     * @param store    the store
     * @param checkout the checkout to free
     */
    public static void freeCheckout(Store store, Checkout checkout) {
        store.getCheckoutAssignments().freeCheckout(checkout);
    }

    /**
     * Unassigns a cashier from a checkout in the store.
     *
     * @param store   the store
     * @param cashier the cashier to unassign
     */
    public static void unassignCashier(Store store, Cashier cashier) {
        store.getCheckoutAssignments().freeCashier(cashier);
    }

    /**
     * Shows the free cashiers in the store.
     *
     * @param store the store
     */
    public static void showFreeCashiers(Store store) {
        store.getCheckoutAssignments().printFreeCashiers();
    }

    /**
     * Shows the free checkouts in the store.
     *
     * @param store the store
     */
    public static void showFreeCheckouts(Store store) {
        store.getCheckoutAssignments().printFreeCheckouts();
    }

    /**
     * Assigns a cashier to a checkout in the store.
     *
     * @param store    the store
     * @param cashier  the cashier to assign
     * @param checkout the checkout to assign the cashier to
     */
    public static void assignCashierToCheckout(Store store, Cashier cashier, Checkout checkout) {
        store.getCheckoutAssignments().assignCashierToCheckout(checkout, cashier);
    }

    /**
     * Prints the checkout assignments in the store.
     *
     * @param store    the store
     */
    public static void printCheckoutAssignment(Store store) {
        store.getCheckoutAssignments().printCheckoutAssignments();
    }

    /**
     * Shows the number of receipts in the store.
     *
     * @param store the store
     * @return the number of receipts
     */
    public static long showHowManyReceipts(Store store) {
        long numOfReceipts = store.getReceipts().size();
        System.out.println("Store has " + numOfReceipts + " number of receipts.");
        return numOfReceipts;
    }

    /**
     * Shows the delivered items in the store.
     *
     * @param store the store
     */
    public static void showDeliveredItems(Store store) {
        System.out.println("Delivered items are " + store.getDeliveredItems());
    }

    /**
     * Shows the sold items in the store.
     *
     * @param store the store
     */
    public static void showSoldItems(Store store) {
        System.out.print("Sold items are: ");
        System.out.println(store.getSoldItems());
    }

    /**
     * Shows the delivery expense of the store.
     *
     * @param store the store
     */
    public static void showDeliveryExpense(Store store) {
        System.out.println("Delivery expenses are: "+ store.getDeliveryExpense());
    }
}

