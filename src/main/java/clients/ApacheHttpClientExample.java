package clients;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ApacheHttpClientExample implements IHttpClient {
    private static final String url = "http://localhost:8080/Server_war/json_test";
    private static final CloseableHttpClient client = HttpClients.createDefault();
    private static final HttpGet getRequest = new HttpGet(url);
    private final HttpPost postRequest;
    public ApacheHttpClientExample(byte[] data) {
        ByteArrayEntity requestEntity = new ByteArrayEntity(data);
        postRequest = new HttpPost(url);
        postRequest.setEntity(requestEntity);
    }
    @Override
    public void makeGet() throws IOException {
        HttpResponse httpResponse = client.execute(getRequest);
        readInputData(httpResponse);
        getRequest.releaseConnection();
    }
    @Override
    public void makePost() throws IOException {
        HttpResponse httpResponse = client.execute(postRequest);
        readInputData(httpResponse);
        postRequest.releaseConnection();
    }
    private void readInputData(HttpResponse httpResponse) throws IOException {
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
    @Override
    public void stop() throws IOException {
        client.close();
    }
}
