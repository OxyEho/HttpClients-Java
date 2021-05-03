
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ApacheHttpClientExample implements IHttpClient {

    private final JSONObject data = new JSONObject();

    public  ApacheHttpClientExample() {
        try {
            data.put("user", "VALUE1");
            data.put("pass", "VALUE2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void makeGetRequest(int requestsCount, String url) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        for (int i = 0; i < requestsCount; i++){

            CloseableHttpResponse httpResponse = httpclient.execute(httpget);

            var a = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line;
            while ((line = a.readLine()) != null) {
                System.out.println(line);
            }
            httpResponse.close();
        }
        httpclient.close();
    }

    public void makePostRequest(int requestsCount, String url) throws IOException {
        StringEntity requestEntity = new StringEntity(
                data.toString(),
                ContentType.APPLICATION_JSON);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
//        List<NameValuePair> urlParameters = new ArrayList<>();
//        urlParameters.add(new BasicNameValuePair("username", "test"));
//        urlParameters.add(new BasicNameValuePair("password", "test"));

//        httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
        httpPost.setEntity(requestEntity);

        for (int i = 0; i < requestsCount; i++){

            CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException();
            }
//            var a = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
//            String line;
//            while ((line = a.readLine()) != null) {
//                System.out.println(line);
//            }
            httpResponse.close();
        }
        httpclient.close();
    }
}
