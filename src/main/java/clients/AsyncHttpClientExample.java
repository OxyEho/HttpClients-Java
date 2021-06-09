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
    private static final String url = "http://localhost:8080/Server_war/json_test";
    private static final AsyncHttpClient client = Dsl.asyncHttpClient();
    private static final Request getRequest = new RequestBuilder(HttpConstants.Methods.GET)
            .setUrl(url)
            .build();
    private final Request postRequest;
    public AsyncHttpClientExample(byte[] data) {
        postRequest = new RequestBuilder(HttpConstants.Methods.POST)
                .setUrl(url)
                .setBody(data)
                .build();
    }
    @Override
    public void makeAsyncGet(int requestsCount) throws ExecutionException, InterruptedException, IOException {
        List<Future<Response>> futures = new ArrayList<>();
        for (int i = 0; i < requestsCount; i++) {
            Future<Response> future = client.executeRequest(getRequest, new AsyncCompletionHandler<>() {
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
    }
    @Override
    public void makeAsyncPost(int requestsCount) throws InterruptedException, IOException, ExecutionException {
        List<Future<Response>> futures = new ArrayList<>();
        for (int i = 0; i < requestsCount; i++) {
            Future<Response> future = client.executeRequest(postRequest, new AsyncCompletionHandler<>() {
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
    }

    @Override
    public void stopAsync() throws Exception { client.close(); }

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