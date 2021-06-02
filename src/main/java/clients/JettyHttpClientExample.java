package clients;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.BufferingResponseListener;
import org.eclipse.jetty.client.util.BytesRequestContent;;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class JettyHttpClientExample implements IAsyncHttpClient, IHttpClient {
    @Override
    public void makeAsyncGet(int requestsCount, String url) throws Exception {
        HttpClient client = new HttpClient();
        client.start();
        final CountDownLatch latch = new CountDownLatch(requestsCount);
        for (int i = 0; i < requestsCount; i++)
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

        latch.await();
        client.stop();
    }
    @Override
    public void makeAsyncPost(int requestsCount, String url, byte[] data) throws Exception {
        HttpClient client = new HttpClient();
        client.start();
        final CountDownLatch latch = new CountDownLatch(requestsCount);
        for (int i = 0; i < requestsCount; i++)
            client.POST(url)
                    .body(new BytesRequestContent(data))
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

        latch.await();
        client.stop();
    }
    @Override
    public void makeGet(int requestsCount, String url) throws Exception {
        HttpClient client = new HttpClient();
        client.start();
        for (int i = 0; i < requestsCount; i++) {
            ContentResponse contentResponse = client.GET(url);
            if (contentResponse.getStatus() != 200) { throw new RuntimeException(); }
            String strResponse = contentResponse.getContentAsString();
        }
        client.stop();
    }
    @Override
    public void makePost(int requestsCount, String url, byte[] data) throws Exception {
        HttpClient client = new HttpClient();
        client.start();
        for (int i = 0; i < requestsCount; i++) {
            Request request = client.POST(url);
            request.body(new BytesRequestContent(data));
            ContentResponse contentResponse = request.send();
            if (contentResponse.getStatus() != 200) { throw new RuntimeException(); }
            String strResponse = contentResponse.getContentAsString();
        }
        client.stop();
    }
}
