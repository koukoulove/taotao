package com.taotao.common.exception;

import com.taotao.common.exception.ErrorCode;


public class CodedException extends RuntimeException {

    private static final long serialVersionUID = -6398461679900775331L;
    /**
     * 该异常的错误码
     */
    private ErrorCode errorCode;
    
    /**
     * 异常发生时的参数信息
     */
    private Object[] args;
    
    protected CodedException(ErrorCode errorCode,Throwable throwable, Object...arguments) {
        super(throwable);
        this.errorCode = errorCode;
        this.args = arguments;
    }
    
    protected CodedException(ErrorCode errorCode,Object...arguments){
            super(errorCode.getMessage());
            this.errorCode = errorCode;
            this.args = arguments;
    }
    
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
