package com.taotao.manage.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.taotao.common.utils.LocalCacheUtil;
import com.taotao.manage.service.CacheLocalService;

@Service
public class CacheLocalServiceImpl implements CacheLocalService {

    private static Logger logger = LoggerFactory.getLogger(CacheLocalServiceImpl.class);

    private static final String NAMESPACE = "taotao";

    @Override
    public void setCache(String key, Object value) {
        this.setCacheDetail(key, value);
    }

    private void setCacheDetail(String key, Object value) {
        setCacheDetail(key, value, Long.MAX_VALUE);
    }

    private void setCacheDetail(String key, Object value, long timeout) {
        if (value == null || key == null) {
            logger.warn("cache key or value is null! not set");
            return;
        }

        LocalCacheUtil.put(NAMESPACE, key, value, timeout);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCache(String key, Class<T> clazz) {
        if (key == null) {
            logger.warn("cache key is null!");
            return null;
        }

        Optional<Object> op = LocalCacheUtil.get(NAMESPACE, key);
        if (op.isPresent()) {
            return (T) op.get();
        }
        return null;

    }

    @Override
    public void removeCache(String key) {
        LocalCacheUtil.remove(NAMESPACE, key);
    }

    @Override
    public void setCache(String key, Object value, int timeout) {
        setCacheDetail(key, value, timeout);
    }

    @Override
    public boolean lock(String key, long seconds) {
        return LocalCacheUtil.put(NAMESPACE, key, System.currentTimeMillis(), seconds);
    }

    @Override
    public boolean lock(String key) {
        return lock(key, 600); // 锁十分钟
    }

    @Override
    public void unlock(String key) {
        LocalCacheUtil.remove(NAMESPACE, key);
    }
}
