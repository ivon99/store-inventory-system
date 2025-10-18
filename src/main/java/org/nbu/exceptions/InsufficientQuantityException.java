package org.nbu.exceptions;

public class InsufficientQuantityException extends Exception {
    public InsufficientQuantityException() {
        super("Insufficient quantity of product : ");
    }

    public InsufficientQuantityException(String message) {
        super(message);
    }
}
