import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionExample implements IHttpClient {
    private static final JSONObject data = new JSONObject().put("KEY1", "VALUE1").put("KEY2", "VALUE2");
    public void makeGetRequest(int requestsCount, String url) throws IOException {
        for (int i = 0; i < requestsCount; i++) {
            URL myUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            connection.setDoInput(true);
            readInputData(connection);
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
            connection.setDoInput(true);
            try(BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))) {
                wr.write(data.toString());
            }
            readInputData(connection);
            connection.disconnect();
        }
    }

    private void readInputData(HttpURLConnection connection) throws IOException{
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        if (connection.getResponseCode() != 200) {
            throw new RuntimeException();
        }
    }
}
