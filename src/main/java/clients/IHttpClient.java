package clients;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IHttpClient {
    void makeGet(int requestsCount, String url) throws Exception;
    void makePost(int requestsCount, String url, byte[] data) throws Exception;
}
