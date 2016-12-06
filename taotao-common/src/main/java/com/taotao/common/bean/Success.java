package com.taotao.common.bean;

import org.springframework.http.HttpStatus;

public class Success extends Message {

    public Success() {
        super(String.valueOf(HttpStatus.OK.value()), "");
    }

    public Success(String msg) {
        super(String.valueOf(HttpStatus.OK.value()), msg);
    }
}
