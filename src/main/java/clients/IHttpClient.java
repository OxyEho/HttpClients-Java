package clients;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IHttpClient {
    void makeGet() throws Exception;
    void makePost() throws Exception;
    default void stop() throws Exception {}
}
