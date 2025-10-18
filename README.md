# Store Management System

This project models and implements the complete process of stocking and selling goods in a store, including product management, cashier operations, receipt generation, and profit calculation. It is structured using Object-Oriented Programming principles in Java, with a focus on clean architecture, exception handling, and unit testing.

---

## Features

### Store Inventory

- Items are stocked in the store with:
    - Unique ID
    - Name
    - Unit delivery price
    - Category (food or non-food)
    - Expiration date
    - Available quantity
- Items that have expired cannot be sold.
- Selling price is calculated dynamically:
    - Food and non-food items have different markup percentages.
    - If an expiration date is approaching, a discount is applied.
    - The number of days before expiration and discount percentage are configurable per store.

---

### Cashier Management

- Each cashier has:
    - Name
    - ID number
    - Fixed monthly salary
- Only one cashier can operate each checkout counter.
- Each cashier can work on a separate counter.

---

### Receipt System

- When a purchase is made:
    - The cashier verifies if the requested quantity is available.
    - If not, a custom exception is thrown with information about the missing quantity.
    - If successful, a receipt is generated.
- Receipt contains:
    - Serial number
    - Cashier information
    - Date and time of issuance
    - List of purchased items with quantity and price
    - Total amount
- Receipts are:
    - Displayed upon issuance
    - Saved to **individual files**, each named after its serial number
    - Counted for reporting total issued receipts and total turnover

---

### Financial Calculation

The store tracks:

- Total expenses (cashier salaries + goods delivery cost)
- Total income (sold items)
- Profit (income – expenses)

---

## Technical Requirements

- **Language**: Java
- **Concepts Used**:
    - Object-Oriented Programming
    - Exception Handling
    - File Handling (for receipt storage)
    - Unit Testing
