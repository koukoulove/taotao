package com.taotao.common.utils;

import java.util.concurrent.TimeUnit;




import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

import com.google.common.base.Optional;

public class LocalCacheUtil {

    private final static ExpiringMap<String, Object> map =
            ExpiringMap.builder().variableExpiration().build();

    /**
     * 获取缓存中的值
     * 
     * @param namespace 命名空间
     * @param key 缓存键
     * @return 缓存值的Optional封装，请用isPresent()判断是否存在
     */
    public static Optional<Object> get(String namespace, String key) {
        return Optional.fromNullable(map.get(combineKey(namespace, key)));
    }

    /**
     * 设置缓存中的值
     * 
     * @param namespace 命名空间
     * @param key 缓存键
     * @param value 缓存值
     * @param expiredAfter 失效时间(秒)
     * @return
     */
    public static boolean put(String namespace, String key, Object value, long expiredAfter) {
        map.put(combineKey(namespace, key), value, ExpirationPolicy.CREATED, expiredAfter,
                TimeUnit.SECONDS);
        return true;
    }

    /**
     * 删除缓存中的值
     * 
     * @param namespace
     * @param key
     * @return 被删除的值
     */
    public static Object remove(String namespace, String key) {
        return map.remove(combineKey(namespace, key));
    }

    private static String combineKey(String namespace, String key) {
        return String.format("%s-%s", namespace, key);
    }

}
