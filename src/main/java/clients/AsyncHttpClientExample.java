package clients;

import org.asynchttpclient.*;
import org.asynchttpclient.util.HttpConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncHttpClientExample implements IAsyncHttpClient {

    @Override
    public void makeAsyncGet(int requestsCount, String url) throws ExecutionException, InterruptedException, IOException {
        AsyncHttpClient client = Dsl.asyncHttpClient();
        Request request = new RequestBuilder(HttpConstants.Methods.GET)
                .setUrl(url)
                .build();
        List<Future<Response>> futures = new ArrayList<>();
        for (int i = 0; i < requestsCount; i++) {
            Future<Response> future = client.executeRequest(request, new AsyncCompletionHandler<>() {
                @Override
                public Response onCompleted(Response response) throws IOException {
                    if (response.getStatusCode() != 200)
                        throw new RuntimeException();
                    readInputData(response);
                    return response;
                }
            });
            futures.add(future);
        }
        for (Future<Response> future: futures) {
            future.get();
        }
        client.close();
    }
    @Override
    public void makeAsyncPost(int requestsCount, String url, byte[] data) throws InterruptedException, IOException, ExecutionException {
        AsyncHttpClient client = Dsl.asyncHttpClient();
        Request request = new RequestBuilder(HttpConstants.Methods.POST)
                .setUrl(url)
                .setBody(data)
                .build();
        List<Future<Response>> futures = new ArrayList<>();
        for (int i = 0; i < requestsCount; i++) {
            Future<Response> future = client.executeRequest(request, new AsyncCompletionHandler<>() {
                @Override
                public Response onCompleted(Response response) throws IOException {
                    if (response.getStatusCode() != 200)
                        throw new RuntimeException();
                    readInputData(response);
                    return response;
                }
            });
            futures.add(future);
        }
        for (Future<Response> future: futures) {
            future.get();
        }
        client.close();
    }
    private void readInputData(Response response) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getResponseBodyAsStream()))) {
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
    }
}