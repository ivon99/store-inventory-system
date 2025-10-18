package org.nbu.model;

import java.util.Objects;

public class Checkout {
    private long id;


    /**
     * Constructs a Checkout object with the specified ID.
     *
     * @param id the unique identifier for the checkout session
     * @throws IllegalArgumentException if the provided ID is negative
     */
    public Checkout(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID of checkout cannot be negative!");
        }
        this.id = id;
    }

    /**
     * Retrieves the ID of this checkout session.
     *
     * @return the unique identifier of the checkout session
     */
    public long getId() {
        return id;
    }

    /**
     * Returns a string representation of the checkout.
     *
     * @return a string representation of the checkout
     */
    @Override
    public String toString() {
        return "Checkout{id=" + id + "}";
    }

    /**
     * Compares this checkout to the specified object for equality.
     * Only equal if having the same ID
     *
     * @param obj the object to compare with
     * @return true if the specified object is equal to this checkout
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Checkout checkout = (Checkout) obj;
        return id == checkout.id;
    }

    /**
     * Returns a hash code value for the checkout.
     * Only considers the ID of the checkout.
     *
     * @return a hash code value for the checkout
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
