import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class Demo {


    public static void main(String[] args) {
        String url = "";
        String refer = "";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("fid", "-1"));
        nameValuePairs.add(new BasicNameValuePair("uname", "*"));
        String pwd = "*";
        Base64 base64 = new Base64();
        String pwdEncode = base64.encodeToString(pwd.getBytes());
        System.out.println(pwdEncode);
        nameValuePairs.add(new BasicNameValuePair("password", pwdEncode));
        nameValuePairs.add(new BasicNameValuePair("refer", refer));
        nameValuePairs.add(new BasicNameValuePair("t", "true"));
        System.out.println("nameValuePairs:" + nameValuePairs);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // 执行http请求
            response = httpClient.execute(httpPost);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
