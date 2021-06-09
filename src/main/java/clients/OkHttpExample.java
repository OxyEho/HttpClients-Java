package clients;

import okhttp3.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

public class OkHttpExample implements IHttpClient, IAsyncHttpClient {
    private static final String url = "http://localhost:8080/Server_war/json_test";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Request getRequest = new Request.Builder()
            .url(url)
            .get()
            .build();
    private final Request postRequest;

    public OkHttpExample(byte[] data) {
        RequestBody body = RequestBody.create(data);
        postRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }

    @Override
    public void makeGet() throws IOException {
        Call call = client.newCall(getRequest);
        Response response = call.execute();
        readInputData(response);

    }
    @Override
    public void makePost() throws IOException {
        Call call = client.newCall(postRequest);
        Response response = call.execute();
        readInputData(response);
    }

    @Override
    public void makeAsyncGet(int requestsCount) throws Exception {
        final CountDownLatch latch = new CountDownLatch(requestsCount);
        for (int i = 0; i < requestsCount; i++) {
            client.newCall(getRequest).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    throw new RuntimeException(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    readInputData(response);
                    latch.countDown();
                }
            });
        }
        latch.await();
    }
    @Override
    public void makeAsyncPost(int requestsCount) throws Exception {
        final CountDownLatch latch = new CountDownLatch(requestsCount);
        for (int i = 0; i < requestsCount; i++) {
            client.newCall(postRequest).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    throw new RuntimeException(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    readInputData(response);
                    latch.countDown();
                }
            });
        }
        latch.await();
    }

    @Override
    public void stopAsync() throws Exception { client.dispatcher().executorService().shutdown(); }

    private void readInputData(Response response) throws IOException {
        if (!response.isSuccessful()) {
            throw new RuntimeException();
        }
        try (ResponseBody requestBody = response.body()) {
            try (BufferedReader buf = new BufferedReader(new InputStreamReader(requestBody.byteStream()))) {
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = buf.readLine()) != null) {
                    builder.append(line);
                }
            }
        }
        response.close();
    }
}
