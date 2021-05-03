import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OkHttpExample implements IHttpClient {

    private final JSONObject data = new JSONObject();

    public OkHttpExample() {
        try {
            data.put("KEY1", "VALUE1");
            data.put("KEY2", "VALUE2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void makeGetRequest(int requestsCount, String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        for (int i = 0; i < requestsCount; i++) {
            Call call = client.newCall(request);
            Response response = call.execute();
            if (response.code() != 200) {
                throw new RuntimeException();
            }
            response.close();
        }
    }

    public void makePostRequest(int requestsCount, String url) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(data.toString(), JSON);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        for (int i = 0; i < requestsCount; i++) {
            Call call = client.newCall(request);
            Response response = call.execute();
            if (response.code() != 200) {
                throw new RuntimeException();
            }
//            BufferedReader buf = new BufferedReader(new InputStreamReader(response.body().byteStream()));
//            String line;
//            while ((line = buf.readLine()) != null) {
//                System.out.println(line);
//            }
            response.close();
        }
    }
}
