package com.yubin.hotelSys.result;


// 实现响应的枚举类
public enum ExceptionMsg {
    SUCCESS(0, "success"),
    FAILED(-1,"fail");

    private ExceptionMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}