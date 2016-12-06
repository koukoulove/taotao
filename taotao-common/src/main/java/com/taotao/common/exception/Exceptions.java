package com.taotao.common.exception;

import com.taotao.common.exception.CodedException;
import com.taotao.common.exception.ErrorCode;




public class Exceptions {
    
    public static ErrorCode errorMessage(String module,String code, String message) {
        return new ErrorCode(module,code,message);
    }
    
    public static CodedException fail(ErrorCode errCode) {          
        return new CodedException(errCode);
    }
    
    /**
     * 创建带参数的CodedException
     * @param errCode
     * @param args  参数
     * @return
     */
    public static CodedException fail(ErrorCode errCode,Object...args) {
            if(args==null) {
                    return fail(errCode);
            }
            CodedException coded = new CodedException(errCode,null,args);
            return coded;
    }
}
