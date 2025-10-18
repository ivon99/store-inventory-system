package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nbu.model.Cashier;
import org.nbu.model.Checkout;
import org.nbu.model.CheckoutAssignment;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

public class CheckoutAssignmentTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testValidConstructor() {
        HashSet<Cashier> nonAssignedCashiers = new HashSet<>();
        nonAssignedCashiers.add(new Cashier("John Doe", 1, 2500.0));
        nonAssignedCashiers.add(new Cashier("Jane Smith", 2, 3000.0));

        HashSet<Checkout> emptyCheckouts = new HashSet<>();
        emptyCheckouts.add(new Checkout(1));
        emptyCheckouts.add(new Checkout(2));

        CheckoutAssignment checkoutAssignment = new CheckoutAssignment(nonAssignedCashiers, emptyCheckouts);

        assertEquals(nonAssignedCashiers.size(), checkoutAssignment.getNonAssignedCashiers().size());
        assertEquals(emptyCheckouts.size(), checkoutAssignment.getEmptyCheckouts().size());
    }

    @Test
    public void testConstructorNullNonAssignedCashiers() {
        HashSet<Checkout> emptyCheckouts = new HashSet<>();
        emptyCheckouts.add(new Checkout(1));
        emptyCheckouts.add(new Checkout(2));

        assertThrows(IllegalArgumentException.class, () -> {
            new CheckoutAssignment(null, emptyCheckouts);
        });
    }

    @Test
    public void testConstructorNullEmptyCheckouts() {
        HashSet<Cashier> nonAssignedCashiers = new HashSet<>();
        nonAssignedCashiers.add(new Cashier("John Doe", 1, 2500.0));
        nonAssignedCashiers.add(new Cashier("Jane Smith", 2, 3000.0));

        assertThrows(IllegalArgumentException.class, () -> {
            new CheckoutAssignment(nonAssignedCashiers, null);
        });
    }

    @Test
    public void testFreeCheckout() {
        HashSet<Cashier> nonAssignedCashiers = new HashSet<>();
        nonAssignedCashiers.add(new Cashier("John Doe", 1, 2500.0));
        nonAssignedCashiers.add(new Cashier("Jane Smith", 2, 3000.0));

        HashSet<Checkout> emptyCheckouts = new HashSet<>();
        emptyCheckouts.add(new Checkout(1));
        emptyCheckouts.add(new Checkout(2));

        CheckoutAssignment checkoutAssignment = new CheckoutAssignment(nonAssignedCashiers, emptyCheckouts);

        Checkout checkout = new Checkout(1);
        Cashier cashier = new Cashier("John Doe", 1, 2500.0);

        checkoutAssignment.assignCashierToCheckout(checkout, cashier);
        checkoutAssignment.freeCheckout(checkout);

        assertTrue(checkoutAssignment.getEmptyCheckouts().contains(checkout));
        assertTrue(checkoutAssignment.getNonAssignedCashiers().contains(cashier));
        assertTrue(checkoutAssignment.getCheckoutAssignments().isEmpty());
    }

    @Test
    public void testFreeCheckoutNull() {
        HashSet<Cashier> nonAssignedCashiers = new HashSet<>();
        nonAssignedCashiers.add(new Cashier("John Doe", 1, 2500.0));
        nonAssignedCashiers.add(new Cashier("Jane Smith", 2, 3000.0));

        HashSet<Checkout> emptyCheckouts = new HashSet<>();
        emptyCheckouts.add(new Checkout(1));
        emptyCheckouts.add(new Checkout(2));

        CheckoutAssignment checkoutAssignment = new CheckoutAssignment(nonAssignedCashiers, emptyCheckouts);

        assertThrows(IllegalArgumentException.class, () -> {
            checkoutAssignment.freeCheckout(null);
        });
    }

    @Test
    public void testFreeCheckoutNotAssigned() {
        HashSet<Cashier> nonAssignedCashiers = new HashSet<>();
        nonAssignedCashiers.add(new Cashier("John Doe", 1, 2500.0));
        nonAssignedCashiers.add(new Cashier("Jane Smith", 2, 3000.0));

        HashSet<Checkout> emptyCheckouts = new HashSet<>();
        emptyCheckouts.add(new Checkout(1));
        emptyCheckouts.add(new Checkout(2));

        CheckoutAssignment checkoutAssignment = new CheckoutAssignment(nonAssignedCashiers, emptyCheckouts);

        Checkout checkout = new Checkout(1);

        assertThrows(IllegalStateException.class, () -> {
            checkoutAssignment.freeCheckout(checkout);
        });
    }

    @Test
    public void testFreeCashier() {
        HashSet<Cashier> nonAssignedCashiers = new HashSet<>();
        nonAssignedCashiers.add(new Cashier("John Doe", 1, 2500.0));
        nonAssignedCashiers.add(new Cashier("Jane Smith", 2, 3000.0));

        HashSet<Checkout> emptyCheckouts = new HashSet<>();
        emptyCheckouts.add(new Checkout(1));
        emptyCheckouts.add(new Checkout(2));

        CheckoutAssignment checkoutAssignment = new CheckoutAssignment(nonAssignedCashiers, emptyCheckouts);

        Checkout checkout = new Checkout(1);
        Cashier cashier = new Cashier("John Doe", 1, 2500.0);

        checkoutAssignment.assignCashierToCheckout(checkout, cashier);
        checkoutAssignment.freeCashier(cashier);

        assertTrue(checkoutAssignment.getNonAssignedCashiers().contains(cashier));
        assertTrue(checkoutAssignment.getEmptyCheckouts().contains(checkout));
        assertTrue(checkoutAssignment.getCheckoutAssignments().isEmpty());
    }

    @Test
    public void testFreeCashierNull() {
        HashSet<Cashier> nonAssignedCashiers = new HashSet<>();
        nonAssignedCashiers.add(new Cashier("John Doe", 1, 2500.0));
        nonAssignedCashiers.add(new Cashier("Jane Smith", 2, 3000.0));

        HashSet<Checkout> emptyCheckouts = new HashSet<>();
        emptyCheckouts.add(new Checkout(1));
        emptyCheckouts.add(new Checkout(2));

        CheckoutAssignment checkoutAssignment = new CheckoutAssignment(nonAssignedCashiers, emptyCheckouts);

        assertThrows(IllegalArgumentException.class, () -> {
            checkoutAssignment.freeCashier(null);
        });
    }

    @Test
    public void testFreeCashierNotAssigned() {
        HashSet<Cashier> nonAssignedCashiers = new HashSet<>();
        nonAssignedCashiers.add(new Cashier("John Doe", 1, 2500.0));
        nonAssignedCashiers.add(new Cashier("Jane Smith", 2, 3000.0));

        HashSet<Checkout> emptyCheckouts = new HashSet<>();
        emptyCheckouts.add(new Checkout(1));
        emptyCheckouts.add(new Checkout(2));

        CheckoutAssignment checkoutAssignment = new CheckoutAssignment(nonAssignedCashiers, emptyCheckouts);

        Cashier cashier = new Cashier("John Doe", 1, 2500.0);

        assertThrows(IllegalStateException.class, () -> {
            checkoutAssignment.freeCashier(cashier);
        });
    }

    @Test
    public void testCheckIfCashierAssigned() {
        HashSet<Cashier> nonAssignedCashiers = new HashSet<>();
        nonAssignedCashiers.add(new Cashier("John Doe", 1, 2500.0));
        nonAssignedCashiers.add(new Cashier("Jane Smith", 2, 3000.0));

        HashSet<Checkout> emptyCheckouts = new HashSet<>();
        emptyCheckouts.add(new Checkout(1));
        emptyCheckouts.add(new Checkout(2));

        CheckoutAssignment checkoutAssignment = new CheckoutAssignment(nonAssignedCashiers, emptyCheckouts);

        Cashier cashier = new Cashier("John Doe", 1, 2500.0);

        assertFalse(checkoutAssignment.checkIfCashierAssigned(cashier));

        Checkout checkout = new Checkout(1);
        checkoutAssignment.assignCashierToCheckout(checkout, cashier);

        assertTrue(checkoutAssignment.checkIfCashierAssigned(cashier));
    }

    @Test
    public void testCheckIfCashierAssignedNull() {
        HashSet<Cashier> nonAssignedCashiers = new HashSet<>();
        nonAssignedCashiers.add(new Cashier("John Doe", 1, 2500.0));
        nonAssignedCashiers.add(new Cashier("Jane Smith", 2, 3000.0));

        HashSet<Checkout> emptyCheckouts = new HashSet<>();
        emptyCheckouts.add(new Checkout(1));
        emptyCheckouts.add(new Checkout(2));

        CheckoutAssignment checkoutAssignment = new CheckoutAssignment(nonAssignedCashiers, emptyCheckouts);

        assertThrows(IllegalArgumentException.class, () -> {
            checkoutAssignment.checkIfCashierAssigned(null);
        });
    }

    @Test
    public void testAssignCashierToCheckout() {
        HashSet<Cashier> nonAssignedCashiers = new HashSet<>();
        nonAssignedCashiers.add(new Cashier("John Doe", 1, 2500.0));
        nonAssignedCashiers.add(new Cashier("Jane Smith", 2, 3000.0));

        HashSet<Checkout> emptyCheckouts = new HashSet<>();
        emptyCheckouts.add(new Checkout(1));
        emptyCheckouts.add(new Checkout(2));

        CheckoutAssignment checkoutAssignment = new CheckoutAssignment(nonAssignedCashiers, emptyCheckouts);

        Checkout checkout = new Checkout(1);
        Cashier cashier = new Cashier("John Doe", 1, 2500.0);

        checkoutAssignment.assignCashierToCheckout(checkout, cashier);
        assertFalse(checkoutAssignment.getNonAssignedCashiers().contains(cashier));
        assertFalse(checkoutAssignment.getEmptyCheckouts().contains(checkout));
        assertEquals(1, checkoutAssignment.getCheckoutAssignments().size());
    }

    @Test
    public void testAssignCashierToCheckoutNullCheckout() {
        HashSet<Cashier> nonAssignedCashiers = new HashSet<>();
        nonAssignedCashiers.add(new Cashier("John Doe", 1, 2500.0));
        nonAssignedCashiers.add(new Cashier("Jane Smith", 2, 3000.0));

        HashSet<Checkout> emptyCheckouts = new HashSet<>();
        emptyCheckouts.add(new Checkout(1));
        emptyCheckouts.add(new Checkout(2));

        CheckoutAssignment checkoutAssignment = new CheckoutAssignment(nonAssignedCashiers, emptyCheckouts);

        Cashier cashier = new Cashier("John Doe", 1, 2500.0);
        checkoutAssignment.assignCashierToCheckout(null, cashier);
        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Error: Checkout and Cashier cannot be null"));
    }

    @Test
    public void testAssignCashierToCheckoutNullCashier() {
        HashSet<Cashier> nonAssignedCashiers = new HashSet<>();
        nonAssignedCashiers.add(new Cashier("John Doe", 1, 2500.0));
        nonAssignedCashiers.add(new Cashier("Jane Smith", 2, 3000.0));

        HashSet<Checkout> emptyCheckouts = new HashSet<>();
        emptyCheckouts.add(new Checkout(1));
        emptyCheckouts.add(new Checkout(2));

        CheckoutAssignment checkoutAssignment = new CheckoutAssignment(nonAssignedCashiers, emptyCheckouts);

        Checkout checkout = new Checkout(1);
        checkoutAssignment.assignCashierToCheckout(checkout, null);
        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Error: Checkout and Cashier cannot be null"));
    }

    @Test
    public void testAssignCashierToCheckoutAlreadyAssignedCashier() {
        HashSet<Cashier> nonAssignedCashiers = new HashSet<>();
        nonAssignedCashiers.add(new Cashier("John Doe", 1, 2500.0));
        nonAssignedCashiers.add(new Cashier("Jane Smith", 2, 3000.0));

        HashSet<Checkout> emptyCheckouts = new HashSet<>();
        emptyCheckouts.add(new Checkout(1));
        emptyCheckouts.add(new Checkout(2));

        CheckoutAssignment checkoutAssignment = new CheckoutAssignment(nonAssignedCashiers, emptyCheckouts);

        Checkout checkout1 = new Checkout(1);
        Checkout checkout2 = new Checkout(2);
        Cashier cashier = new Cashier("John Doe", 1, 2500.0);

        checkoutAssignment.assignCashierToCheckout(checkout1, cashier);
        checkoutAssignment.assignCashierToCheckout(checkout2, cashier);
        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Cashier " + cashier.getName() + " already assigned. Please unassign them before proceeding."));
    }

    @Test
    public void testAssignCashierToCheckoutAlreadyAssignedCheckout() {
        HashSet<Cashier> nonAssignedCashiers = new HashSet<>();
        nonAssignedCashiers.add(new Cashier("John Doe", 1, 2500.0));
        nonAssignedCashiers.add(new Cashier("Jane Smith", 2, 3000.0));

        HashSet<Checkout> emptyCheckouts = new HashSet<>();
        emptyCheckouts.add(new Checkout(1));
        emptyCheckouts.add(new Checkout(2));

        CheckoutAssignment checkoutAssignment = new CheckoutAssignment(nonAssignedCashiers, emptyCheckouts);

        Checkout checkout = new Checkout(1);
        Cashier cashier1 = new Cashier("John Doe", 1, 2500.0);
        Cashier cashier2 = new Cashier("Jane Smith", 2, 3000.0);

        checkoutAssignment.assignCashierToCheckout(checkout, cashier1);
        checkoutAssignment.assignCashierToCheckout(checkout, cashier2);
        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Checkout #" + checkout.getId() + " already has assigned cashier. Please free checkout before proceeding."));
    }
}

