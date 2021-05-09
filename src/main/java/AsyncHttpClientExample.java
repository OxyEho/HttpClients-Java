import org.apache.commons.lang3.time.StopWatch;
import org.asynchttpclient.*;
import org.asynchttpclient.util.HttpConstants;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncHttpClientExample implements IHttpClient {
    private final JSONObject data;

    public AsyncHttpClientExample() {
        data = new JSONObject();
        try {
            data.put("user", "VALUE1");
            data.put("pass", "VALUE2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void makeGetRequest(int requestsCount, String url) throws ExecutionException, InterruptedException {
        AsyncHttpClient client = Dsl.asyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().setKeepAlive(true).build());
        Request request = new RequestBuilder(HttpConstants.Methods.GET)
                .setUrl(url)
                .build();
        ArrayList<Future<Response>> responses = new ArrayList<>();
        for (int i = 0; i < requestsCount; i++) {
            Future<Response> f = client.executeRequest(request, new AsyncCompletionHandler<Response>() {
                @Override
                public Response onCompleted(Response response) throws IOException {
                    if (response.getStatusCode() != 200)
                        throw new RuntimeException();
                    return response;
                }
            });
            responses.add(f);
        }

        for (Future<Response> resp: responses) {
            resp.get();
        }
    }

    public void makePostRequest(int requestsCount, String url) throws ExecutionException, InterruptedException {
        AsyncHttpClient client = Dsl.asyncHttpClient();
        Request request = new RequestBuilder(HttpConstants.Methods.POST)
                .setUrl(url)
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .setBody(data.toString())
                .build();
        ArrayList<Future<Response>> responses = new ArrayList<>();
        for (int i = 0; i < requestsCount; i++) {
            Future<Response> f = client.executeRequest(request, new AsyncCompletionHandler<Response>() {
                @Override
                public Response onCompleted(Response response) throws IOException {
                    if (response.getStatusCode() != 200)
                        throw new RuntimeException();
                    return response;
                }
            });
            responses.add(f);
        }
        for (Future<Response> resp: responses) {
            resp.get();
        }
    }
}