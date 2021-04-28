import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class HttpClientExample {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        makeGetRequest();
//        makePostRequest();
    }

    public static void makeGetRequest() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/"))
                .build();
        for (int i = 0; i < 5; i++) {
            long startTime = System.nanoTime();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(System.nanoTime() - startTime);
        }
    }

    public static void makePostRequest() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString("user=test&password=test", StandardCharsets.UTF_8))
                .uri(new URI("https://postman-echo.com/post"))
                .header("Content-Type", "application/x-www-form-urlencoded")
//                .header("Content-type", "application/json")
                .build();
        for (int i = 0; i < 5; i++) {
            long startTime = System.currentTimeMillis();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(System.currentTimeMillis() - startTime);
            System.out.println(response.body());
        }
    }
}
