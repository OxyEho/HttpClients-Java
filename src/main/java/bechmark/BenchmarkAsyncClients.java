package bechmark;

import clients.*;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Threads(value = 1)
@Measurement(iterations = 1000)
@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 100)
public class BenchmarkAsyncClients {

    @State(Scope.Thread)
    public static class TestState {
        public byte[] data = new byte[10000];
        public int requestsCount;
        public IAsyncHttpClient okHttpClient;
        public IAsyncHttpClient http11Client;
        public IAsyncHttpClient asyncHttpClient;
        public IAsyncHttpClient jettyHttpClient;

        @Setup
        public void setClients() {
            http11Client = new HttpClientExample(data);
            asyncHttpClient = new AsyncHttpClientExample(data);
            okHttpClient = new OkHttpExample(data);
            jettyHttpClient = new JettyHttpClientExample(data);
        }

        @Setup
        public void setupData() {
            Arrays.fill(data, (byte) 1);
        }

        @Setup
        public void setRequestsCount() { requestsCount = 100; }

        @TearDown
        public void stopClients() throws Exception {
            asyncHttpClient.stopAsync();
            jettyHttpClient.stopAsync();
            okHttpClient.stopAsync();
        }
    }

//    @Benchmark
//    public void okHttpBenchmarkAsyncGet(TestState state) throws Exception {
//        state.okHttpClient.makeAsyncGet(state.requestsCount);
//    }
//
//    @Benchmark
//    public void asyncHttp11BenchmarkGet(TestState state) throws Exception {
//        state.http11Client.makeAsyncGet(state.requestsCount);
//    }
//
    @Benchmark
    public void jettyHttpBenchmarkAsyncGet(TestState state) throws Exception {
        state.jettyHttpClient.makeAsyncGet(state.requestsCount);
    }
//
//    @Benchmark
//    public void asyncClientBenchmarkGet(TestState state) throws Exception {
//        state.asyncHttpClient.makeAsyncGet(state.requestsCount);
//    }
//
//    @Benchmark
//    public void okHttpBenchmarkAsyncPost(TestState state) throws Exception {
//        state.okHttpClient.makeAsyncPost(state.requestsCount);
//    }
//
//    @Benchmark
//    public void asyncHttp11BenchmarkPost(TestState state) throws Exception {
//        state.http11Client.makeAsyncPost(state.requestsCount);
//    }

    @Benchmark
    public void jettyHttpBenchmarkAsyncPost(TestState state) throws Exception {
        state.jettyHttpClient.makeAsyncPost(state.requestsCount);
    }

//    @Benchmark
//    public void asyncClientBenchmarkPost(TestState state) throws Exception {
//        state.asyncHttpClient.makeAsyncPost(state.requestsCount);
//    }
}