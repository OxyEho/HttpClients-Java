import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) throws Exception {
        IHttpClient apacheHttp = new ApacheHttpClientExample();
        IHttpClient okHttp = new OkHttpExample();
        IHttpClient httpClient11 = new HttpClientExample();
        IHttpClient httpConnection = new HttpURLConnectionExample();
        IHttpClient asyncClient = new AsyncHttpClientExample();
        IHttpClient jettyClient = new JettyHttpClientExample();
        testHttpClient(jettyClient, 10000, "http://localhost:8080/Server_war/json_test", "POST", 5);
        System.exit(0);
    }

    private static void testHttpClient(IHttpClient client, int requestsCount,
                                       String url, String method, int retryCount) throws Exception {
        if (method.equals("POST")) {
            for (int i = 0; i < retryCount; i++) {
                client.makePostRequest(1, url); //прогревочный запуск
                StopWatch watch = new StopWatch();
                watch.start();
                client.makePostRequest(requestsCount, url);
                watch.stop();
                System.out.println(watch.toString() + " " + client.getClass().getName());
            }
        } else {

            for (int i = 0; i < retryCount; i++) {
                client.makeGetRequest(10, url); //прогревочный запуск
                StopWatch watch = new StopWatch();
                watch.start();
                client.makeGetRequest(requestsCount, url);
                watch.stop();
                System.out.println(watch.toString() + " " + client.getClass().getName());
            }
        }
    }
}
