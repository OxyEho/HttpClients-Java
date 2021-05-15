import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpURLConnectionExample implements IHttpClient {

    private static final JSONObject data = new JSONObject().put("KEY1", "VALUE1").put("KEY2", "VALUE2");

    public HttpURLConnectionExample() {
        try {
            data.put("KEY1", "VALUE1");
            data.put("KEY2", "VALUE2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void makeGetRequest(int requestsCount, String url) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < requestsCount; i++) {
            URL myUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            connection.setDoInput(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
//            System.out.println(stringBuilder.toString());
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException();
            }
            reader.close();
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

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException();
            }
            connection.disconnect();
        }
    }
}
