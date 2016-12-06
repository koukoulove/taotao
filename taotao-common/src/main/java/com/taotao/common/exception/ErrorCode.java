package com.taotao.common.exception;

import java.io.Serializable;

public class ErrorCode implements Serializable {
    
    private static final long serialVersionUID = 3387609973869986635L;
    public static String ILLEGAL_ARGUMENT_MODULE = "ILLEGAL_ARGUMENT";
    
    private String moduleCode;
    private String errorCode;
    private String message;

    protected ErrorCode(String moduleCode,String errorCode) {
            this.moduleCode = moduleCode;
            this.errorCode = errorCode;
    }
    protected ErrorCode(String moduleCode,String errorCode,String message) {
            this.moduleCode = moduleCode;
            this.errorCode = errorCode;
            this.message  = message;
    }
    
    public String toString() {
            if(this.moduleCode==null) return this.errorCode;
            return this.moduleCode+"."+this.errorCode;
    }
    
    public String getModuleCode() {
        return moduleCode;
    }
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
