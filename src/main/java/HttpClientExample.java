import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.*;

public class HttpClientExample {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://www.google.com/"))
                .build();
        for (int i = 0; i < 5; i++) {
            long startTime = System.currentTimeMillis();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(System.currentTimeMillis() - startTime);
//            System.out.println("Ответное сообщение: " + response);
//            System.out.println("Код: " + response.statusCode());
        }
//        System.out.println(response.body());
    }
}
