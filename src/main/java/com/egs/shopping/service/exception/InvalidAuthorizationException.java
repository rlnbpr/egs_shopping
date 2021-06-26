package com.egs.shopping.service.exception;

public class InvalidAuthorizationException extends IllegalArgumentException {
    public InvalidAuthorizationException() {
        super("You are not authorized to perform this operation");
    }
}
