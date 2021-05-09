import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpURLConnectionExample implements IHttpClient {

    private final JSONObject data = new JSONObject();

    public HttpURLConnectionExample() {
        try {
            data.put("KEY1", "VALUE1");
            data.put("KEY2", "VALUE2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void makeGetRequest(int requestsCount, String url) throws IOException {
        for (int i = 0; i < requestsCount; i++) {
            URL myUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException();
            }
            connection.disconnect();
        }
    }

    public void makePostRequest(int requestsCount, String url) throws IOException {
        for (int i = 0; i < requestsCount; i++) {
            URL myUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            try(BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))) {
                wr.write(data.toString());
            }
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException();
            }
            connection.disconnect();
        }
    }
}
