package com.example.entity;

import java.io.Serializable;

/**
 * Created by hasee on 2017/3/6.
 */
public class ServiceResult<T> implements Serializable{
    private static final long serialVersionUID = -4699713095477151086L;

    /**
     * 数据
     */
    private T data;
    /**
     * 信息
     */
    private String message;
    /**
     * 是否成功
     */
    private boolean success;

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ServiceResult() {
        super();
    }

    public ServiceResult(T data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public ServiceResult(T data, String message) {
        this.data = data;
        this.message = message;
        this.success = true;
    }

    public ServiceResult(T data) {
        this.data = data;
        this.success = true;
    }
}