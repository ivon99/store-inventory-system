package org.nbu.model;

import java.util.Objects;

/**
 * Represents a cashier with a name, ID, and salary.
 * Ensures that the name is non-null and non-empty, and that the ID and salary are non-negative.
 */
public class Cashier {
    private String name;
    private long id;
    private double salary;

    /**
     * Constructs a new Cashier with the specified name, ID, and salary.
     *
     * @param name   the name of the cashier, must be non-null and non-empty
     * @param id     the ID of the cashier, must be non-negative
     * @param salary the salary of the cashier, must be non-negative
     * @throws IllegalArgumentException if the name is null or empty, or if the ID or salary is negative
     */

    public Cashier(String name, long id, double salary) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty for cashier");
        }
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative for cashier " + name + "!");
        }
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative for cashier " + name + "!");
        }
        this.name = name;
        this.id = id;
        this.salary = salary;
    }


    /**
     * Returns the name of the cashier.
     *
     * @return the name of the cashier
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the ID of the cashier.
     *
     * @return the ID of the cashier
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the salary of the cashier.
     *
     * @return the salary of the cashier
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Returns a string representation of the cashier.
     *
     * @return a string representation of the cashier
     */
    @Override
    public String toString() {
        return "Cashier{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", salary=" + salary +
                '}';
    }

    /**
     * Compares this cashier to the specified object for equality.
     * Only equal if having the same ID
     *
     * @param obj the object to compare with
     * @return true if the specified object is equal to this cashier
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Cashier cashier = (Cashier) obj;
        return id == cashier.id;
    }

    /**
     * Returns a hash code value for the cashier.
     * Only considers the ID of the cashier.
     *
     * @return a hash code value for the cashier
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
