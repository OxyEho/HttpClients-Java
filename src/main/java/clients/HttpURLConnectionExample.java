package clients;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionExample implements IHttpClient {
    private static final String url = "http://localhost:8080/Server_war/json_test";
    private final byte[] data;
    public HttpURLConnectionExample(byte[] data) {
        this.data = data;
    }
    @Override
    public void makeGet() throws IOException {
        URL myUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);
        readInputData(connection);
    }
    @Override
    public void makePost() throws IOException {
        URL myUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        try(OutputStream wr = connection.getOutputStream()) {
            wr.write(data);
            wr.flush();
        }
        readInputData(connection);
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