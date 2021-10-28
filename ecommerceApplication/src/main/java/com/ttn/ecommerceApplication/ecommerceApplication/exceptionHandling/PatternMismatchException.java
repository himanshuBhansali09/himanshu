package com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling;

public class PatternMismatchException extends RuntimeException
{
    public PatternMismatchException(String message) {
        super(message);
    }
}
