import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpURLConnectionExample implements IHttpClient{
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
            long startTime = System.currentTimeMillis();
            String urlParameters = "user=test&password=test";
            URL myUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            try(BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))) {
                wr.write(urlParameters);
            }
            System.out.println(connection.getResponseMessage());
            System.out.println(System.currentTimeMillis() - startTime);
            BufferedReader buf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = buf.readLine()) != null) {
                System.out.println(line + "\n");
            }
            connection.disconnect();
        }
    }
}
