package com.taotao.common.exception;

import com.taotao.common.exception.ErrorCode;
import com.taotao.common.exception.Exceptions;



public class TaoTaoErrorCodes {
    private static final String module = "TAO_TAO";

    public static final ErrorCode OP_FAILURE = Exceptions.errorMessage(module, "90001", "操作失败");
    
}
