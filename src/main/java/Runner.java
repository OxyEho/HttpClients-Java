import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.net.URISyntaxException;

public class Runner {
    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        IHttpClient apacheHttp = new ApacheHttpClientExample();
        IHttpClient okHttp = new OkHttpExample();
        IHttpClient httpClient11 = new HttpClientExample();
        httpClient11.makeGetRequest(1, "http://localhost:8080/Server_war/hello");
        okHttp.makeGetRequest(1, "http://localhost:8080/Server_war/hello");
//        apacheHttp.makePostRequest(1, "http://localhost:8080/Server_war/hello");
        StopWatch watch = new StopWatch();
        watch.start();
        okHttp.makeGetRequest(100, "http://localhost:8080/Server_war/hello");
        watch.stop();
        System.out.println(watch.toString() + "  Ok http");
        watch.reset();
        watch.start();
        httpClient11.makeGetRequest(100, "http://localhost:8080/Server_war/hello");
        watch.stop();
        System.out.println(watch.toString() + " HttpClient");

//        System.out.println("Apache HTTP");
//        long apacheTime = apacheHttp.makePostRequest(1, "http://localhost:8080/Server_war/hello");
//        System.out.println(apacheTime);
//        long resTime = System.currentTimeMillis();
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("HttpClient Java 11 1");
//                long httpClientTime = 0;
//                try {
//                    httpClientTime = httpClient11.makePostRequest(1000, "http://localhost:8080/Server_war/hello");
//                } catch (URISyntaxException | IOException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(httpClientTime);
//            }
//        });
//
//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("HttpClient Java 11 2");
//                long httpClientTime = 0;
//                try {
//                    httpClientTime = httpClient11.makePostRequest(1000, "http://localhost:8080/Server_war/hello");
//                } catch (URISyntaxException | IOException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(httpClientTime);
//            }
//        });
//        thread1.start();
//        thread2.start();
//        thread1.join();
//        thread2.join();
//        System.out.println(System.currentTimeMillis() - resTime);
    }
}
