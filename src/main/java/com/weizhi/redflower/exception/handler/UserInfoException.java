package com.weizhi.redflower.exception.handler;

public class UserInfoException extends Exception {
    private String message ;
    public UserInfoException(){

    }

    public UserInfoException(String message){
        this.message=message;
    }
}
