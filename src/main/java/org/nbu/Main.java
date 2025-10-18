package org.nbu;

import org.nbu.model.*;
import org.nbu.model.enums.ItemType;
import org.nbu.service.StoreService;

import java.time.LocalDate;


public class Main {
    public static void main(String[] args){
        System.out.println("Welcome to Store Service Management program!");

        //Creating stores
        Store fantastiko = new Store(0.2,0.5, 0.7,2 );
        Store lidl = new Store(0.4,0.8, 0.7,3);

        //Creating items
        Item apple = new Item(2,"apple",5,ItemType.FOOD,LocalDate.of(2024,07,1));
        Item tomato = new Item(6, "tomato",3,ItemType.FOOD,LocalDate.of(2024,11,8));
        Item chips = new Item(213,"Chio chips",4,ItemType.FOOD,LocalDate.of(2025,10,3));
        Item lighter = new Item(567,"lighter",7,ItemType.NONFOOD,LocalDate.of(2030,11,6));

        //Delivering items to stores
        StoreService.addItemToInventory(fantastiko,apple,3);
        StoreService.addItemToInventory(fantastiko,chips,10);
        StoreService.addItemToInventory(fantastiko,lighter,5);
        StoreService.addItemToInventory(lidl,tomato,16);

        //Showing delivered items of stores
        StoreService.showDeliveredItems(fantastiko);
        StoreService.showDeliveredItems(lidl);

        //Hiring cashiers
        Cashier Ludmila = new Cashier("Ludmila", 1, 2000);
        Cashier Mary = new Cashier("Mary", 2, 400);
        Cashier Georgi = new Cashier("Georgi",3,3500);

        StoreService.hireCashier(fantastiko,Ludmila);
        StoreService.hireCashier(fantastiko, Mary);
        StoreService.hireCashier(lidl,Georgi);

        //Creating checkouts
        Checkout checkout1 = new Checkout(1);
        Checkout checkout2= new Checkout(2);
        Checkout checkout3= new Checkout(3);

        StoreService.addACheckout(fantastiko,checkout1);
        StoreService.addACheckout(fantastiko,checkout2);
        StoreService.addACheckout(lidl, checkout3);

        //Assigning cashiers to checkouts
        StoreService.assignCashierToCheckout(fantastiko,Mary,checkout1);
        StoreService.assignCashierToCheckout(fantastiko,Ludmila, checkout2);
        StoreService.printCheckoutAssignment(fantastiko);
        StoreService.assignCashierToCheckout(lidl,Georgi,checkout3 );

        //Customers buying items with receipts
        Customer cust1 = new Customer(100);
        Customer cust2 = new Customer(26.70);

        cust1.addToBasket(apple,2);
        cust1.addToBasket(chips,10);
        cust1.addToBasket(lighter,2);
        StoreService.sellingWithReceipt(fantastiko,123,Mary,cust1); //saves receipt to file with same name

        cust2.addToBasket(tomato,2);
        StoreService.sellingWithReceipt(lidl,32,Georgi,cust2);

        //Store management functions
        StoreService.showRevenueFromSoldItems(fantastiko);
        StoreService.showHowManyReceipts(fantastiko);
        StoreService.showHiredCashiers(fantastiko);
        StoreService.showDeliveredItems(fantastiko);
        StoreService.showSoldItems(fantastiko);
        StoreService.showSalaryExpense(fantastiko);
        StoreService.showDeliveryExpense(fantastiko);
        StoreService.showProfit(fantastiko);
    }
}