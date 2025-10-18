package org.nbu.model;

import java.util.HashMap;
import java.util.Map;

public class Customer {
    private double money;
    private HashMap<Item, Integer> basket;

    /**
     * Constructs a Customer object with the specified initial amount of money.
     *
     * @param money the initial amount of money the customer possesses
     * @throws IllegalArgumentException if the initial money is negative
     */
    public Customer(double money) {
        if (money < 0) {
            throw new IllegalArgumentException("Initial money cannot be negative");
        }
        this.money = money;
        this.basket = new HashMap<>();
    }


    /**
     * Retrieves the amount of money the customer currently possesses.
     *
     * @return the current amount of money the customer has
     */
    public double getMoney() {
        return money;
    }

    /**
     * Retrieves a copy of the customer's shopping basket.
     *
     * @return a copy of the items and quantities in the customer's basket
     */
    public Map<Item, Integer> getBasket() {
        return new HashMap<>(basket);
    }


    /**
     * Sets budget of customer.
     *
     */
    public void setMoney(double money) {
        this.money = money;
    }

    /**
     * Adds a specified quantity of an item to the customer's shopping basket.
     *
     * @param item the item to add to the basket
     * @param quantity the quantity of the item to add (must be positive)
     * @throws IllegalArgumentException if the item is null or quantity is not positive
     */
    public void addToBasket(Item item, Integer quantity) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive integer");
        }
        basket.merge(item, quantity, Integer::sum); // Merges quantity if item already exists
    }
}



