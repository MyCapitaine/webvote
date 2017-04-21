package com.example.serviceInterface;

import com.example.exception.SendEmailException;

/**
 * Created by hasee on 2017/4/14.
 */
public interface SendEmail {
    void send(String to,String url) throws SendEmailException;
}
