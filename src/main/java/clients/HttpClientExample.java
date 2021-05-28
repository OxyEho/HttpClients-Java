package clients;

import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HttpClientExample implements IHttpClient {
    private static final JSONObject data = new JSONObject().put("KEY1", "VALUE1").put("KEY2", "VALUE2");
    public boolean makeGetRequest(int requestsCount, String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        for (int i = 0; i < requestsCount; i++) {
            HttpResponse<InputStream> response = client.send(request, BodyHandlers.ofInputStream());
            readInputData(response);
        }
        return true;
    }

    public void a(String url) throws ExecutionException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        CompletableFuture<HttpResponse<String>> future = client.sendAsync(request, BodyHandlers.ofString());
        future.thenApply(HttpResponse::body).thenAccept(System.out::println).get();
    }

    public boolean makePostRequest(int requestsCount, String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(data.toString(), StandardCharsets.UTF_8))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .build();

        for (int i = 0; i < requestsCount; i++) {
            HttpResponse<InputStream> response = client.send(request, BodyHandlers.ofInputStream());
            readInputData(response);
        }
        return true;
    }

    private void readInputData(HttpResponse<InputStream> response) throws IOException {
        if (response.statusCode() != 200) {
            throw new RuntimeException();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()))) {
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
    }
}
