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
//        testHttpClient(okHttp, 100000, "http://localhost:8080/Server_war/json_test", "GET", 1);
//        testHttpClient(httpClient11, 100000, "http://localhost:8080/Server_war/json_test", "POST", 1);
//        testHttpClient(apacheHttp, 100000, "http://localhost:8080/Server_war/json_test", "POST", 10);
//        System.out.println();
//        testHttpClient(okHttp, 100000, "http://localhost:8080/Server_war/json_test", "GET", 10);
//        System.out.println();
//        testHttpClient(httpClient11, 100000, "http://localhost:8080/Server_war/json_test", "POST", 10);
//        System.out.println();
//        testHttpClient(httpConnection, 1000, "http://localhost:8080/Server_war/json_test", "POST", 5);
        testHttpClient(asyncClient, 100000, "http://localhost:8080/Server_war/json_test", "GET", 5);
//        System.out.println();
//        testHttpClient(jettyClient, 100000, "http://localhost:8080/Server_war/json_test", "POST", 5);
        System.exit(0);
    }

    private static void testHttpClient(IHttpClient client, int requestsCount,
                                       String url, String method, int retryCount) throws Exception {
        if (method.equals("POST")) {
            client.makePostRequest(1, url); //прогревочный запуск
            for (int i = 0; i < retryCount; i++) {

                StopWatch watch = new StopWatch();
                watch.start();
                client.makePostRequest(requestsCount, url);
                watch.stop();
                System.out.println(watch.toString() + " " + client.getClass().getName());
            }
        } else {
            client.makeGetRequest(1, url); //прогревочный запуск
            for (int i = 0; i < retryCount; i++) {

                StopWatch watch = new StopWatch();
                watch.start();
                client.makeGetRequest(requestsCount, url);
                watch.stop();
                System.out.println(watch.toString() + " " + client.getClass().getName());
            }
        }
    }
}
