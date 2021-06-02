package clients;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ApacheHttpClientExample implements IHttpClient {
    @Override
    public void makeGet(int requestsCount, String url) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpget = new HttpGet(url);
            for (int i = 0; i < requestsCount; i++) {
                try (CloseableHttpResponse httpResponse = httpClient.execute(httpget)) {
                    readInputData(httpResponse);
                }
                httpget.releaseConnection();
            }
        }
    }
    @Override
    public void makePost(int requestsCount, String url , byte[] data) throws IOException {
        ByteArrayEntity requestEntity = new ByteArrayEntity(data, ContentType.APPLICATION_JSON);
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(requestEntity);
            for (int i = 0; i < requestsCount; i++) {
                try (CloseableHttpResponse httpResponse = httpclient.execute(httpPost)) {
                    readInputData(httpResponse);
                }
                httpPost.releaseConnection();
            }
        }
    }
    private void readInputData(CloseableHttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusLine().getStatusCode() != 200) { throw new RuntimeException(); }
        HttpEntity entity = httpResponse.getEntity();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        EntityUtils.consume(entity);
    }
}
