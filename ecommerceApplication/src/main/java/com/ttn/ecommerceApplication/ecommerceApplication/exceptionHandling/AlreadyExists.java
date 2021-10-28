package com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling;

public class AlreadyExists extends RuntimeException
{
    public AlreadyExists(String message) {
        super(message);
    }
}
