package org.nbu.exceptions;

public class InvalidPaymentException extends Exception{
    public InvalidPaymentException() {
        super("Invalid customer payment!");
    }

    public InvalidPaymentException(String message) {
        super(message);
    }
}
