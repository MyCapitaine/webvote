package com.example.util;

import com.example.serviceInterface.SendEmail;

/**
 * Created by hasee on 2017/4/14.
 */
public class SendEmailFactory {
    public static SendEmail getInstance(Class<? extends SendEmail> c){
        try {
            return (SendEmail) c.newInstance();
        } catch (Exception e) {
            System.out.println("无效参数,无法初始化");
        }
        return null;
    }
}
