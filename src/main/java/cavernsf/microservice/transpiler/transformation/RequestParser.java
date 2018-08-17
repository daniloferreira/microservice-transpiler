package cavernsf.microservice.transpiler.transformation;

import cavernsf.microservice.transpiler.http_client.RequestHttpClient;
import cavernsf.microservice.transpiler.stream.ParametersParser;
import cavernsf.microservice.transpiler.stream.Request;
import com.bazaarvoice.jolt.JsonUtils;

public class RequestParser implements ParametersParser {

    private String params;

    private RequestParser(String params) {
        this.params = params;
    }

    public static RequestParser json(String json) {
        return new RequestParser(json);
    }

    @Override
    public Request parse() {
        return JsonUtils.stringToType(params, RequestHttpClient.class);
    }
}
