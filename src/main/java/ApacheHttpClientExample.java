
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.text.html.parser.Entity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ApacheHttpClientExample implements IHttpClient {

    private final JSONObject data = new JSONObject();

    public  ApacheHttpClientExample() {
        try {
            data.put("user", "VALUE1");
            data.put("pass", "VALUE2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void makeGetRequest(int requestsCount, String url) throws IOException {
//        var httpClient = HttpClients.createMinimal();
//        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setConnectionManager(cm)
//                .build();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
//        HttpContext context = HttpClientContext.create();
        for (int i = 0; i < requestsCount; i++){
            CloseableHttpResponse httpResponse = httpClient.execute(httpget);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException();
            }
            HttpEntity entity = httpResponse.getEntity();
            EntityUtils.consume(entity);
            httpResponse.close();
        }
        httpClient.close();
    }

    public void makePostRequest(int requestsCount, String url) throws IOException {
        StringEntity requestEntity = new StringEntity(
                data.toString(),
                ContentType.APPLICATION_JSON);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(requestEntity);
        for (int i = 0; i < requestsCount; i++){
            CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException();
            }
            HttpEntity entity = httpResponse.getEntity();
            EntityUtils.consume(entity);
            httpResponse.close();
        }
        httpclient.close();
    }
}
