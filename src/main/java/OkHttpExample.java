import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OkHttpExample implements IHttpClient {
    private static final JSONObject data = new JSONObject().put("KEY1", "VALUE1").put("KEY2", "VALUE2");
    public void makeGetRequest(int requestsCount, String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);
        for (int i = 0; i < requestsCount; i++) {
            Response response = call.clone().execute();
            readInputData(response);
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
            readInputData(response);
        }
    }

    private void readInputData(Response response) throws IOException {
        if (response.code() != 200) {
            throw new RuntimeException();
        }
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(response.body().byteStream()))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = buf.readLine()) != null) {
                builder.append(line);
            }
        }
        response.close();
    }
}
