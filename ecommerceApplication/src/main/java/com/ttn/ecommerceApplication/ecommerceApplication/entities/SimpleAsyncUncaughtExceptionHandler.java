package com.ttn.ecommerceApplication.ecommerceApplication.entities;

import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.UserNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class SimpleAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

    private static final Log logger = LogFactory.getLog(org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler.class);


    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {

        throw new UserNotFoundException("user with this id is not present");
        }
    }

