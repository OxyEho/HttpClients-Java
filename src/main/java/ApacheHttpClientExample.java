import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ApacheHttpClientExample {
    public static void main(String[] args) throws IOException {
        makeGetRequest();
//        makePostRequest();
    }

    public static void makeGetRequest() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost/");
        for (int i = 0; i < 5; i++){
            long startTime = System.currentTimeMillis();
            CloseableHttpResponse httpResponse = httpclient.execute(httpget);
            System.out.println(System.currentTimeMillis() - startTime);
            httpResponse.close();
        }
        httpclient.close();
    }

    public static void makePostRequest() throws IOException {
        CloseableHttpClient httpclient1 = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://postman-echo.com/post");
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("username", "abc"));
        urlParameters.add(new BasicNameValuePair("password", "123"));
        urlParameters.add(new BasicNameValuePair("custom", "secret"));

        httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
        for (int i = 0; i < 5; i++){
            long startTime = System.currentTimeMillis();
            CloseableHttpResponse httpResponse = httpclient1.execute(httpPost);
            var a = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line;
            while ((line = a.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println(System.currentTimeMillis() - startTime);
            httpResponse.close();
        }
        httpclient1.close();
    }
}
