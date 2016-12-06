package com.taotao.common.exception;

import com.taotao.common.exception.ErrorCode;
import com.taotao.common.exception.Exceptions;



public class TaoTaoErrorCodes {
    private static final String module = "TAO_TAO";

    public static final ErrorCode AMOUNT_IS_VALID = Exceptions.errorMessage(module, "90001", "交易金额必须为不小于50的正整数");
    
}
