package com.egs.shopping.service.exception;

import java.security.InvalidParameterException;

public class InvalidTokenException extends InvalidParameterException {
    public InvalidTokenException() {
        super("Token is not valid");
    }
}
