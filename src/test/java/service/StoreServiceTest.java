package service;

import org.junit.jupiter.api.Test;
import org.nbu.model.*;
import org.nbu.model.enums.ItemType;
import org.nbu.service.StoreService;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

class StoreServiceTest {

    @Test
    void testAddItemToInventory() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Item foodItem = new Item(1, "bread", 1.00, ItemType.FOOD, LocalDate.now().plusDays(5));

        StoreService.addItemToInventory(store, foodItem, 10);
        assertTrue(store.getInventory().containsKey(foodItem));
        assertEquals(10, store.getInventory().get(foodItem));
    }

    @Test
    void testSellItemSuccess() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Item foodItem = new Item(1, "bread", 1.00, ItemType.FOOD, LocalDate.now().plusDays(5));

        StoreService.addItemToInventory(store, foodItem, 10);
        boolean result = StoreService.sellItem(store, foodItem, 5);
        assertTrue(result);
        assertEquals(5, store.getInventory().get(foodItem));
        assertEquals(5, store.getSoldItems().get(foodItem));
    }

    @Test
    void testSellItemInsufficientQuantity() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Item foodItem = new Item(1, "bread", 1.00, ItemType.FOOD, LocalDate.now().plusDays(5));

        StoreService.addItemToInventory(store, foodItem, 2);
        boolean result = StoreService.sellItem(store, foodItem, 5);
        assertFalse(result);
        assertEquals(2, store.getInventory().get(foodItem));
    }


    @Test
    void testAddItemBackToStore() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Item foodItem = new Item(1, "bread", 1.00, ItemType.FOOD, LocalDate.now().plusDays(5));

        StoreService.addItemToInventory(store, foodItem, 10);
        StoreService.sellItem(store, foodItem, 5);
        StoreService.addItemBackToStore(store, foodItem, 2);
        assertEquals(7, store.getInventory().get(foodItem));
        assertEquals(3, store.getSoldItems().get(foodItem));
    }

    @Test
    void testSellingWithReceipt() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Item foodItem = new Item(1, "bread", 1.00, ItemType.FOOD, LocalDate.now().plusDays(5));
        Cashier cashier = new Cashier("John Doe", 1, 3000.0);
        Customer customer = new Customer(50.0);
        Checkout checkout1 = new Checkout(1);

        StoreService.addACheckout(store, checkout1);
        StoreService.assignCashierToCheckout(store, cashier, checkout1);

        StoreService.addItemToInventory(store, foodItem, 10);
        customer.getBasket().put(foodItem, 2);
        Receipt receipt = StoreService.sellingWithReceipt(store, 1, cashier, customer);
        assertNotNull(receipt);
        assertEquals(1, store.getReceipts().size());
    }

    @Test
    void testSellingWithReceiptInsufficientFunds() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Item foodItem = new Item(1, "bread", 1.00, ItemType.FOOD, LocalDate.now().plusDays(5));
        Cashier cashier = new Cashier("John Doe", 1, 3000.0);
        Customer customer = new Customer(5.0); // Insufficient funds
        Checkout checkout1 = new Checkout(1);

        StoreService.hireCashier(store,cashier);
        StoreService.addACheckout(store, checkout1);
        StoreService.assignCashierToCheckout(store, cashier, checkout1);
        StoreService.addItemToInventory(store, foodItem, 10);

        customer.addToBasket(foodItem,10);
        Receipt receipt = StoreService.sellingWithReceipt(store, 1, cashier, customer);
        assertNull(receipt);
        assertEquals(10, store.getInventory().get(foodItem));
    }

    @Test
    void testHireCashier() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Cashier cashier = new Cashier("John Doe", 1, 3000.0);

        StoreService.hireCashier(store, cashier);
        assertTrue(store.getCashiers().contains(cashier));
        assertEquals(3000.0, store.getWagesExpense());
    }

    @Test
    void testAddReceiptToReceiptInventory() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Receipt receipt = new Receipt(1, 1);

        StoreService.addReceiptToReceiptInventory(store, receipt);
        assertTrue(store.getReceipts().contains(receipt));
    }

    @Test
    void testShowHiredCashiers() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Cashier cashier = new Cashier("John Doe", 1, 3000.0);

        StoreService.hireCashier(store, cashier);
        assertDoesNotThrow(() -> StoreService.showHiredCashiers(store));
    }

    @Test
    void testShowSalaryExpense() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Cashier cashier = new Cashier("John Doe", 1, 3000.0);

        StoreService.hireCashier(store, cashier);
        assertDoesNotThrow(() -> StoreService.showSalaryExpense(store));
    }

    @Test
    void testShowRevenueFromSoldItems() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Item foodItem = new Item(1, "bread", 1.00, ItemType.FOOD, LocalDate.now().plusDays(5));

        StoreService.addItemToInventory(store, foodItem, 10);
        StoreService.sellItem(store, foodItem, 5);
        assertDoesNotThrow(() -> StoreService.showRevenueFromSoldItems(store));
    }

    @Test
    void testShowProfit() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Item foodItem = new Item(1, "bread", 1.00, ItemType.FOOD, LocalDate.now().plusDays(5));
        Cashier cashier = new Cashier("John Doe", 1, 3000.0);

        StoreService.addItemToInventory(store, foodItem, 10);
        StoreService.sellItem(store, foodItem, 5);
        StoreService.hireCashier(store, cashier);
        assertDoesNotThrow(() -> StoreService.showProfit(store));
    }

    @Test
    void testAddACheckout() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Checkout checkout = new Checkout(1);

        StoreService.addACheckout(store, checkout);
        assertTrue(store.getCheckouts().contains(checkout));
    }

    @Test
    void testFreeCheckout() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Checkout checkout = new Checkout(10);
        Cashier cashier = new Cashier("John Doe", 1, 3000.0);


        StoreService.addACheckout(store, checkout);
        StoreService.hireCashier(store,cashier);
        StoreService.assignCashierToCheckout(store,cashier,checkout);
        StoreService.freeCheckout(store, checkout);
        assertFalse(store.getCheckoutAssignments().checkIfCheckoutAssigned(checkout));
    }

    @Test
    void testUnassignCashier() {
        Store store1 = new Store(0.1, 0.2, 0.5, 5);
        Checkout checkout = new Checkout(1);
        Cashier cashier = new Cashier("Jane Doe", 2, 3000.0);

        StoreService.addACheckout(store1, checkout);
        StoreService.hireCashier(store1, cashier);
        StoreService.assignCashierToCheckout(store1, cashier, checkout);
        StoreService.unassignCashier(store1, cashier);
        assertFalse(store1.getCheckoutAssignments().checkIfCashierAssigned(cashier));
    }

    @Test
    void testShowFreeCashiers() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Cashier cashier = new Cashier("John Doe", 1, 3000.0);

        StoreService.hireCashier(store, cashier);
        assertDoesNotThrow(() -> StoreService.showFreeCashiers(store));
    }

    @Test
    void testShowFreeCheckouts() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Checkout checkout = new Checkout(1);

        StoreService.addACheckout(store, checkout);
        assertDoesNotThrow(() -> StoreService.showFreeCheckouts(store));
    }

    @Test
    void testAssignCashierToCheckout() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Checkout checkout = new Checkout(1);
        Cashier cashier = new Cashier("John Doe", 1, 3000.0);

        StoreService.addACheckout(store, checkout);
        StoreService.hireCashier(store, cashier);
        StoreService.assignCashierToCheckout(store, cashier, checkout);
        assertTrue(store.getCheckoutAssignments().checkIfCashierAssigned(cashier));
    }

    @Test
    void testPrintCheckoutAssignment() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Checkout checkout = new Checkout(1);
        Cashier cashier = new Cashier("John Doe", 1, 3000.0);

        StoreService.addACheckout(store, checkout);
        StoreService.hireCashier(store, cashier);
        StoreService.assignCashierToCheckout(store, cashier, checkout);
        assertDoesNotThrow(() -> StoreService.printCheckoutAssignment(store));
    }

    @Test
    void testShowHowManyReceipts() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Receipt receipt = new Receipt(1, 1);

        StoreService.addReceiptToReceiptInventory(store, receipt);
        assertEquals(1, StoreService.showHowManyReceipts(store));
    }

    @Test
    void testShowDeliveredItems() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Item foodItem = new Item(1, "bread", 1.00, ItemType.FOOD, LocalDate.now().plusDays(5));

        StoreService.addItemToInventory(store, foodItem, 10);
        assertDoesNotThrow(() -> StoreService.showDeliveredItems(store));
    }

    @Test
    void testShowSoldItems() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Item foodItem = new Item(1, "bread", 1.00, ItemType.FOOD, LocalDate.now().plusDays(5));

        StoreService.addItemToInventory(store, foodItem, 10);
        StoreService.sellItem(store, foodItem, 5);
        assertDoesNotThrow(() -> StoreService.showSoldItems(store));
    }

    @Test
    void testShowDeliveryExpense() {
        Store store = new Store(0.1, 0.2, 0.5, 5);
        Item foodItem = new Item(1, "bread", 1.00, ItemType.FOOD, LocalDate.now().plusDays(5));

        StoreService.addItemToInventory(store, foodItem, 10);
        assertDoesNotThrow(() -> StoreService.showDeliveryExpense(store));
    }
}
