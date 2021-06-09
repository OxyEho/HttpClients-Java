package clients;

import org.json.JSONObject;

public interface IAsyncHttpClient {
    void makeAsyncGet(int requestsCount) throws Exception;
    void makeAsyncPost(int requestsCount) throws Exception;
    default void stopAsync() throws Exception {}
}
