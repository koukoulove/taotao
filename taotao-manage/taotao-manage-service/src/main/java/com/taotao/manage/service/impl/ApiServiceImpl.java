package com.taotao.manage.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ApiServiceImpl implements BeanFactoryAware{

    private BeanFactory beanFactory;
    
    @Autowired(required = false)
    private RequestConfig requestConfig;
    
    public String doGet(String url) throws ClientProtocolException, IOException{
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        
        try {
            response = getHttpClient().execute(httpGet);
            if(response.getStatusLine().getStatusCode()== HttpStatus.OK.value()){
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }finally{
            if(null != response){
                response.close();
            }
        }
        return null;
    }
    
    public String doGet(String url,Map<String,String> params) throws URISyntaxException, ClientProtocolException, IOException{
        if(null == params || params.isEmpty()){
            return doGet(url);
        }
        URIBuilder builder = new URIBuilder(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.setParameter(entry.getKey(), entry.getValue());
        }
        return doGet(builder.build().toString());
    }
    
    public String doPost(String url, Map<String, String> params) throws ClientProtocolException, IOException{
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if(params != null && !params.isEmpty()){
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            } 
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
            httpPost.setEntity(formEntity);
        }
        CloseableHttpResponse response = null;
        try{
            response = getHttpClient().execute(httpPost);
            if(response.getStatusLine().getStatusCode()== HttpStatus.OK.value()){
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }finally{
            if (response != null) {
                response.close();
            }
        }
        return null;
    }
    
    /**
     * 执行post请求，发送json数据
     * 
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public String doPostJson(String url, String json) throws IOException {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (null != json) {
            // 构造一个字符串的实体
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(stringEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = getHttpClient().execute(httpPost);
            if(response.getStatusLine().getStatusCode()== HttpStatus.OK.value()){
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }
    
    private CloseableHttpClient getHttpClient() {
        return this.beanFactory.getBean(CloseableHttpClient.class);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
    
}
