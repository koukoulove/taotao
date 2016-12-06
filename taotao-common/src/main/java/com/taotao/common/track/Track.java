package com.taotao.common.track;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class Track {
    //当前线程UID存储
    private final static ThreadLocal<String> uids = new ThreadLocal<>();
    
    //最近一次正常调用的功能
    private final static ThreadLocal<String> codes = new ThreadLocal<>();
    
    private static final String UID_IN_COOKIE = "u_=";
    
    //@Value("${track.access}")
    private static String accessEnable;


    //@Value("${track.service}")
    private static String serviceEnable;
    
    

    public static String getAccessEnable() {
        return accessEnable;
    }

    public static void setAccessEnable(String accessEnable) {
        Track.accessEnable = accessEnable;
    }

    public static String getServiceEnable() {
        return serviceEnable;
    }

    public static void setServiceEnable(String serviceEnable) {
        Track.serviceEnable = serviceEnable;
    }

    /**
     * 记录应用的访问情况的Logger
     */
    private static final Logger access = LoggerFactory.getLogger("TRACK.ACCESS");

    /**
     * 记录应用服务层调用的Logger
     */
    private static final Logger service = LoggerFactory.getLogger("TRACK.SERVICE");

    private static String uri() {
        try {
            final HttpServletRequest req = request();
            final String query = req.getQueryString();
            final String uri = req.getRequestURI();
            if (query == null) {
                return uri;
            }
            return uri + "?" + query;
        } catch (Exception e) {
            return "";
        }
    }

    private static HttpServletRequest request() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) (RequestContextHolder
                .getRequestAttributes());
        if (attrs == null)
            return null;
        return attrs.getRequest();

    }

    public static void request(String format, Object... arguments) {
        if (Boolean.valueOf(accessEnable)) {
            final String uri = uri();
            access.info(String.format("REQ [%s] [%s] [%s] ", uid(), uri, ctx()) + format, arguments);
        }
    }

    public static void response(String format, Object... arguments) {
        if (Boolean.valueOf(accessEnable)) {
            final String uri = uri();
            access.info(String.format("RES [%s] [%s] [%s] ", uid(), uri, ctx()) + format, arguments);
        }
    }

    public static void service(String format, Object... arguments) {
        if (Boolean.valueOf(serviceEnable)) {
            final String ctx = ctx();
            service.info(String.format("SRV [%s] [%s] ", uid(), ctx) + format, arguments);
            codes.set(ctx);
        }
    }

    public static String uid() {
        String uid = uids.get();
        if (uid != null) {
            return uid;
        }
        final HttpServletRequest request = request();
        
        try {
            // 从Cookie中寻找是否有uid信息
            final String cookie = request.getHeader("Cookie");
            int index = -1;
            if (cookie != null) {
                index = cookie.indexOf(UID_IN_COOKIE);
            }
            // 没有定义uid则使用sessionId作为uid
            if (index < 0) {
                uid = request.getSession(true).getId();
            } else {
                // 从Cookie中取出uid信息
                index = index + UID_IN_COOKIE.length();
                int indexEnd = cookie.indexOf(';', index);
                if (indexEnd < 0) { // uid cookie在字符串尾
                    uid = cookie.substring(index);
                } else {
                    uid = cookie.substring(index, indexEnd);
                }
            }
        } catch (Exception e) {
            return "";
        }
        uids.set(uid);
        return uid;
    }
    
    private static String ctx() {
        //构造上下文信息
        final StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        final StackTraceElement stack = stacks[3];
        StringBuilder context = new StringBuilder();
        context.append(scn(stack.getClassName()))
            .append('.')
            .append(stack.getMethodName())
            .append("-")
            .append(stack.getLineNumber());
        return context.toString();
    }
    
    private static String scn(String clazz) {
        final int index = clazz.lastIndexOf('.');
        if(index==-1){
            return clazz;
        }
        return clazz.substring(index+1);
    }
}
