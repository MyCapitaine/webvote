package com.example.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * Created by hasee on 2017/4/14.
 */
public class EmailSession {
    private static String from = "lzs105@sina.com";
    private static String pwd = "lzszjy081212.";

    public static Session getSession(){
        Properties properties = System.getProperties();
//        String host = "smtp.163.com";

        String host = "smtp.sina.com";

//        String host = "smtp.qq.com";

        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth","true");
        //properties.put("mail.smtp.port", "587");
        return Session.getInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(from, pwd); //发件人邮件用户名、密码
            }
        });
    }
    public static String getFrom(){return from;}

    public static String getPwd(){return pwd;}
}
