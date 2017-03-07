package com.example.entity;

import java.io.Serializable;

/**
 * Created by hasee on 2017/3/6.
 */
public class Result  implements Serializable{
    private static final long serialVersionUID=1L;
    private Object dataMap;
    private String message="ok";
    private String code="404";
    public Result(){}
    public Result(Object dataMap){this.dataMap=dataMap;}
    public Result(String message,String code){
        this.message=message;
        this.code=code;
    }
}
