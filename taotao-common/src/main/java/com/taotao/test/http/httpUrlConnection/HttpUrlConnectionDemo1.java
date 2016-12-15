package com.taotao.test.http.httpUrlConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUrlConnectionDemo1 {

    private static final String chechecheList = "http://exchange.tjs.yfycyq.com/v1/crowdfundinglist";
    
    public static void main(String[] args) {
        get();
    }
    
    public static void get(){
        URL url = null;
        HttpURLConnection openConnection = null;
        try {
            url = new URL(chechecheList);
            openConnection = (HttpURLConnection)url.openConnection();
//            openConnection = new HttpURLConnection(url);
            openConnection.setUseCaches(false);
            //openConnection.connect();
            openConnection.setUseCaches(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(openConnection.getInputStream(), "UTF-8"));
            StringWriter writer = new StringWriter();
            
            char[] chars = new char[256];
            int count = 0;
            while ((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }
            System.out.println(writer.toString());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            
        }
        
        
    }
    public void post(){
        
    }
}
