package com.example.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hasee on 2017/4/20.
 */
public class Encrypt {
    public static  String encrypt(String source){

        int length = source.length();
        String result="";

        if(length<=3){
            result=replaceAction(source,".*");
        }
        else if(length==4){
            result=replaceAction(source,"(?<=.{1}).");
        }
        else if(length>4&& length <= 6){
            result=replaceAction(source,"(?<=.{1}).(?=.{1})");
        }
        else if(length>6&& length <= 9){
            result=replaceAction(source,"(?<=.{2}).(?=.{1})");
        }
        else{
            result=replaceAction(source,"(?<=.{2}).(?=.{2})");
        }
        return result;
    }

    private static String replaceAction(String username, String regular) {
        return username.replaceAll(regular, "*");
    }

    public static String encryptEmailPrefix(String email){

        if(email==null){
            return null;
        }

        String prefix="";
        String suffix="@";
        String result="";

        String regular="([a-zA-Z_0-9]{0,})@((([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,})";
        Pattern emailPattern = Pattern.compile(regular);
        Matcher emailMatcher = emailPattern.matcher(email);

        if(!emailMatcher.find()){
            return encrypt(email);
        }
        prefix = emailMatcher.group(1);
        suffix += emailMatcher.group(2);

        int length = prefix.length();
        if(length<=2){
            prefix=replaceAction(prefix,".*");
        }
        else if(length<=4){
            prefix=replaceAction(prefix,"(?<=[a-zA-Z_0-9]{1})[a-zA-Z_0-9]");
        }
        else if(length <= 6){
            prefix=replaceAction(prefix,"(?<=[a-zA-Z_0-9]{1})[a-zA-Z_0-9](?=[a-zA-Z_0-9]{1})");
        }
        else if(length <= 9){
            prefix=replaceAction(prefix,"(?<=[a-zA-Z_0-9]{2})[a-zA-Z_0-9](?=[a-zA-Z_0-9]{1})");
        }
        else{
            prefix=replaceAction(prefix,"(?<=[a-zA-Z_0-9]{2})[a-zA-Z_0-9](?=[a-zA-Z_0-9]{2})");
        }
        result =prefix + suffix;

        return result;
    }
}
