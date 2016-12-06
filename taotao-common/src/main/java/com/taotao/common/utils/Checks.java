package com.taotao.common.utils;

import static org.valid4j.Validation.validate;

import java.util.regex.Pattern;

import com.google.common.base.Strings;
import com.taotao.common.exception.ChecksErrorCodes;
import com.taotao.common.exception.ErrorCode;
import com.taotao.common.exception.Exceptions;

public class Checks {

    /** 普通非空检查 */
    public static void notNull(Object obj, ErrorCode ex, Object... args) {
        validate(obj != null, Exceptions.fail(ex, args));
    }

    /** 普通非空检查 */
    public static void notNull(Object obj) {
        notNull(obj, ChecksErrorCodes.NULL_VALUE, obj);
    }

    /** 普通非空检查 */
    public static void notNull(String str, ErrorCode ex, Object... args) {
        validate(!Strings.isNullOrEmpty(str), Exceptions.fail(ex, args));
    }

    /** 普通非空检查 */
    public static void notNull(String str) {
        notNull(str, ChecksErrorCodes.NULL_VALUE, str);
    }

    /** 验证Email */
    public static void email(String email, ErrorCode ex, Object... args) {
        if (!RegexUtils.regex(email, RegexUtils.EMAIL_REGEX))
            throw Exceptions.fail(ex, args);
    }
    /** 验证Email */
    public static void email(String email) {
        email(email, ChecksErrorCodes.ILLEGAL_MAIL);
    }

    /** 验证身份证号码 匹配18位身份证号 */
    public static void idCard(String idCard, ErrorCode ex, Object... args) {
        if (!RegexUtils.regex(idCard, RegexUtils.IDNO_REGEX))
            throw Exceptions.fail(ex, args);
    }
    /** 验证身份证号码 匹配18位身份证号 */
    public static void idCard(String idCard) {
        idCard(idCard, ChecksErrorCodes.ILLEGAL_IDCARD, idCard);
    }
    /** 验证手机号码  */
    public static void mobile(String mobile, ErrorCode ex, Object... args) {
        if (!RegexUtils.regex(mobile, RegexUtils.TELEPHONE_REGEX))
            throw Exceptions.fail(ex, args);
    }
    /** 验证手机号码  */
    public static void mobile(String mobile) {
        mobile(mobile, ChecksErrorCodes.ILLEGAL_MOBILE, mobile);
    }
    
    

    /**
     * 验证固定电话号码
     * 
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *        <p>
     *        <b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字， 数字之后是空格分隔的国家（地区）代码。
     *        </p>
     *        <p>
     *        <b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号—— 对不使用地区或城市代码的国家（地区），则省略该组件。
     *        </p>
     *        <p>
     *        <b>电话号码：</b>这包含从 0 到 9 的一个或多个数字
     *        </p>
     */
    public static void phone(String phone, ErrorCode ex, Object... args) {
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        if (!Pattern.matches(regex, phone))
            throw Exceptions.fail(ex, args);
    }

    public static void phone(String phone) {
        phone(phone, ChecksErrorCodes.ILLEGAL_PHONE, phone);
    }

    /** 验证整数（正整数和负整数） */
    public static void isDigit(String digit, ErrorCode ex, Object... args) {
        if (!RegexUtils.regex(digit, RegexUtils.INTEGER_REGEX))
            throw Exceptions.fail(ex, args);
    }
    /** 验证整数（正整数和负整数） */
    public static void isDigit(String digit) {
        isDigit(digit, ChecksErrorCodes.ILLEGAL_DIGIT, digit);
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     * 
     * @param decimals 要验证的数
     * @param decimal 小数部分的最大长度
     */
    public static void decimals(String decimals, int decimal, ErrorCode ex, Object... args) {
        notNull(decimals, ex, args);
        if (".".equals(decimals))
            throw Exceptions.fail(ex, args);
        final String regex = "^[+-]?(\\d*)?(\\.)?\\d{0," + decimal + "}$";
        if (!Pattern.matches(regex, decimals))
            throw Exceptions.fail(ex, args);
    }

    public static void decimals(String decimals, int decimal) {
        decimals(decimals, decimal, ChecksErrorCodes.ILLEGAL_DECIMALS, decimals);
    }

    /** 验证整数和浮点数 */
    public static void isDecimals(String decimals, ErrorCode ex, Object... args) {
        if (!RegexUtils.regex(decimals, RegexUtils.INTEGER_REGEX) 
                && !RegexUtils.regex(decimals, RegexUtils.FLOAT_REGEX))
            throw Exceptions.fail(ex, args);
    }
    /** 验证整数和浮点数 */
    public static void isDecimals(String decimals) {
        isDecimals(decimals, ChecksErrorCodes.ILLEGAL_DECIMALS, decimals);
    }
    
    
    /** 验证中文 */
    public static void isChinese(String chinese, ErrorCode ex, Object... args) {
        if (!RegexUtils.regex(chinese, RegexUtils.ZHCN_REGEX))
            throw Exceptions.fail(ex, args);
    }
    /** 验证中文 */
    public static void isChinese(String chinese) {
        isChinese(chinese, ChecksErrorCodes.ILLEGAL_CHINESE, chinese);
    }
    /** 验证日期 格式：1992-09-03 */
    public static void isDate(String date, ErrorCode ex, Object... args) {
        if (!RegexUtils.regex(date, RegexUtils.YYYY_MM_DD_REGEX))
            throw Exceptions.fail(ex, args);
    }
    /** 验证日期 格式：1992-09-03 */
    public static void isDate(String date) {
        isDate(date, ChecksErrorCodes.ILLEGAL_DATE, date);
    }

    /** 验证URL地址 */
    public static void isUrl(String url, ErrorCode ex, Object... args) {
        if (!RegexUtils.regex(url, RegexUtils.URL_REGEX))
            throw Exceptions.fail(ex, args);
    }
    /** 验证URL地址 */
    public static void isUrl(String url) {
        isUrl(url, ChecksErrorCodes.ILLEGAL_URL, url);
    }
    /** 匹配中国邮政编码 */
    public static void isPostCode(String postcode, ErrorCode ex, Object... args) {
        if (!RegexUtils.regex(postcode, RegexUtils.ZIPCODE_REGEX))
            throw Exceptions.fail(ex, args);
    }
    /** 匹配中国邮政编码 */
    public void isPostCode(String postcode) {
        isPostCode(postcode, ChecksErrorCodes.ILLEGAL_POSTCODE, postcode);
    }

   
}
