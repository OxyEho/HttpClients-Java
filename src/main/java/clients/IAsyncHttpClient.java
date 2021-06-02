package clients;

import org.json.JSONObject;

public interface IAsyncHttpClient {
    void makeAsyncGet(int requestsCount, String url) throws Exception;
    void makeAsyncPost(int requestsCount, String url, byte[] data) throws Exception;
}
