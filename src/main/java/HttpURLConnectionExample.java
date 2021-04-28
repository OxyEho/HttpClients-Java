import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpURLConnectionExample {
    public static void main(String[] args) throws IOException {
        makeGetRequests();
//        makePostRequests();
    }

    private static void makeGetRequests() throws IOException {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {

            URL myUrl = new URL("http://localhost/");
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            connection.getResponseMessage();
            connection.disconnect();
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }

    private static void makePostRequests() throws IOException {
        for (int i = 0; i < 5; i++) {
            long startTime = System.currentTimeMillis();
            String urlParameters = "user=test&password=test";
            URL myUrl = new URL("https://postman-echo.com/post");
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
