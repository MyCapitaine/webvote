package com.example.service;

import com.example.exception.SendEmailException;

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


    private static Session getSession(){
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

    private static String getValidateContent(String to, String validator){
        StringBuffer  content=new StringBuffer ("您好：");
        content.append(to+"!<br>");
        content.append("请点击下面的链接来激活您的账号（如果不能跳转，请复制粘贴到浏览器地址栏）<br>");
//        content.append("http://localhost:8080/validate/");
        content.append("http://localhost:8080/validate?token=");
        content.append(validator);
        return content.toString();
    }

    public static void sendValidateEmail(String to, String validator) throws  SendEmailException {
        //Session session = getSession();
        String content = getValidateContent(to,validator);
        send(to,content);
    }

    private static String getPasswordContent(String loginName,String password){
        StringBuffer  content=new StringBuffer ("您好：");
        content.append(loginName+"!<br>");
        content.append("您的登录密码是： "+password+" <br>");
        content.append("请妥善保管好您的登录密码!");
        return content.toString();
    }

    /**
     * @param to 收件人
     * @param loginName 登录名，构成邮件内容
     * @param password 登录密码，构成邮件内容
     * @throws SendEmailException
     */
    public static void sendPasswordEmail(String to,String loginName, String password)throws  SendEmailException{
        String content = getPasswordContent(loginName,password);
        send(to,content);
    }

    /**
     * @param to 收件人
     * @param content 邮件内容
     * @throws SendEmailException
     */
    private static void send(String to ,String content)throws  SendEmailException {
        try{
            MimeMessage message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(from,"网投", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("您注册的账号已创建，请激活");
            message.setContent(content , "text/html;charset=utf-8");
            Transport.send(message);
        }catch (MessagingException me) {
            if(me.getMessage().equals("Invalid Addresses")){
                throw new SendEmailException("绑定邮箱非法");
            }
            else{
                throw new SendEmailException("邮件系统出了点问题，正在修复。请稍后再试");
            }
        }
        catch (UnsupportedEncodingException ue){
            throw new SendEmailException("邮件系统出了点问题：不支持的编码格式");
        }
    }



}
