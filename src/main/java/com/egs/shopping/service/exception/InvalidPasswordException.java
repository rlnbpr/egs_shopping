package com.egs.shopping.service.exception;

public class InvalidPasswordException extends IllegalArgumentException {
    public InvalidPasswordException() {
        super("Password is not valid");
    }
}
