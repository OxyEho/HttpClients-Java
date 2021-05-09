import java.io.IOException;
import java.net.URISyntaxException;

public interface IHttpClient {
    void makeGetRequest(int requestsCount, String url) throws Exception;
    void makePostRequest(int requestsCount, String url) throws Exception;
}
