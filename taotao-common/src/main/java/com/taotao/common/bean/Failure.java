package com.taotao.common.bean;

public class Failure extends Message {

    public Failure() {
        super();
    }

    public Failure(String code, String msg) {
        super(code, msg);
    }

    public String toString() {
        return "Failure [code=" + this.getCode() + ",msg=" + this.getMsg() + "]";
    }

}
