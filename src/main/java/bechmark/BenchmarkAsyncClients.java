package bechmark;

        import clients.*;
        import org.openjdk.jmh.annotations.*;

        import java.util.concurrent.TimeUnit;

@Threads(value = 1)
@Measurement(iterations = 10)
@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 10)
public class BenchmarkAsyncClients {

    @State(Scope.Thread)
    public static class TestState {
        public byte[] data = new byte[10000];
        public String url;
        public int requestsCount;

        @Setup
        public void setupJson() {
            for (int i = 0; i < data.length; i++) {
                data[i] = 1;
            }
        }

        @Setup
        public void setUrl() { url = "http://localhost:8080/Server_war/json_test"; }

        @Setup
        public void setRequestsCount() { requestsCount = 1; }
    }

    @Benchmark
    public void okHttpBenchmarkGetAsync(TestState state) throws Exception {
        IAsyncHttpClient client = new OkHttpExample();
        client.makeAsyncGet(state.requestsCount, state.url);
    }

    @Benchmark
    public void asyncHttp11BenchmarkGet(TestState state) throws Exception {
        IAsyncHttpClient client = new HttpClientExample();
        client.makeAsyncGet(state.requestsCount, state.url);
    }

    @Benchmark
    public void jettyHttpBenchmarkGetAsync(TestState state) throws Exception {
        IAsyncHttpClient client = new JettyHttpClientExample();
        client.makeAsyncGet(state.requestsCount, state.url);
    }

    @Benchmark
    public void asyncClientBenchmarkGet(TestState state) throws Exception {
        IAsyncHttpClient client = new AsyncHttpClientExample();
        client.makeAsyncGet(state.requestsCount, state.url);
    }

    @Benchmark
    public void okHttpBenchmarkPostAsync(TestState state) throws Exception {
        IAsyncHttpClient client = new OkHttpExample();
        client.makeAsyncPost(state.requestsCount, state.url, state.data);
    }

    @Benchmark
    public void asyncHttp11BenchmarkPost(TestState state) throws Exception {
        IAsyncHttpClient client = new HttpClientExample();
        client.makeAsyncPost(state.requestsCount, state.url, state.data);
    }



    @Benchmark
    public void jettyHttpBenchmarkPostAsync(TestState state) throws Exception {
        IAsyncHttpClient client = new JettyHttpClientExample();
        client.makeAsyncPost(state.requestsCount, state.url, state.data);
    }

    @Benchmark
    public void asyncClientBenchmarkPost(TestState state) throws Exception {
        IAsyncHttpClient client = new AsyncHttpClientExample();
        client.makeAsyncPost(state.requestsCount, state.url, state.data);
    }
}