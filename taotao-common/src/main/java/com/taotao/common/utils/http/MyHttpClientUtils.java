package com.taotao.common.utils.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;


public class MyHttpClientUtils {

    private static final SSLHandler simpleVerifier = new SSLHandler();

    private static HttpClientBuilder httpClientBuilder;

    private static final int maxTotal = 200;

    private static final int defaultMaxPerRoute = 100;

    static {
        SSLContext sc;
        try {
            sc = SSLContext.getInstance("SSLv3");
            sc.init(null, new TrustManager[] { simpleVerifier }, new java.security.SecureRandom());
            SSLConnectionSocketFactory sslConnFactory = new SSLConnectionSocketFactory(sc, simpleVerifier);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory> create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", sslConnFactory).build();
            // 设置连接池大小
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            connManager.setMaxTotal(maxTotal);
            connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
            httpClientBuilder = HttpClients.custom().setConnectionManager(connManager);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private static class SSLHandler implements X509TrustManager, HostnameVerifier {

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[] {};
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                throws java.security.cert.CertificateException {
        }

        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                throws java.security.cert.CertificateException {
        }

        public boolean verify(String paramString, SSLSession paramSSLSession) {
            return true;
        }
    };

    // -----------华----丽----分----割----线--------------
    // -----------华----丽----分----割----线--------------
    // -----------华----丽----分----割----线--------------

    /**
     * 以Get方式，请求资源或服务
     * 
     * @param client client对象
     * @param url 资源地址
     * @param headers 请求头信息
     * @param context http上下文，用于cookie操作
     * @param encoding 编码
     * @return 返回处理结果
     * @throws HttpProcessException
     * @throws UnsupportedEncodingException 
     */
    public static String get(String url) throws HttpProcessException, UnsupportedEncodingException {
        return send(HttpConfig.custom().url(buildGetURL(url,null,null)).method(HttpMethods.GET));
    }

    public static String get(String url, Map<String, String> maps,String charset) throws HttpProcessException, UnsupportedEncodingException {
        return send(HttpConfig.custom().url(buildGetURL(url,maps,charset)).method(HttpMethods.GET));
    }
    
    public static String buildGetURL(String url,Map<String, String> params, String charset) throws UnsupportedEncodingException {
        if (params == null || params.isEmpty()) {
            return url;
        }
        if(StringUtils.isEmpty(charset)){
            charset = Charset.defaultCharset().displayName();
        }
        StringBuilder query = new StringBuilder();
        Set<Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (!StringUtils.isAnyEmpty(name,value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                    query.append("?");
                }
                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }

        return url+query.toString();
    }
    // -----------华----丽----分----割----线--------------
    // -----------华----丽----分----割----线--------------
    // -----------华----丽----分----割----线--------------
    /**
     * 获取client
     * 
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient getHttpClient() {
        return httpClientBuilder.build();
    }

    /**
     * 请求资源或服务
     * 
     * @param config
     * @return
     * @throws HttpProcessException
     */
    private static String send(HttpConfig config) throws HttpProcessException {
        return fmt2String(execute(config), config.outenc());
    }

    /**
     * 请求资源或服务
     * 
     * @param client client对象
     * @param url 资源地址
     * @param httpMethod 请求方法
     * @param parasMap 请求参数
     * @param headers 请求头信息
     * @param encoding 编码
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    private static HttpResponse execute(HttpConfig config) throws HttpProcessException {

        HttpResponse resp = null;
        try {
            // 创建请求对象
            HttpRequestBase request = getRequest(config.url(), config.method());

            // 设置header信息
            request.setHeaders(config.headers());

            // 判断是否支持设置entity(仅HttpPost、HttpPut、HttpPatch支持)
            if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();

                // 检测url中是否存在参数
                config.url(Utils.checkHasParas(config.url(), nvps, config.inenc()));

                // 装填参数
                HttpEntity entity = Utils.map2HttpEntity(nvps, config.map(), config.inenc());

                // 设置参数到请求对象中
                ((HttpEntityEnclosingRequestBase) request).setEntity(entity);

                Utils.info("请求地址：" + config.url());
                if (nvps.size() > 0) {
                    Utils.info("请求参数：" + nvps.toString());
                }
            } else {
                int idx = config.url().indexOf("?");
                Utils.info("请求地址：" + config.url().substring(0, (idx > 0 ? idx : config.url().length())));
                if (idx > 0) {
                    Utils.info("请求参数：" + config.url().substring(idx + 1));
                }
            }
            // 执行请求操作，并拿到结果（同步阻塞）
            CloseableHttpClient build = getHttpClient();
            resp = (config.context() == null) ? build.execute(request) : build.execute(
                    request, config.context());


            // 获取结果实体
            return resp;

        } catch (IOException e) {
            throw new HttpProcessException(e);
        }
    }

    // -----------华----丽----分----割----线--------------
    // -----------华----丽----分----割----线--------------
    // -----------华----丽----分----割----线--------------

    /**
     * 转化为字符串
     * 
     * @param entity 实体
     * @param encoding 编码
     * @return
     * @throws HttpProcessException
     */
    private static String fmt2String(HttpResponse resp, String encoding) throws HttpProcessException {
        String body = "";
        try {
            if (resp.getEntity() != null) {
                // 按指定编码转换结果实体为String类型
                body = EntityUtils.toString(resp.getEntity(), encoding);
                Utils.info(body);
            }
            EntityUtils.consume(resp.getEntity());
        } catch (IOException e) {
            throw new HttpProcessException(e);
        } finally {
            close(resp);
        }
        return body;
    }

    /**
     * 转化为流
     * 
     * @param entity 实体
     * @param out 输出流
     * @return
     * @throws HttpProcessException
     */
    public static OutputStream fmt2Stream(HttpResponse resp, OutputStream out) throws HttpProcessException {
        try {
            resp.getEntity().writeTo(out);
            EntityUtils.consume(resp.getEntity());
        } catch (IOException e) {
            throw new HttpProcessException(e);
        } finally {
            close(resp);
        }
        return out;
    }

    /**
     * 根据请求方法名，获取request对象
     * 
     * @param url 资源地址
     * @param method 请求方式
     * @return
     */
    private static HttpRequestBase getRequest(String url, HttpMethods method) {
        HttpRequestBase request = null;
        switch (method.getCode()) {
        case 0:// HttpGet
            request = new HttpGet(url);
            break;
        case 1:// HttpPost
            request = new HttpPost(url);
            break;
        case 2:// HttpHead
            request = new HttpHead(url);
            break;
        case 3:// HttpPut
            request = new HttpPut(url);
            break;
        case 4:// HttpDelete
            request = new HttpDelete(url);
            break;
        case 5:// HttpTrace
            request = new HttpTrace(url);
            break;
        case 6:// HttpPatch
            request = new HttpPatch(url);
            break;
        case 7:// HttpOptions
            request = new HttpOptions(url);
            break;
        default:
            request = new HttpPost(url);
            break;
        }
        return request;
    }

    /**
     * 尝试关闭response
     * 
     * @param resp HttpResponse对象
     */
    private static void close(HttpResponse resp) {
        try {
            if (resp == null)
                return;
            // 如果CloseableHttpResponse 是resp的父类，则支持关闭
            if (CloseableHttpResponse.class.isAssignableFrom(resp.getClass())) {
                ((CloseableHttpResponse) resp).close();
            }
        } catch (IOException e) {
            Utils.exception(e);
        }
    }
}
