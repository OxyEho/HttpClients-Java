package clients;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IHttpClient {
    boolean makeGetRequest(int requestsCount, String url) throws Exception;
    boolean makePostRequest(int requestsCount, String url) throws Exception;
}
