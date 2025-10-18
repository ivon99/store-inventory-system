package org.nbu.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CheckoutAssignment {
    private HashMap<Checkout, Cashier> checkoutAssignments;
    private HashSet<Cashier> nonAssignedCashiers;
    private HashSet<Checkout> emptyCheckouts;


    /**
     * Constructs a CheckoutAssignment object with initial non-assigned cashiers and empty checkouts.
     *
     * @param nonAssignedCashiers a set of cashiers not currently assigned to any checkout
     * @param emptyCheckouts a set of checkouts that are currently unoccupied
     * @throws IllegalArgumentException if either nonAssignedCashiers or emptyCheckouts is null
     */
    public CheckoutAssignment(HashSet<Cashier> nonAssignedCashiers, HashSet<Checkout> emptyCheckouts) {
        if (nonAssignedCashiers == null || emptyCheckouts == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        this.checkoutAssignments = new HashMap<>();
        this.nonAssignedCashiers = new HashSet<>(nonAssignedCashiers);
        this.emptyCheckouts = new HashSet<>(emptyCheckouts);
    }

    /**
     * Frees the specified checkout by removing its assignment to a cashier.
     *
     * @param checkout the checkout to be freed
     * @throws IllegalArgumentException if checkout is null
     * @throws IllegalStateException if the checkout is not currently assigned to any cashier
     */
    public void freeCheckout(Checkout checkout) {
        if (checkout == null) {
            throw new IllegalArgumentException("Checkout cannot be null");
        }
        if (this.checkoutAssignments.containsKey(checkout)) {
            Cashier cashier = checkoutAssignments.remove(checkout);
            nonAssignedCashiers.add(cashier);
            emptyCheckouts.add(checkout);
        } else {
            throw new IllegalStateException("Checkout is not assigned");
        }
    }

    /**
     * Retrieves the current checkout assignments in the store.
     * Each entry in the map represents a mapping between a checkout and its assigned cashier.
     *
     * @return a HashMap containing the checkout assignments, where the key is a Checkout and the value is a Cashier.
     */
    public HashMap<Checkout, Cashier> getCheckoutAssignments() {
        return checkoutAssignments;
    }

    /**
     * Retrieves the set of cashiers who are not assigned to any checkout.
     *
     * @return a HashSet containing the non-assigned cashiers.
     */
    public HashSet<Cashier> getNonAssignedCashiers() {
        return nonAssignedCashiers;
    }

    /**
     * Retrieves the set of checkouts that are currently empty, i.e., not assigned to any cashier.
     *
     * @return a HashSet containing the empty checkouts.
     */
    public HashSet<Checkout> getEmptyCheckouts() {
        return emptyCheckouts;
    }

    /**
     * Frees the specified cashier by removing their assignment from any checkout.
     *
     * @param cashier the cashier to be freed
     * @throws IllegalArgumentException if cashier is null
     * @throws IllegalStateException if the cashier is not assigned to any checkout
     */
    public void freeCashier(Cashier cashier) {
        if (cashier == null) {
            throw new IllegalArgumentException("Cashier cannot be null");
        }
        for (Map.Entry<Checkout, Cashier> entry : this.checkoutAssignments.entrySet()) {
            if (entry.getValue().equals(cashier)) {
                Checkout checkout = entry.getKey();
                this.checkoutAssignments.remove(checkout);
                nonAssignedCashiers.add(cashier);
                emptyCheckouts.add(checkout);
                return;
            }
        }
        throw new IllegalStateException("Cashier is not assigned to any checkout");
    }

    /**
     * Prints all non-assigned cashiers.
     */
    public void printFreeCashiers() {
        if(this.nonAssignedCashiers.isEmpty()){
            System.out.println("No unassigned cashiers!");
        }else{
            System.out.print("Cashiers who are not assigned are: ");
            for (Cashier cashier : this.nonAssignedCashiers) {
                System.out.println(cashier);
            }
        }
    }


    /**
     * Prints all empty checkouts.
     */
    public void printFreeCheckouts() {
        for (Checkout checkout : this.emptyCheckouts) {
            System.out.println(checkout);
        }
    }


    /**
     * Checks if the specified cashier is currently assigned to any checkout.
     *
     * @param cashier the cashier to check assignment status
     * @return true if the cashier is assigned to a checkout, false otherwise
     * @throws IllegalArgumentException if cashier is null
     */
    public boolean checkIfCashierAssigned(Cashier cashier) {
        if (cashier == null) {
            throw new IllegalArgumentException("Cashier cannot be null");
        }
        return !nonAssignedCashiers.contains(cashier);
    }


    /**
     * Checks if the specified checkout is currently occupied by a cashier.
     *
     * @param checkout the checkout to check assignment status
     * @return true if the checkout is assigned, false otherwise
     * @throws IllegalArgumentException if checkout is null
     */
    public boolean checkIfCheckoutAssigned(Checkout checkout) {
        if (checkout == null) {
            throw new IllegalArgumentException("Cashier cannot be null");
        }
        return !this.emptyCheckouts.contains(checkout);
    }

    /**
     * Assigns the specified cashier to the specified checkout, if both are available.
     *
     * @param checkout the checkout to assign
     * @param cashier the cashier to assign
     * @throws IllegalArgumentException if checkout or cashier is null
     * @throws IllegalStateException if the cashier is already assigned to a checkout or if the checkout already has an assigned cashier
     */
    public void assignCashierToCheckout(Checkout checkout, Cashier cashier) {
        try {
            if (checkout == null || cashier == null) {
                throw new IllegalArgumentException("Checkout and Cashier cannot be null");
            }
            if (nonAssignedCashiers.contains(cashier) && emptyCheckouts.contains(checkout)) {
                checkoutAssignments.put(checkout, cashier);
                nonAssignedCashiers.remove(cashier);
                emptyCheckouts.remove(checkout);
            } else if (!nonAssignedCashiers.contains(cashier)) {
                throw new IllegalStateException("Cashier " + cashier.getName() + " already assigned. Please unassign them before proceeding.");
            } else if (!emptyCheckouts.contains(checkout)) {
                throw new IllegalStateException("Checkout #" + checkout.getId() + " already has assigned cashier. Please free checkout before proceeding.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Adds a new cashier to the set of non-assigned cashiers.
     *
     * @param cashier the Cashier to be added to the non-assigned cashiers set.
     */
    public void addNewCashier(Cashier cashier){
        this.nonAssignedCashiers.add(cashier);
    }

    /**
     * Adds a new checkout to the set of empty checkouts.
     *
     * @param checkout the Checkout to be added to the empty checkouts set.
     */
    public void addNewCheckout(Checkout checkout){
        this.emptyCheckouts.add(checkout);
    }

    /**
     * Prints all current checkout assignments showing which cashier is assigned to which checkout.
     */
    public void printCheckoutAssignments() {
        if (this.checkoutAssignments.isEmpty()) {
            System.out.println("No assigned cashier-checkout pairs!");
        } else{
            for (Map.Entry<Checkout, Cashier> entry : this.checkoutAssignments.entrySet()) {
                Checkout checkout = entry.getKey();
                Cashier cashier = entry.getValue();
                System.out.println("Checkout # " + checkout.getId() + " -> cashier " + cashier.getName() + " #" + cashier.getId());
            }
        }
    }
}
