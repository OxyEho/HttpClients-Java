package clients;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionExample implements IHttpClient {
    public void makeGet(int requestsCount, String url) throws IOException {
        for (int i = 0; i < requestsCount; i++) {
            URL myUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            readInputData(connection);
        }
    }

    public void makePost(int requestsCount, String url , byte[] data) throws IOException {
        for (int i = 0; i < requestsCount; i++) {
            URL myUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            try(OutputStream wr = connection.getOutputStream()) {
                wr.write(data);
            }
            readInputData(connection);
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