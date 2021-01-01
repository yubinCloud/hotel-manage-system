package com.yubin.hotelSys.result;

// 实现返回对象实体
public class Response {
    /** 返回信息码*/
    private int code = 0;
    /** 返回信息内容*/
    private String msg = "success";

    public Response() {
    }

    public Response(ExceptionMsg msg){
        this.code = msg.getCode();
        this.msg = msg.getMsg();
    }

    public Response(int code) {
        this.code = code;
        this.msg = "";
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Response{" +
                "rspCode='" + code + '\'' +
                ", rspMsg='" + msg + '\'' +
                '}';
    }
}