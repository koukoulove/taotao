package com.taotao.common.bean;

public class Message {

    private String code;
    private String msg;
    
    public Message(){}

    public Message(String code, String msg) {
            this.code = code;
            this.msg = msg;
    }

    public String getCode() {
            return code;
    }

    public void setCode(String code) {
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
            return "code=" + this.code + " msg=" + this.msg;
    }

}
