package com.taotao.manage.service;

public interface CacheLocalService{

    /**
     * 设置缓存
     * 
     * @param key           缓存key
     * @param value         缓存值
     */
    public void setCache(String key,Object value);
    
    /**
     * 设置缓存
     * 
     * @param key           缓存key
     * @param value         缓存值
     */
    public void setCache(String key,Object value,int timeout);
    
    /**
     * 获取缓存值
     * 
     * @param key           缓存key
     * @return
     */
    public <T> T getCache(String key,Class<T> clazz);
    
    /**
     * 删除缓存
     * 
     * @param key
     */
    public void removeCache(String key);

    /**
     * 解锁
     * 
     * @param key
     */
    void unlock(String key);

    /**
     * 加锁
     * 
     * @param key
     * @param seconds 过期秒数
     * @return
     */
    boolean lock(String key, long seconds);
    
    /**
     * 加锁
     * 
     * @param key
     * @return
     */
    boolean lock(String key);
}
