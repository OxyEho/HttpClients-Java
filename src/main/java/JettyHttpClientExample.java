import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.BufferingResponseListener;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.client.util.StringRequestContent;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class JettyHttpClientExample implements IHttpClient {

    private static final JSONObject data = new JSONObject().put("KEY1", "VALUE1").put("KEY2", "VALUE2");
    private static final ExecutorService executorService = Executors.newFixedThreadPool(8 + 1);

    @Override
    public void makeGetRequest(int requestsCount, String url) throws Exception {
        HttpClient client = new HttpClient();
        client.setExecutor(executorService);
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
    public void makePostRequest(int requestsCount, String url) throws Exception {
        HttpClient client = new HttpClient();
        client.setExecutor(executorService);
        client.start();
        final CountDownLatch latch = new CountDownLatch(requestsCount);
        for (int i = 0; i < requestsCount; i++)
            client.POST(url)
                    .body(new StringRequestContent("application/json", data.toString()))
                    .send(new BufferingResponseListener() {

                        @Override
                        public void onComplete(Result result) {
                            latch.countDown();
                        }
                        @Override
                        public void onFailure(Response response, Throwable failure) {
                            throw new RuntimeException();
                        }
                    });

        latch.await();
        client.stop();
    }
}
