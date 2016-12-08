package com.taotao.common.utils;

import java.util.ArrayList;
import java.util.List;

public class TaoTaoUtils {

    /**
     * 字符串 ids 转化为 集合
     */
    public static List<Object> idsToList(String ids){
        List<Object> idsList = new ArrayList<Object>();
        String[] split = ids.split(",");
        for (String id : split) {
            idsList.add(id);
        }
        return idsList;
    }
}
