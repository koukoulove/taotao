package com.taotao.common.utils;

import java.util.regex.Pattern;

public class RegexUtils {

    /** 匹配中文字符 */
    public static String ZHCN_REGEX = "[\u4e00-\u9fa5]";
    /** 匹配Email地址 */
    public static String EMAIL_REGEX = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
    /** 匹配网址URL */
    public static String URL_REGEX = "[a-zA-z]+://[^\\s]*";
    /** 匹配国内固定电话号码 */
    public static String PHONE_FIXED_REGEX = "\\d{3}-\\d{8}|\\d{4}-\\{7,8}*";
    /** 匹配腾讯QQ号 */
    public static String QQ_REGEX = "[1-9][0-9]{4,}";
    /** 匹配中国邮政编码 */
    public static String ZIPCODE_REGEX = "[1-9]\\d{5}(?!\\d)";
    /** 匹配18位身份证号 */
    public static String IDNO_REGEX = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";
    /** 匹配(年-月-日)格式日期 */
    public static String YYYY_MM_DD_REGEX = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
    /** 匹配正整数 */
    public static String POSITIVE_INTEGER_REGEX = "^[1-9]\\d*$";
    /** 匹配负整数 */
    public static String NEGATIVE_INTEGER_REGEX = "^-[1-9]\\d*$";
    /** 匹配整数 */
    public static String INTEGER_REGEX = "^-?[1-9]\\d*|0$";
    /** 匹配非负整数（正整数 + 0） */
    public static String NONNEGATIVE_INTEGER_REGEX = "^[1-9]\\d*|0$";
    /** 匹配非正整数（负整数 + 0） */
    public static String NONPOSITIVE_INTEGER_REGEX = "^-[1-9]\\d*|0$";
    
    /** 匹配浮点数  0.00+正浮点数+负浮点数 */
    public static String FLOAT_REGEX = "^-?[1-9]\\d*\\.\\d*|0\\.\\d*[0-9]\\d*$";
    /** 匹配浮点数  保留N位小数 0.00+正浮点数+负浮点数 */
    public static String FLOAT_POINT_REGEX = "^-?[1-9]\\d*\\.\\d*|0\\.\\d{0,%d}$";
    
    // "^[+-]?(\\d*)?(\\.)?\\d{0,%}$";
    /** 匹配正浮点数  */
    public static String POSITIVE_FLOAT_REGEX = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
    /** 匹配负浮点数   */
    public static String NEGATIVE_FLOAT_REGEX = "^-[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$";
    
    /**
     * 手机号码: 
     * 13[0-9], 14[5,7], 15[0, 1, 2, 3, 5, 6, 7, 8, 9], 17[0, 1, 6, 7, 8], 18[0-9]
     * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
     * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
     * 电信号段: 133,149,153,170,173,177,180,181,189
     */
    public static String TELEPHONE_REGEX = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";
    /**
     * 中国移动：China Mobile
     * 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
     */
    public static String TELEPHONE_CHINA_MOBILE_REGEX = "^1(3[4-9]|4[7]|5[0-27-9]|7[08]|8[2-478])\\d{8}$";
    /**
     * 中国联通：China Unicom
     * 130,131,132,145,155,156,170,171,175,176,185,186
     */
    public static String TELEPHONE_CHINA_UNICOM_REGEX = "^1(3[0-2]|4[5]|5[56]|7[0156]|8[56])\\d{8}$";
    /**
     * 中国电信：China Telecom
     * 133,149,153,170,173,177,180,181,189
     */
    public static String TELEPHONE_CHINA_TELECOM_REGEX = "^1(3[3]|4[9]|53|7[037]|8[019])\\d{8}$";
    
    /**
     * 
     * @param param 参数
     * @param regex 正则表达式
     * @return true - 匹配成功； false - 匹配失败
     */
    public static boolean regex(String param,String regex){
        return Pattern.matches(regex, param);
    }
    public static void main(String[] args) {
        String param = "0.00";
        
        
        String sttr = String.format(FLOAT_POINT_REGEX, 2);
        System.out.println(sttr+"==="+regex(param,sttr));
        
        String regex1 = "^-?[1-9]\\d*\\.\\d*|0\\.\\d{0,2}$";
        System.out.println(regex1+"==="+regex(param,regex1));
        
        
        
        String regex = "^[+-]?(\\d*)?(\\.)?\\d{0,2}$";
        System.out.println(regex+"==="+regex(param,regex));
    }
}
