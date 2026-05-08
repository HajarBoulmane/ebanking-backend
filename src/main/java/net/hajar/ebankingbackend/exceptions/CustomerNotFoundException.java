package net.hajar.ebankingbackend.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
    super(message);
    }
}
