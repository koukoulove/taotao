package com.taotao.test.http.httpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientGetDemo {

    public static void main(String[] args) {
        CloseableHttpResponse resp = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URI uri = new URIBuilder(
                    "http://120.27.150.250:8082/fund-app/user/upload/img")
             .setParameter("token", "123").setParameter("img", "123").setParameter("imgType", "123").build();
            
            HttpGet httpGet = new HttpGet(uri);
            resp = httpClient.execute(httpGet);
            if(resp.getStatusLine().getStatusCode()==200){
                String content = EntityUtils.toString(resp.getEntity(),"UTF-8");
                System.out.println(content);
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
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
