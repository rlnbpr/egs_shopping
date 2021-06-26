package com.egs.shopping.service.exception;

import java.security.InvalidParameterException;

public class InvalidEmailPasswordException extends InvalidParameterException {
    public InvalidEmailPasswordException() {
        super("email or password is not valid");
    }
}
