package com.example.exception;

/**
 * Created by hasee on 2017/4/11.
 */
public class SendEmailException extends Exception {
    public SendEmailException(String message){
        super(message);
    }
}
