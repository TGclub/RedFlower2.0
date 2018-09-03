package com.weizhi.redflower.exception.handler;

public class NoAuthenticationException extends Exception{
    public NoAuthenticationException() {
    }

    public NoAuthenticationException(String message) {
        super(message);
    }
}