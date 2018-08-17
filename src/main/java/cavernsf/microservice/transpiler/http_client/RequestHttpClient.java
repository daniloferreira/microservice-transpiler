package cavernsf.microservice.transpiler.http_client;

import cavernsf.microservice.transpiler.definitions.Source;
import cavernsf.microservice.transpiler.stream.Request;
import cavernsf.microservice.transpiler.stream.TranspileResult;
import cavernsf.microservice.transpiler.transformation.OutputTranspiler;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.Map;
import java.util.Optional;

public class RequestHttpClient implements Request {

    private String url;
    private String method;
    private Map<String, String> headers;
    private Object body;

    @Override
    public TranspileResult execute() throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpRequestBase httpRequestBase = MethodFactory.from(Optional.ofNullable(getMethod()), getUrl());
        getHeaders().forEach((s, s2) -> httpRequestBase.addHeader(s, s2));
        HttpResponse response = httpClient.execute(httpRequestBase);

        String output = EntityUtils.toString(response.getEntity());
        httpClient.getConnectionManager().shutdown();

        return new OutputTranspiler(new Source(output));
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public Object getBody() {
        return body;
    }
}
