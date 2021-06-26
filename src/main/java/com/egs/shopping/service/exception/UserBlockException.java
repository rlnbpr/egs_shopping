package com.egs.shopping.service.exception;

public class UserBlockException extends IllegalArgumentException {
    public UserBlockException() {
        super("Customer is blocked. please contact supporting service.");
    }
}
