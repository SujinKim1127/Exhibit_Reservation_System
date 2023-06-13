package com.ers.model;

public class ResponseInfo {
    private int code;
    private Object message;

    public ResponseInfo(int code, Object message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object value) {
        this.message = value;
    }
}
