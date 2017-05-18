package com.example.util;

import com.example.exception.SendEmailException;
import com.example.serviceInterface.SendEmail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by hasee on 2017/4/14.
 */
public class SendResetPasswordEmail implements SendEmail {

    private static String getContent(String to, String url){
        StringBuffer  content=new StringBuffer ("您好：");
        content.append(to);
        content.append("!<br>");
        content.append("请点击下面的链接完成重置密码操作<br>");
        content.append("<h3>");
        content.append("（下面的链接仅能访问一次，刷新/关闭后链接失效）");
        content.append("</h3>");
        content.append("http://localhost:8080/resetPasswordValidate?");
        content.append(url);
        content.append("<br>");
        content.append("（如果上面的链接不能点击，请复制粘贴到浏览器地址栏）");
        return content.toString();
    }

    @Override
    public void send(String to, String url) throws SendEmailException {
        try{
            MimeMessage message = new MimeMessage(EmailSession.getSession());
            message.setFrom(new InternetAddress(EmailSession.getFrom(),"网投", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("修改密码验证");
            message.setContent(getContent(to,url) , "text/html;charset=utf-8");
            Transport.send(message);
        }catch (MessagingException me) {
            if(me.getMessage().equals("Invalid Addresses")){
                throw new SendEmailException("绑定邮箱非法");
            }
            else{
                throw new SendEmailException("邮件系统出了点问题，正在修复。请稍后再试");
            }
        }
        catch (UnsupportedEncodingException ue) {
            throw new SendEmailException("邮件系统出了点问题：不支持的编码格式");
        }
    }
}
