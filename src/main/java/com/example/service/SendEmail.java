package com.example.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by hasee on 2017/3/23.
 */
public class SendEmail {
//        private static String from = "lzs105@163.com";
//        private static String pwd = "lzswot081212.";
//        private static String from = "578776370@qq.com";
//        private static String pwd = "torxchekuyeobfhh";
        private static String from = "lzs105@sina.com";
        private static String pwd = "lzszjy081212.";

//    public SendEmail(String to,String content){
//
//    }

//    public void sendEmail(String to,String content){
//
//    }

    private static Session getSession(){
        Properties properties = System.getProperties();
//        String host = "smtp.163.com";
        String host = "smtp.sina.com";
//        String host = "smtp.qq.com";
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth","true");
        //properties.put("mail.smtp.port", "587");
        return Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(from, pwd); //发件人邮件用户名、密码
            }
        });
    }
    public static void send(String to,String content){
        String nick="";
        try {
            nick=javax.mail.internet.MimeUtility.encodeText("网投");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Session session = getSession();
        try{
            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(nick+"<"+from+">"));
            message.setFrom(new InternetAddress(from,"网投", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("您注册的账号已创建，请激活");
            message.setContent(content , "text/html;charset=utf-8");
            Transport.send(message);
        }catch (Exception me) {
            me.printStackTrace();
        }
    }
}
