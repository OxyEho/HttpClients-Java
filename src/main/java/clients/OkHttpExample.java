package clients;

import okhttp3.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

public class OkHttpExample implements IHttpClient, IAsyncHttpClient {
    @Override
    public void makeGet(int requestsCount, String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        for (int i = 0; i < requestsCount; i++) {
            Call call = client.newCall(request);
            Response response = call.execute();
            readInputData(response);
        }
    }
    @Override
    public void makePost(int requestsCount, String url , byte[] data) throws IOException {
        RequestBody body = RequestBody.create(data);
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
    @Override
    public void makeAsyncGet(int requestsCount, String url) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        final CountDownLatch latch = new CountDownLatch(requestsCount);
        for (int i = 0; i < requestsCount; i++) {
            client.newCall(request).enqueue(new Callback() {
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
        client.dispatcher().executorService().shutdown();
    }
    @Override
    public void makeAsyncPost(int requestsCount, String url, byte[] data) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(data))
                .build();
        final CountDownLatch latch = new CountDownLatch(requestsCount);
        for (int i = 0; i < requestsCount; i++) {
            client.newCall(request).enqueue(new Callback() {
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
        client.dispatcher().executorService().shutdown();
    }
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
