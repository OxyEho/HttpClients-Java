import clients.*;
import org.apache.commons.lang3.time.StopWatch;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
//        HttpClient client = HttpClient.newBuilder()
//                .version(HttpClient.Version.HTTP_1_1)
//                .build();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("http://localhost:8080/Server_war/json_test"))
//                .method("GET", HttpRequest.BodyPublishers.ofByteArray("aaaaaa".getBytes()))
//                .build();
//        var a = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
//        System.out.println(new String(a.body()));
//        IAsyncHttpClient client = new AsyncHttpClientExample();
//        IHttpClient client = new HttpURLConnectionExample();
//        client.makeGet(10, "http://localhost:8080/Server_war/json_test");

//        client.makeAsyncPost(10, "http://localhost:8080/Server_war/json_test", data);
//        for (int i = 0; i < 10; i++) {
//            long startTime1 = System.currentTimeMillis();
//            IAsyncHttpClient client = new AsyncHttpClientExample();
//            client.makeAsyncPost(10, "http://localhost:8080/Server_war/json_test", data);
//            System.out.println(System.currentTimeMillis() - startTime1);
//        }
//        System.out.println("!!!!!!!!!!!");
//        for (int i = 0; i < 10; i++) {
//            long startTime = System.currentTimeMillis();
//            IAsyncHttpClient client1 = new JettyHttpClientExample();
//            client1.makeAsyncPost(10, "http://localhost:8080/Server_war/json_test", data);
//            System.out.println(System.currentTimeMillis() - startTime);
//        }
//        IAsyncHttpClient client = new AsyncHttpClientExample();
//        client.makeAsyncPost(10, "http://localhost:8080/Server_war/json_test" , data);
//        IAsyncHttpClient client = new OkHttpExample();
//        client.makeAsyncGet(10, "http://localhost:8080/Server_war/json_test");
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

//    private static void testHttpClients(List<IHttpClient> clients, String method, int requestsCount, String url) throws Exception {
//        System.out.println(method + " requests count: " + requestsCount);
//        for (IHttpClient client: clients) {
//            long milliseconds = testHttpClient(client, requestsCount, url, method, 30);
//            System.out.println(milliseconds + " : " + client.getClass().getName());
//        }
//    }

//    private static long testHttpClient(IHttpClient client, int requestsCount,
//                                       String url, String method, int retryCount) throws Exception {
//        if (method.equals("POST")) {
//            client.makePostRequest(1, url); //прогревочный запуск
//            long bestTime = 10000000;
//            for (int i = 0; i < retryCount; i++) {
//                StopWatch watch = new StopWatch();
//                watch.start();
//                client.makePostRequest(requestsCount, url);
//                watch.stop();
//                if (bestTime > watch.getTime())
//                    bestTime = watch.getTime();
//            }
//            return bestTime;
//        } else {
//            client.makeGetRequest(10, url); //прогревочный запуск
//            long bestTime = 10000000;
//            for (int i = 0; i < retryCount; i++) {
//                StopWatch watch = new StopWatch();
//                watch.start();
//                client.makeGetRequest(requestsCount, url);
//                watch.stop();
//                if (bestTime > watch.getTime())
//                    bestTime = watch.getTime();
//            }
//            return bestTime;
//        }
//    }
}
