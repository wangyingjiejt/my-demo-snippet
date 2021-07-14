import common.UrlConstant;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Demo {


    public static void main(String[] args) {

        
        //创建一个HttpContext对象，用来保存Cookie
        HttpClientContext httpClientContext = HttpClientContext.create();
        //登录，获取cookie
        doLogin(httpClientContext);
        //从请求结果中获取Cookie，此时的Cookie已经带有登录信息了
        CookieStore cookieStore = getCookieStore(httpClientContext);
        //获取预约时间段列表
        getIntervalSettingList(cookieStore);
    }

    private static CookieStore getCookieStore(HttpClientContext httpClientContext) {
        CookieStore cookieStore = httpClientContext.getCookieStore();
//        cookieStore.addCookie(new BasicClientCookie("chaoxinguser", "1"));
//        cookieStore.addCookie(new BasicClientCookie("lv", "0"));
//        cookieStore.addCookie(new BasicClientCookie("oa_uid", "188664455"));
//        cookieStore.addCookie(new BasicClientCookie("oa_name", "%E7%8E%8B%E8%BF%8E%E6%8E%A5"));
//        cookieStore.addCookie(new BasicClientCookie("oa_deptid", "97561"));
//        cookieStore.addCookie(new BasicClientCookie("oa_enc", "109c541dfff85115fc04b21cfe8977f8"));
        return cookieStore;
    }

    private static void getIntervalSettingList(CookieStore cookieStore) {
        //构造一个带这个Cookie的HttpClient
        HttpClient newHttpClient = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            URI uri =new URIBuilder(UrlConstant.INTERVAL_SETTING_URL)
                    .setParameter("itemId", "3546")
                    .setParameter("reserveId", "2178").build();
            HttpGet httpGet = new HttpGet(uri);
            httpGet.addHeader("Cookie", cookieStore.toString());
            // 执行http请求
            response = newHttpClient.execute(httpGet);
            // 获得http响应体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // 响应的结果
                String content = EntityUtils.toString(entity, "UTF-8");
                System.out.println("content" + content);

            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private static void doLogin(HttpClientContext httpClientContext) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        CloseableHttpResponse response2 = null;
        HttpPost httpPost = new HttpPost(UrlConstant.LOGIN_URL);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("fid", "-1"));
        nameValuePairs.add(new BasicNameValuePair("uname", ""));
        String pwd = "";
        Base64 base64 = new Base64();
        String pwdEncode = base64.encodeToString(pwd.getBytes());
        System.out.println(pwdEncode);
        nameValuePairs.add(new BasicNameValuePair("password", pwdEncode));
//        nameValuePairs.add(new BasicNameValuePair("refer", ""));
//        nameValuePairs.add(new BasicNameValuePair("t", "true"));
        System.out.println("nameValuePairs:" + nameValuePairs);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // 执行http请求
            response = httpClient.execute(httpPost, httpClientContext);
            // 获得http响应体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // 响应的结果
                String content = EntityUtils.toString(entity, "UTF-8");
                System.out.println("content" + content);

            }
            Header[] headers = response.getHeaders("set-cookie");
            for (Header header : headers) {
                System.out.println(header.toString());
            }

//            System.out.println("--------------");
//            HttpGet httpGet= new HttpGet(UrlConstant.INTERVAL_SETTING_URL);
//            response2 = httpClient.execute(httpGet, httpClientContext);
//
//            HttpEntity entity2 = response2.getEntity();
//            if (entity != null) {
//                // 响应的结果
//                String content = EntityUtils.toString(entity2, "UTF-8");
//                System.out.println("content" + content);
//
//            }
//            Header[] headers2 = response.getHeaders("set-cookie");
//            for (Header header : headers2) {
//                System.out.println(header.toString());
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
