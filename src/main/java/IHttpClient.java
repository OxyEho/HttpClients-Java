import java.io.IOException;
import java.net.URISyntaxException;

public interface IHttpClient {
    public void makeGetRequest(int requestsCount, String url) throws URISyntaxException, IOException, InterruptedException;
    public void makePostRequest(int requestsCount, String url) throws URISyntaxException, IOException, InterruptedException;
}
