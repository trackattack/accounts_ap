package com.demo.ing.exceptions;

public class IbanException extends RuntimeException {
    public IbanException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public IbanException(String errorMessage) {
        super(errorMessage);
    }

    public IbanException() {
    }
}
