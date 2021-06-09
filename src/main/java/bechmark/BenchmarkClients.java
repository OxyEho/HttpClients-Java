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
public class BenchmarkClients {

    @State(Scope.Thread)
    public static class TestState {
        public byte[] data = new byte[1000000];
        public int requestsCount;
        public IHttpClient apacheClient;
        public IHttpClient http11Client;
        public IHttpClient httpURLConnection;
        public IHttpClient okHttpClient;
        public IHttpClient jettyHttpClient;

        @Setup
        public void setClients() {
            apacheClient = new ApacheHttpClientExample(data);
            http11Client = new HttpClientExample(data);
            httpURLConnection = new HttpURLConnectionExample(data);
            okHttpClient = new OkHttpExample(data);
            jettyHttpClient = new JettyHttpClientExample(data);
        }

        @Setup
        public void setupData() {
            Arrays.fill(data, (byte) 1);
        }

        @Setup
        public void setRequestsCount() {
            requestsCount = 1;
        }

        @TearDown
        public void stopClients() throws Exception {
            jettyHttpClient.stop();
            apacheClient.stop();
        }
    }

    @Benchmark
    public void apacheBenchmarkGet(TestState state) throws Exception {
        state.apacheClient.makeGet();
    }

    @Benchmark
    public void okHttpBenchmarkGet(TestState state) throws Exception {
        state.okHttpClient.makeGet();
    }

    @Benchmark
    public void http11BenchmarkGet(TestState state) throws Exception {
        state.http11Client.makeGet();
    }

    @Benchmark
    public void httpURLConnectionBenchmarkGet(TestState state) throws Exception {
        state.httpURLConnection.makeGet();
    }

    @Benchmark
    public void jettyHttpBenchmarkGet(TestState state) throws Exception {
        state.jettyHttpClient.makeGet();
    }

    @Benchmark
    public void apacheBenchmarkPost(TestState state) throws Exception {
        state.apacheClient.makePost();
    }

    @Benchmark
    public void okHttpBenchmarkPost(TestState state) throws Exception {
        state.okHttpClient.makePost();
    }

    @Benchmark
    public void http11BenchmarkPost(TestState state) throws Exception {
        state.http11Client.makePost();
    }

    @Benchmark
    public void httpURLConnectionBenchmarkPost(TestState state) throws Exception {
        state.httpURLConnection.makePost();
    }

    @Benchmark
    public void jettyHttpBenchmarkPost(TestState state) throws Exception {
        state.jettyHttpClient.makePost();
    }
}