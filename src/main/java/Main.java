import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newCachedThreadPool();
//        long startTime1 = System.currentTimeMillis();
//        Thread.sleep(1000);
//        System.out.println(System.currentTimeMillis() - startTime1);
//        String[] strings = new String[]{"https://yandex.ru/", "https://google.com/"};

        for (int i = 0; i < 5; i++) {
            long startTime = System.currentTimeMillis();
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://postman-echo.com/get"))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            System.out.println(System.currentTimeMillis() - startTime);
            startTime = System.currentTimeMillis();
        }
//        long startTime1 = System.currentTimeMillis();
//        URL myUrl = new URL("https://postman-echo.com/get");
//        HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
//        connection.getResponseMessage();
//        connection.getResponseCode();
//        System.out.println(System.currentTimeMillis() - startTime1);
//        startTime1 = System.currentTimeMillis();
//        myUrl = new URL("https://postman-echo.com/get");
//        connection = (HttpURLConnection) myUrl.openConnection();
//        URL myUrl1 = new URL("https://google.com/");
//        HttpURLConnection connection1 = (HttpURLConnection) myUrl1.openConnection();
//        connection.getResponseMessage();
//        connection.getResponseCode();
//        System.out.println(System.currentTimeMillis() - startTime1);
//        timer(() -> {
//            try {
//                httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
    }

    private static void timer(Runnable method) {
        long time = System.currentTimeMillis();
        method.run();
        System.out.println(System.currentTimeMillis() - time);
    }
}
