package clients;
import org.json.JSONObject;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HttpClientExample implements IHttpClient, IAsyncHttpClient{
    @Override
    public void makeGet(int requestsCount, String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        for (int i = 0; i < requestsCount; i++) {
            HttpResponse<InputStream> response = client.send(request, BodyHandlers.ofInputStream());
            readInputData(response);
        }
    }

    @Override
    public void makePost(int requestsCount, String url, byte[] data) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofByteArray(data))
                .uri(URI.create(url))
                .build();
        for (int i = 0; i < requestsCount; i++) {
            HttpResponse<InputStream> response = client.send(request, BodyHandlers.ofInputStream());
            readInputData(response);
        }
    }

    @Override
    public void makeAsyncGet(int requestsCount, String url) throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        List<CompletableFuture<HttpResponse<InputStream>>> futuresResponses = new ArrayList<>();
        for (int i = 0; i < requestsCount; i++) {
            CompletableFuture<HttpResponse<InputStream>> future = client.sendAsync(request, BodyHandlers.ofInputStream());
            futuresResponses.add(future.thenApplyAsync((resp) -> {
                if (resp.statusCode() != 200) throw new RuntimeException();
                try {
                    readInputData(resp);
                } catch (IOException e) {
                    throw new RuntimeException();
                }
                return resp;
            }));
        }
        for (CompletableFuture<HttpResponse<InputStream>> futureResponse: futuresResponses) {
            futureResponse.get();
        }
    }

    @Override
    public void makeAsyncPost(int requestsCount, String url, byte[] data) throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofByteArray(data))
                .build();
        List<CompletableFuture<HttpResponse<InputStream>>> futuresResponses = new ArrayList<>();
        for (int i = 0; i < requestsCount; i++) {
            CompletableFuture<HttpResponse<InputStream>> future = client.sendAsync(request, BodyHandlers.ofInputStream());
            futuresResponses.add(future.thenApplyAsync((resp) -> {
                if (resp.statusCode() != 200) throw new RuntimeException();
                try {
                    readInputData(resp);
                } catch (IOException e) {
                    throw new RuntimeException();
                }
                return resp;
            }));
        }
        for (CompletableFuture<HttpResponse<InputStream>> futureResponse: futuresResponses) {
            futureResponse.get();
        }
    }

    private void readInputData(HttpResponse<InputStream> response) throws IOException {
        if (response.statusCode() != 200) { throw new RuntimeException(); }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()))) {
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
    }
}
