package com.kolnetworks.koln.api.basebean;

import com.google.gson.annotations.SerializedName;

public class FailureBody<T> {
    private T data;
    @SerializedName(value = "code", alternate = "status")
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
