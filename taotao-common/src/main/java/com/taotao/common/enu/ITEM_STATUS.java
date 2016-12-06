package com.taotao.common.enu;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;



/**
 * 商品状态
 */
public enum ITEM_STATUS {

    NORMAL("1","新建"),
    ON_LINE("2","上架"),
    OFF_LINE("3","下架"),
    DELETE("4","作废");
    
    private static final Map<String, String> VALUES_MAP;
    
    static {
        Map<String, String> map = new LinkedHashMap<>();
        for (ITEM_STATUS each : values()) {
                map.put(each.getCode(), each.getName());
        }
        VALUES_MAP = Collections.unmodifiableMap(map);
    }
    
    private String code;
    private String name;
    
    private ITEM_STATUS(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public String getCode() {
            return code;
    }
    public String getName() {
            return name;
    }
    
    public static Map<String, String> toMap() {
            return VALUES_MAP;
    }
    public static ITEM_STATUS getEnumByCode(String code) {
        for (ITEM_STATUS each : values()) {
                if (each.getCode().equals(code)) {
                        return each;
                }
        }
        return null;
    }
    
    public static String getNameByCode(String code) {
        ITEM_STATUS en = getEnumByCode(code);
        if (null != en) {
                return en.getName();
        }
        return null;
    }
}
