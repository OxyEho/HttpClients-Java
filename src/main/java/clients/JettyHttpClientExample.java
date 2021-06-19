package clients;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.BufferingResponseListener;
import org.eclipse.jetty.client.util.BytesRequestContent;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class JettyHttpClientExample implements IAsyncHttpClient, IHttpClient {
    private static final String url = "http://localhost:8080/Server_war/json_test";
    private static final HttpClient client = new HttpClient();
    private final byte[] data;
    public JettyHttpClientExample(byte[] data) {
        this.data = data;
        try {
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void makeAsyncGet(int requestsCount) throws Exception {
        final CountDownLatch latch = new CountDownLatch(requestsCount);
        for (int i = 0; i < requestsCount; i++) {
            client.newRequest(url)
                    .send(new BufferingResponseListener() {
                        @Override
                        public void onComplete(Result result) {
                            latch.countDown();
                        }
                        @Override
                        public void onContent(Response response, ByteBuffer content) {
                            String data = StandardCharsets.UTF_8.decode(content).toString();
                        }
                        @Override
                        public void onFailure(Response response, Throwable failure) {
                            throw new RuntimeException();
                        }
                    });
        }
        latch.await();
    }
    @Override
    public void makeAsyncPost(int requestsCount) throws Exception {
        final CountDownLatch latch = new CountDownLatch(requestsCount);
        for (int i = 0; i < requestsCount; i++) {
            client.POST(url).body(new BytesRequestContent(data)).send(new BufferingResponseListener() {
                @Override
                public void onComplete(Result result) {
                    latch.countDown();
                }
                @Override
                public void onContent(Response response, ByteBuffer content) {
                    String data = StandardCharsets.UTF_8.decode(content).toString();
                }
                @Override
                public void onFailure(Response response, Throwable failure) {
                    throw new RuntimeException();
                }
            });
        }
        latch.await();
    }
    @Override
    public void makeGet() throws Exception {
        ContentResponse contentResponse = client.GET(url);
        if (contentResponse.getStatus() != 200) { throw new RuntimeException(); }
        String strResponse = contentResponse.getContentAsString();
    }
    @Override
    public void makePost() throws Exception {
        Request postRequest = client.POST(url);
        postRequest.body(new BytesRequestContent(data));
        ContentResponse contentResponse = postRequest.send();
        if (contentResponse.getStatus() != 200) { throw new RuntimeException(); }
        String strResponse = contentResponse.getContentAsString();
    }
    @Override
    public void stop() throws Exception { client.stop(); }

    @Override
    public void stopAsync() throws Exception { client.stop(); }
}
