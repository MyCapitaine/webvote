package com.example.util;

import java.security.MessageDigest;

/**
 * Created by hasee on 2017/4/7.
 */
public class Code {
    public final static String MD5Encoder(String s, String charset) throws Exception {
        try {
            byte[] btInput = s.getBytes(charset);
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                int val = ((int) md[i]) & 0xff;
                if (val < 16){
                    sb.append("0");
                }
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new Exception("MD5Encoder error");
        }
    }
}
