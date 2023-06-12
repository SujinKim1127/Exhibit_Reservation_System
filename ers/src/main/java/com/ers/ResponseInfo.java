package com.ers;

public class ResponseInfo {
    private int code;
    private Object message;
    public ResponseInfo(int code, Object mesg) {
        this.code = code;
        this.message = mesg;
    }
    public int getCode() { return code; }
    public Object getMessage() { return message; }
    public void setCode(int code) { this.code = code; }
    public void setMessage(Object mesg) { this.message = mesg; }
}
