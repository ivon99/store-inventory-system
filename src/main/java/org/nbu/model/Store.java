package org.nbu.model;

import java.util.*;

/**
 * Represents a store with information about markups, discounts, items, cashiers, and financial data.
 */
public class Store {
    private double markupNonfoodItem;
    private double markupFoodItem;
    private double discountExpiringItems;
    private int daysUntilExpirationDiscount;
    private ArrayList<Receipt> receipts;
    private HashSet<Cashier> cashiers;
    private HashMap<Item, Integer> deliveredItems;
    private HashMap<Item, Integer> soldItems;
    private HashMap<Item, Integer> inventory;
    private HashSet<Checkout> checkouts;
    private CheckoutAssignment checkoutAssignments;
    private double wagesExpense;
    private double deliveryExpense;
    private double revenueItems;

    /**
     * Constructs a Store object with specified markups, discounts, cashier, checkouts, profits and more.
     *
     * @param markupFoodItem            The markup percentage for food items.
     * @param markupNonfoodItem         The markup percentage for non-food items.
     * @param discountExpiringItems     The discount percentage for expiring items.
     * @param daysUntilExpirationDiscount Number of days until expiration when expiration discount will be applied.
     * @throws IllegalArgumentException If any markup or discount value is negative.
     */
    public Store(double markupFoodItem, double markupNonfoodItem, double discountExpiringItems, int daysUntilExpirationDiscount) {
        if (markupFoodItem < 0 || markupNonfoodItem < 0 || discountExpiringItems < 0) {
            throw new IllegalArgumentException("Markup and discount values must be non-negative");
        }
        this.markupFoodItem = markupFoodItem;
        this.markupNonfoodItem = markupNonfoodItem;
        this.discountExpiringItems = discountExpiringItems;
        this.daysUntilExpirationDiscount = daysUntilExpirationDiscount;
        this.receipts = new ArrayList<>();
        this.cashiers = new HashSet<>();
        this.deliveredItems = new HashMap<>();
        this.soldItems = new HashMap<>();
        this.inventory = new HashMap<>();
        this.checkouts = new HashSet<>();
        this.checkoutAssignments = new CheckoutAssignment(this.cashiers, this.checkouts);
    }

    /**
     * Retrieves the set of checkouts in the store.
     *
     * @return The set of checkouts.
     */
    public HashSet<Checkout> getCheckouts() {
        return this.checkouts;
    }

    /**
     * Retrieves the markup percentage for non-food items.
     *
     * @return The markup percentage for non-food items.
     */
    public double getMarkupNonfoodItem() {
        return markupNonfoodItem;
    }

    /**
     * Retrieves the markup percentage for food items.
     *
     * @return The markup percentage for food items.
     */
    public double getMarkupFoodItem() {
        return markupFoodItem;
    }

    /**
     * Retrieves the discount percentage for expiring items.
     *
     * @return The discount percentage for expiring items.
     */
    public double getDiscountExpiringItems() {
        return discountExpiringItems;
    }

    /**
     * Retrieves the threshold in days until expiration for applying discounts.
     *
     * @return The threshold in days until expiration for applying discounts.
     */
    public int getDaysUntilExpirationDiscount() {
        return daysUntilExpirationDiscount;
    }

    /**
     * Retrieves the map of delivered items and their quantities.
     *
     * @return The map of delivered items and their quantities.
     */
    public HashMap<Item, Integer> getDeliveredItems() {
        return deliveredItems;
    }

    /**
     * Retrieves the map of items in inventory and their quantities.
     *
     * @return The map of items in inventory and their quantities.
     */
    public HashMap<Item, Integer> getInventory() {
        return inventory;
    }

    /**
     * Retrieves the map of sold items and their quantities.
     *
     * @return The map of sold items and their quantities.
     */
    public HashMap<Item, Integer> getSoldItems() {
        return soldItems;
    }

    /**
     * Retrieves the list of receipts issued by the store.
     *
     * @return The list of receipts issued by the store.
     */
    public List<Receipt> getReceipts() {
        return receipts;
    }

    /**
     * Retrieves the set of cashiers employed by the store.
     *
     * @return The set of cashiers employed by the store.
     */
    public HashSet<Cashier> getCashiers() {
        return cashiers;
    }

    /**
     * Retrieves the total wages expense of the store.
     *
     * @return The total wages expense of the store.
     */
    public double getWagesExpense() {
        return wagesExpense;
    }

    /**
     * Retrieves the total revenue from items sold (excluding expenses).
     *
     * @return The total revenue from items sold (excluding expenses).
     */
    public double getRevenueItems() {
        return revenueItems;
    }

    /**
     * Retrieves the assignment of checkouts to cashiers.
     *
     * @return The assignment of checkouts to cashiers.
     */
    public CheckoutAssignment getCheckoutAssignments() {
        return checkoutAssignments;
    }

    /**
     * Retrieves the total delivery expenses of the store.
     *
     * @return The total delivery expenses of the store.
     */
    public double getDeliveryExpense() {
        return deliveryExpense;
    }

    /**
     * Sets the total revenue from items sold (excluding expenses).
     *
     * @param revenueItems The total revenue from items sold (excluding expenses).
     */
    public void setRevenueItems(double revenueItems) {
        this.revenueItems = revenueItems;
    }

    /**
     * Sets the total wages expense of the store.
     *
     * @param wagesExpense The total wages expense of the store.
     */
    public void setWagesExpense(double wagesExpense) {
        this.wagesExpense = wagesExpense;
    }

    /**
     * Sets the total delivery expenses of the store.
     *
     * @param deliveryExpense The total delivery expenses of the store.
     */
    public void setDeliveryExpense(double deliveryExpense) {
        this.deliveryExpense = deliveryExpense;
    }

    @Override
    public String toString() {
        return "Store{" +
                "markupNonfoodItem=" + markupNonfoodItem +
                ", markupFoodItem=" + markupFoodItem +
                ", discountExpiringItems=" + discountExpiringItems +
                ", daysUntilExpirationDiscount=" + daysUntilExpirationDiscount +
                ", receipts=" + receipts +
                ", cashiers=" + cashiers +
                "\n deliveredItems=" + deliveredItems +
                ", soldItems=" + soldItems +
                "\n inventory=" + inventory +
                ", checkouts=" + checkouts +
                ", checkoutAssignments=" + checkoutAssignments +
                ", wagesExpense=" + wagesExpense +
                ", deliveryExpense=" + deliveryExpense +
                ", revenueItems=" + revenueItems +
                '}';
    }
}

