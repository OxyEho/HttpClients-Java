import clients.*;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
//        String url = "http://localhost:8080/Server_war/json_test";
//        IHttpClient apacheHttp = new ApacheHttpClientExample();
//        IHttpClient okHttp = new OkHttpExample();
//        HttpClientExample httpClient11 = new HttpClientExample();
//        IHttpClient httpConnection = new HttpURLConnectionExample();
//        IHttpClient asyncClient = new AsyncHttpClientExample();
//        IHttpClient jettyClient = new JettyHttpClientExample();
//        List<IHttpClient> syncClients = new ArrayList<>();
//        List<IHttpClient> asyncClients = new ArrayList<>();
//        syncClients.add(apacheHttp);
//        syncClients.add(okHttp);
//        syncClients.add(httpClient11);
//        syncClients.add(httpConnection);
//        testHttpClient(jettyClient, 1, "http://localhost:8080/Server_war/json_test", "GET", 1);
//        httpClient11.a("http://localhost:8080/Server_war/json_test");
//        asyncClients.add(jettyClient);
//        asyncClients.add(asyncClient);
//        testHttpClients(asyncClients, "GET", 100000, url);
////        testHttpClients(asyncClients, "POST", 100000, url);
//        long time = testHttpClient(okHttp, 100000, url, "GET", 5);
//        System.out.println(time);
//        long timePost = testHttpClient(okHttp, 100000, url, "POST", 5);
//        System.out.println(timePost);
////        testHttpClients(syncClients, "GET", 1000, url);
////        testHttpClients(syncClients, "POST", 1000, url);
////        testHttpClients(syncClients, "GET", 100000, url);
////        testHttpClients(syncClients, "POST", 100000, url);
//        System.exit(0);
    }

    private static void testHttpClients(List<IHttpClient> clients, String method, int requestsCount, String url) throws Exception {
        System.out.println(method + " requests count: " + requestsCount);
        for (IHttpClient client: clients) {
            long milliseconds = testHttpClient(client, requestsCount, url, method, 30);
            System.out.println(milliseconds + " : " + client.getClass().getName());
        }
    }

    private static long testHttpClient(IHttpClient client, int requestsCount,
                                       String url, String method, int retryCount) throws Exception {
        if (method.equals("POST")) {
            client.makePostRequest(1, url); //прогревочный запуск
            long bestTime = 10000000;
            for (int i = 0; i < retryCount; i++) {
                StopWatch watch = new StopWatch();
                watch.start();
                client.makePostRequest(requestsCount, url);
                watch.stop();
                if (bestTime > watch.getTime())
                    bestTime = watch.getTime();
            }
            return bestTime;
        } else {
            client.makeGetRequest(10, url); //прогревочный запуск
            long bestTime = 10000000;
            for (int i = 0; i < retryCount; i++) {
                StopWatch watch = new StopWatch();
                watch.start();
                client.makeGetRequest(requestsCount, url);
                watch.stop();
                if (bestTime > watch.getTime())
                    bestTime = watch.getTime();
            }
            return bestTime;
        }
    }
}
