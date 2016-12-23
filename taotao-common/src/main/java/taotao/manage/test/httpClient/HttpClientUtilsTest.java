package taotao.manage.test.httpClient;

import org.apache.http.impl.client.CloseableHttpClient;

import com.taotao.common.utils.httpClient.HCB;
import com.taotao.common.utils.httpClient.HttpClientUtil;
import com.taotao.common.utils.httpClient.HttpConfig;
import com.taotao.common.utils.httpClient.HttpMethods;
import com.taotao.common.utils.httpClient.HttpProcessException;

public class HttpClientUtilsTest {
    public static void main(String[] args) throws HttpProcessException {
        get();
    }

//        checheche.crowdfundinglist=http://exchange.tjs.yfycyq.com/v1/crowdfundinglist
//        checheche.crowdfundingdetail=http://exchange.tjs.yfycyq.com/v1/crowdfundingdetail
//        tjs.ipPrefix.url=https://114.55.137.252/fund-app/consumerFinance/v1/validateTokenAndMobile
//        tjs.account.url=https://114.55.137.252/fund-app/bank/v1/get/mobile/signBank/list
//        tjs.available.url=
    private static final String verify="https://114.55.137.252/fund-app/crowdFunding/available/verify?mobile=11111";
    private static final String itemlist="http://localhost:8081/rest/item?page=1&rows=10";
    private static final String itemCatlist="http://localhost:8081/rest/item/param/3";
    
    public static void get() throws HttpProcessException{
//       String resp = HttpClientUtil.get(HttpConfig.custom().url(verify));
//       System.out.println(resp);
       String itemlistresp = HttpClientUtil.get(HttpConfig.custom().url(itemlist));
       
       
       
       
       System.out.println(itemlistresp);
       String itemCatResp = HttpClientUtil.get(HttpConfig.custom().url(itemCatlist));
       System.out.println(itemCatResp);
    }
    
    public static void post() throws HttpProcessException{
        String resp = HttpClientUtil.send(HttpConfig.custom().url(verify).method(HttpMethods.POST));
        System.out.println(resp);
    }
}
