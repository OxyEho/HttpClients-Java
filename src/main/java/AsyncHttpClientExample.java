import org.apache.commons.lang3.time.StopWatch;
import org.asynchttpclient.*;
import org.asynchttpclient.util.HttpConstants;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncHttpClientExample {
    public static void makeGetRequest(int requestsCount, String url) throws ExecutionException, InterruptedException {
        AsyncHttpClient client = Dsl.asyncHttpClient();
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

    public static void makePostRequest(int requestsCount, String url) throws ExecutionException, InterruptedException {
        JSONObject data = new JSONObject();
        try {
            data.put("user", "VALUE1");
            data.put("pass", "VALUE2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        int c = 0;
        for (Future<Response> resp: responses) {
            System.out.println(resp.get().getResponseBody());
            c++;
        }
        System.out.println(c);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        StopWatch watch = new StopWatch();
        makeGetRequest(1, "http://localhost:8080/Server_war/json");
        watch.start();
        makeGetRequest(10000, "http://localhost:8080/Server_war/json");
        watch.stop();
        System.out.println(watch.toString());
        System.exit(0);
    }
}