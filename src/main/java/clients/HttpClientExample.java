package clients;
import okhttp3.ResponseBody;

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

public class HttpClientExample implements IHttpClient, IAsyncHttpClient {
    private final static String url = "http://localhost:8080/Server_war/json_test";
    private final byte[] data;

    private final static HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    private static final HttpRequest getRequest = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

    private final HttpRequest postRequest;

    public HttpClientExample(byte[] data) {
        this.data = data;
        postRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofByteArray(data))
                .build();
    }

    @Override
    public void makeGet() throws IOException, InterruptedException {
        HttpResponse<InputStream> response = client.send(getRequest, BodyHandlers.ofInputStream());
        readInputData(response);
    }
    @Override
    public void makePost() throws IOException, InterruptedException {
        HttpResponse<InputStream> response = client.send(postRequest, BodyHandlers.ofInputStream());
        readInputData(response);
    }

    @Override
    public void makeAsyncGet(int requestsCount) throws Exception {
        List<CompletableFuture<HttpResponse<InputStream>>> futuresResponses = new ArrayList<>();
        for (int i = 0; i < requestsCount; i++) {
            CompletableFuture<HttpResponse<InputStream>> future = client.sendAsync(getRequest, BodyHandlers.ofInputStream());
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
    public void makeAsyncPost(int requestsCount) throws Exception {
        List<CompletableFuture<HttpResponse<InputStream>>> futuresResponses = new ArrayList<>();
        for (int i = 0; i < requestsCount; i++) {
            CompletableFuture<HttpResponse<InputStream>> future = client.sendAsync(postRequest, BodyHandlers.ofInputStream());
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
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(response.body()))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = buf.readLine()) != null) {
                builder.append(line);
            }
        }
    }
}
