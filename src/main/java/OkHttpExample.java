import okhttp3.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OkHttpExample {
    public static void main(String[] args) throws IOException {
//        makePostRequest();
        makeGetRequest();
    }

    public static void makeGetRequest() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost/my.html")
                .build();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {

            Call call = client.newCall(request);
            Response response = call.execute();
            if (response.code() != 200) {
                throw new RuntimeException();
            }
//            BufferedReader buf = new BufferedReader(new InputStreamReader(response.body().byteStream()));
//            String line;
//            while ((line = buf.readLine()) != null) {
//                System.out.println(line + "\n");
//            }
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }

    public static void makePostRequest() throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("username", "test")
                .add("password", "test")
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://postman-echo.com/post")
                .post(formBody)
                .build();
        for (int i = 0; i < 5; i++) {
            long startTime = System.currentTimeMillis();
            Call call = client.newCall(request);
            Response response = call.execute();
            System.out.println(System.currentTimeMillis() - startTime);
            BufferedReader buf = new BufferedReader(new InputStreamReader(response.body().byteStream()));
            String line;
            while ((line = buf.readLine()) != null) {
                System.out.println(line + "\n");
            }
        }
    }
}
