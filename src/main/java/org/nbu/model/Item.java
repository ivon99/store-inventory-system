package org.nbu.model;

import org.nbu.model.enums.ItemType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Represents an item with attributes such as ID, name, delivery price, item type, expiration date, and selling price.
 */
public class Item {
    private long id;
    private String name;
    private double deliveryPrice;
    private ItemType itemType;
    private LocalDate expirationDate;
    private double sellingPrice;

    /**
     * Constructs an Item object with specified attributes.
     *
     * @param id              The unique identifier for the item.
     * @param name            The name of the item.
     * @param deliveryPrice   The price of item upon delivery without store markup.
     * @param itemType        The type of the item (food, nonfood).
     * @param expirationDate  The date when the item expires and should not be sold.
     * @throws IllegalArgumentException If any of the parameters are invalid (negative ID or delivery price,
     *                                  null or empty name, null itemType, or expiration date in the past).
     */
    public Item(long id, String name, double deliveryPrice, ItemType itemType, LocalDate expirationDate){
        if (id <= 0) {
            throw new IllegalArgumentException("ID for item "+name+ " must be greater than zero");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name for item#" + id+" cannot be null or empty");
        }
        if (deliveryPrice < 0) {
            throw new IllegalArgumentException("Delivery price for item" +name+ " cannot be negative");
        }
        if (itemType == null) {
            throw new IllegalArgumentException("Item" +name+ " type cannot be null");
        }
        if (expirationDate == null || expirationDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expiration date must be in the future for item "+name);
        }

        this.id = id;
        this.name = name;
        this.deliveryPrice = deliveryPrice;
        this.itemType = itemType;
        this.expirationDate = expirationDate;
    }

    /**
     * Returns the id of the item.
     *
     * @return The id of the item.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the name of the item.
     *
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the item when delivered of the item.
     *
     * @return The price of the item when delivered.
     */
    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    /**
     * Returns the selling price of the item.
     *
     * @return The selling price of the item.
     */
    public double getSellingPrice() {
        return sellingPrice;
    }

    /**
     * Returns the type of the item.
     *
     * @return The type of the item.
     */
    public ItemType getItemType() {
        return itemType;
    }

    /**
     * Returns the expiration date of the item.
     *
     * @return The expiration date of the item.
     */
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the selling price of the item based on markup, days until expiration, discount for early expiration,
     * and current date.
     *
     * @param markup                 The percentage markup to apply to the delivery price.
     * @param customDaysUntilExpiration  Number of days until expiration when expiration discount will be applied.
     * @param discountExpiration     The discount to apply if expiration is sooner than expected.
     * @param currentDate            The current date to compare against expiration.
     * @throws IllegalArgumentException If currentDate is null,discountExpiration is not less than 1.1 or markup is negative.
     */
    public void setPrice(double markup, long customDaysUntilExpiration, double discountExpiration, LocalDate currentDate) {
        if (currentDate == null) {
            throw new IllegalArgumentException("Current date cannot be null");
        }
        if (discountExpiration >= 1.1) {
            throw new IllegalArgumentException("Discount expiration should be less than 1.1");
        }
        if(markup < 0){
            throw new IllegalArgumentException("Markup should not be negative");
        }

        double markupPrice = this.deliveryPrice + markup * this.deliveryPrice;
        long daysUntilExpiration = ChronoUnit.DAYS.between(currentDate, this.expirationDate);

        if (daysUntilExpiration < customDaysUntilExpiration) {
            this.sellingPrice = markupPrice - markupPrice * discountExpiration;
        } else {
            this.sellingPrice = markupPrice;
        }
    }

    /**
     * Returns a string representation of the Item object.
     *
     * @return A string representation of the Item object.
     */
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deliveryPrice=" + deliveryPrice +
                ", itemType=" + itemType +
                ", expirationDate=" + expirationDate +
                ", sellingPrice=" + sellingPrice +
                '}';
    }

    /**
     * Indicates whether some other object is "equal to" this one based on ID.
     *
     * @param obj The reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Item item = (Item) obj;
        return id == item.id;
    }

    /**
     * Returns a hash code value for the Item object based on ID.
     *
     * @return A hash code value for this Item object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

