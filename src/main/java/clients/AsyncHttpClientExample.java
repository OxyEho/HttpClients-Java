package clients;

import org.asynchttpclient.*;
import org.asynchttpclient.util.HttpConstants;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncHttpClientExample implements IHttpClient {

    private static final JSONObject data = new JSONObject().put("KEY1", "VALUE1").put("KEY2", "VALUE2");

    public boolean makeGetRequest(int requestsCount, String url) throws ExecutionException, InterruptedException, IOException {
        AsyncHttpClient client = Dsl.asyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().build());
        Request request = new RequestBuilder(HttpConstants.Methods.GET)
                .setUrl(url)
                .build();
        ArrayList<Future<Response>> responses = new ArrayList<>();
        for (int i = 0; i < requestsCount; i++) {
            Future<Response> f = client.executeRequest(request, new AsyncCompletionHandler<>() {
                @Override
                public Response onCompleted(Response response){
                    if (response.getStatusCode() != 200)
                        throw new RuntimeException();
                    return response;
                }
            });
            responses.add(f);
        }
        for (Future<Response> resp: responses) {
            Response response = resp.get();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getResponseBodyAsStream()))) {
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            }
        }
        client.close();
        return true;
    }

    public boolean makePostRequest(int requestsCount, String url) throws ExecutionException, InterruptedException, IOException {
        AsyncHttpClient client = Dsl.asyncHttpClient();
        Request request = new RequestBuilder(HttpConstants.Methods.POST)
                .setUrl(url)
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .setBody(data.toString())
                .build();
        ArrayList<Future<Response>> responses = new ArrayList<>();
        for (int i = 0; i < requestsCount; i++) {
            Future<Response> f = client.executeRequest(request, new AsyncCompletionHandler<>() {
                @Override
                public Response onCompleted(Response response){
                    if (response.getStatusCode() != 200)
                        throw new RuntimeException();
                    return response;
                }
            });
            responses.add(f);
        }
        for (Future<Response> resp: responses) {
            Response response = resp.get();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getResponseBodyAsStream()))) {
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            }
        }
        client.close();
        return true;
    }
}