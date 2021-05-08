import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.BufferingResponseListener;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.client.util.StringRequestContent;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class JettyHttpClientExample {
    public static void main(String[] args) throws Exception {
        makePostRequest(1);
        StopWatch watch = new StopWatch();
        watch.start();
        makePostRequest(10000);
        watch.stop();
        System.out.println(watch.toString());
    }

    public static void makeGetRequest(int requestsCount) throws Exception {
        HttpClient client = new HttpClient();
        client.start();
        final CountDownLatch latch = new CountDownLatch(requestsCount);
        for (int i = 0; i < requestsCount; i++)
            client.newRequest("http://localhost:8080/Server_war/json")
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

    public static void makePostRequest(int requestsCount) throws Exception {
        JSONObject data = new JSONObject();
        try {
            data.put("user", "VALUE1");
            data.put("pass", "VALUE2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient client = new HttpClient();
        client.start();
        final CountDownLatch latch = new CountDownLatch(requestsCount);
        for (int i = 0; i < requestsCount; i++)
            client.POST("http://localhost:8080/Server_war/json")
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
