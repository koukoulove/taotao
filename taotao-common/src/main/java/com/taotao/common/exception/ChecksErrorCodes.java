package com.taotao.common.exception;

import com.taotao.common.exception.ErrorCode;
import com.taotao.common.exception.Exceptions;

public class ChecksErrorCodes {
    private static final String module = "VALID";

    public static final ErrorCode NULL_VALUE = Exceptions.errorMessage(module, "NULL_VALUE", "不允许未空");
    public static final ErrorCode ILLEGAL_MAIL = Exceptions.errorMessage(module, "ILLEGAL_MAIL","mail格式不合法");
    public static final ErrorCode ILLEGAL_IDCARD = Exceptions.errorMessage(module, "ILLEGAL_IDCARD","身份证格式不正确");
    public static final ErrorCode ILLEGAL_MOBILE = Exceptions.errorMessage(module, "ILLEGAL_MOBILE","手机号码格式不正确");
    public static final ErrorCode ILLEGAL_PHONE = Exceptions.errorMessage(module, "ILLEGAL_PHONE","电话号码格式不正确");
    public static final ErrorCode ILLEGAL_DIGIT = Exceptions.errorMessage(module, "ILLEGAL_DIGIT","请输入整数");
    public static final ErrorCode ILLEGAL_DECIMALS = Exceptions.errorMessage(module, "ILLEGAL_DECIMALS","请输入数字");
    public static final ErrorCode ILLEGAL_BLANKSPACE = Exceptions.errorMessage(module, "ILLEGAL_BLANKSPACE","非空白字符");
    public static final ErrorCode ILLEGAL_CHINESE = Exceptions.errorMessage(module, "ILLEGAL_CHINESE","请输入中文");
    public static final ErrorCode ILLEGAL_DATE = Exceptions.errorMessage(module, "ILLEGAL_DATE","请输入日期");
    public static final ErrorCode ILLEGAL_URL = Exceptions.errorMessage(module, "ILLEGAL_URL","URL格式错误");
    public static final ErrorCode ILLEGAL_POSTCODE = Exceptions.errorMessage(module, "ILLEGAL_POSTCODE","邮政编码格式错误");
    public static final ErrorCode ILLEGAL_IP = Exceptions.errorMessage(module, "ILLEGAL_IP","IP地址格式错误");
    public static final ErrorCode ILLEGAL_BANKCARD = Exceptions.errorMessage(module,"ILLEGAL_BANKCARD","银行卡格式错误");
}
