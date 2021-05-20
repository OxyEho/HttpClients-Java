import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.*;
import java.nio.charset.StandardCharsets;

public class HttpClientExample implements IHttpClient {

    private static final JSONObject data = new JSONObject().put("KEY1", "VALUE1").put("KEY2", "VALUE2");

    public void makeGetRequest(int requestsCount, String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        for (int i = 0; i < requestsCount; i++) {
            HttpResponse<InputStream> response = client.send(request, BodyHandlers.ofInputStream());

            if (response.statusCode() != 200) {
                throw new RuntimeException();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
        }
    }

    public void makePostRequest(int requestsCount, String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(data.toString(), StandardCharsets.UTF_8))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .build();

        for (int i = 0; i < requestsCount; i++) {
            HttpResponse<InputStream> response = client.send(request, BodyHandlers.ofInputStream());
            if (response.statusCode() != 200) {
                throw new RuntimeException();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
        }
    }
}
