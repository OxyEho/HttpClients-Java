import org.apache.commons.lang3.time.StopWatch;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncHttpClientExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AsyncHttpClient c1 = Dsl.asyncHttpClient();
        Future<Response> f1 = c1.prepareGet("http://localhost:8080/Server_war/hello").execute(new AsyncCompletionHandler<Response>() {

            @Override
            public Response onCompleted(Response response) throws IOException {
                if (response.getStatusCode() != 200)
                    throw new RuntimeException();
                return response;
            }

            @Override
            public void onThrowable(Throwable t) {
            }
        });
        f1.get();
        ArrayList<Future<Response>> responses = new ArrayList<>();
        StopWatch watch = new StopWatch();
        watch.start();
        for (int i = 0; i < 100; i++) {
            AsyncHttpClient c = Dsl.asyncHttpClient();
            Future<Response> f = c.prepareGet("http://localhost:8080/Server_war/hello").execute(new AsyncCompletionHandler<Response>() {

                @Override
                public Response onCompleted(Response response) throws IOException {
                    if (response.getStatusCode() != 200)
                        throw new RuntimeException();
                    return response;
                }

                @Override
                public void onThrowable(Throwable t) {
                }
            });
            responses.add(f);
        }

        for (Future<Response> resp: responses) {
            System.out.println(resp.get().getResponseBody());
        }
        watch.stop();
        System.out.println(watch.toString());
        System.exit(0);
    }
}
