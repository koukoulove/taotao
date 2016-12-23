package com.taotao.test.http.httpClient;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientPostDemo {

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://www.baidu.com");
        CloseableHttpResponse resp = null;
        try {
            
            resp = httpClient.execute(httpPost);
            if(resp.getStatusLine().getStatusCode()==200){
                String content = EntityUtils.toString(resp.getEntity(),"UTF-8");
                System.out.println(content);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            try {
                if(resp != null){
                    resp.close();
                }
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
