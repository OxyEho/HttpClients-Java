package bechmark;

import clients.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@Threads(value = 1)
@Measurement(iterations = 10)
@Fork(value = 1, warmups = 10)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 10)
public class BenchmarkClients {

    @Benchmark
    public void apacheBenchmark(Blackhole blackhole) throws Exception {
        String url = "http://localhost:8080/Server_war/json_test";
        IHttpClient client = new ApacheHttpClientExample();
        boolean res = client.makeGetRequest(1, url);
        assert res;
        blackhole.consume(res);
    }

    @Benchmark
    public void okHttpBenchmark(Blackhole blackhole) throws Exception {
        String url = "http://localhost:8080/Server_war/json_test";
        IHttpClient client = new OkHttpExample();
        boolean res = client.makeGetRequest(1, url);
        assert res;
        blackhole.consume(res);
    }
//
    @Benchmark
    public void http11(Blackhole blackhole) throws Exception {
        String url = "http://localhost:8080/Server_war/json_test";
        IHttpClient client = new HttpClientExample();
        boolean res = client.makeGetRequest(1, url);
        assert res;
        blackhole.consume(res);
    }

    @Benchmark
    public void httpURLConnectionBenchmark(Blackhole blackhole) throws Exception {
        String url = "http://localhost:8080/Server_war/json_test";
        IHttpClient client = new HttpURLConnectionExample();
        boolean res = client.makeGetRequest(1, url);
        assert res;
        blackhole.consume(res);
    }

    @Benchmark
    public void jettyHttp(Blackhole blackhole) throws Exception {
        String url = "http://localhost:8080/Server_war/json_test";
        IHttpClient client = new JettyHttpClientExample();
        boolean res = client.makeGetRequest(1, url);
        assert res;
        blackhole.consume(res);
    }

    @Benchmark
    public void asyncClient(Blackhole blackhole) throws Exception {
        String url = "http://localhost:8080/Server_war/json_test";
        IHttpClient client = new AsyncHttpClientExample();
        boolean res = client.makeGetRequest(1, url);
        assert res;
        blackhole.consume(res);
    }
}
