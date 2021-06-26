package com.egs.shopping.service.exception;

public class UserNotFoundException extends IllegalArgumentException {
    public UserNotFoundException() {
        super("User not found");
    }
}
