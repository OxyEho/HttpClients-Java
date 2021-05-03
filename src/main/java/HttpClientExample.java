import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.*;
import java.nio.charset.StandardCharsets;

public class HttpClientExample implements IHttpClient{
    public void makeGetRequest(int requestsCount, String url) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NEVER)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        for (int i = 0; i < requestsCount; i++) {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException();
            }
        }
    }

    public void makePostRequest(int requestsCount, String url) throws URISyntaxException, IOException, InterruptedException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user", "VALUE1");
            jsonObject.put("pass", "VALUE2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        long startTime = System.currentTimeMillis();
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString(), StandardCharsets.UTF_8))
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .build();

        for (int i = 0; i < requestsCount; i++) {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException();
            }
//            System.out.println(response.body());
        }
    }
}
